package io.github.dewsmith0.wonkyplugin.lua;

import io.github.dewsmith0.wonkyplugin.WonkyPlugin;
import io.github.dewsmith0.wonkyplugin.duck.AvatarExtensions;
import org.figuramc.figura.avatar.Avatar;
import org.figuramc.figura.lua.FiguraLuaRuntime;
import org.figuramc.figura.lua.LuaNotNil;
import org.figuramc.figura.lua.LuaWhitelist;
import org.figuramc.figura.lua.api.data.FiguraFuture;
import org.figuramc.figura.lua.api.net.NetworkingAPI;
import org.figuramc.figura.lua.docs.LuaMethodDoc;
import org.figuramc.figura.lua.docs.LuaTypeDoc;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;

import java.net.URI;
import java.net.http.HttpClient;

@LuaWhitelist
@LuaTypeDoc(name = "WebSocketAPI", value = "websocket")
public class WebSocketAPI {
    //private final WonkyAPI parent;
    private final HttpClient httpClient;
    private final Avatar owner;
    private final NetworkingAPI net;

    public WebSocketAPI(/*WonkyAPI parent*/ FiguraLuaRuntime runtime) {
        //this.parent = parent;
        this.owner = runtime.owner;
        this.httpClient = HttpClient.newHttpClient();
        this.net = new NetworkingAPI(owner);
        WonkyPlugin.LOGGER.warn("DEBUG: WebSocketAPi init, net = {}", net);
    }

    @LuaWhitelist
    @LuaMethodDoc(value = "websocket.connect")
    public FiguraFuture<WonkyWebSocket> connect(@LuaNotNil String uri) {
        try {
            net.securityCheck(uri);
        } catch (RuntimeException e) {
            // i am NOT doing reflection for this
            if (e.getClass().getSimpleName().equals("LinkNotAllowedException")) {
                WonkyPlugin.LOGGER.warn("[WebSocketAPI] Tried to open a socket to not allowed link {}", uri);
                throw new LuaError("Networking whitelist/blacklist does not allow access to link: %s".formatted(uri));
            } else throw e;
        }
        WonkyPlugin.LOGGER.info("[WebSocketAPI] Connecting to {}", uri);
        FiguraFuture<WonkyWebSocket> future = new FiguraFuture<>();
        try {
            String uriForValidation = uri;
            if (uriForValidation.regionMatches(true, 0, "http://", 0, 7))
                uriForValidation = "ws://" + uriForValidation.substring(7);
            else if (uriForValidation.regionMatches(true, 0, "https://", 0, 8))
                uriForValidation = "wss://" + uriForValidation.substring(8);
            WonkyWebSocket wrapper = new WonkyWebSocket(owner, uriForValidation);

            httpClient.newWebSocketBuilder()
                    .buildAsync(URI.create(uri), wrapper.listener)
                    .whenCompleteAsync((webSocket, error) -> {
                        if (error != null) {
                            WonkyPlugin.LOGGER.error("Failed to connect to {}: {}", uri, error.getMessage());
                            future.error(error);
                        } else {
                            wrapper.attach(webSocket);
                            future.complete(wrapper);
                        }
                    });
        } catch (Exception e) {
            WonkyPlugin.LOGGER.error("Failed to open socket to {}: {}", uri, e.getMessage());
        }
        return future;
    }

    @LuaWhitelist
    @LuaMethodDoc(value = "websocket.get_open_sockets")
    public LuaTable getOpenSockets() {
        LuaTable ret = new LuaTable();
        int i = 1;
        for (WonkyWebSocket socket : ((AvatarExtensions) owner).wonky$getOpenWebSockets()) {
            ret.set(i, owner.luaRuntime.typeManager.javaToLua(socket).checkvalue(1));
            i++;
        }
        return ret;
    }

    public String toString() {
        return "WebSocketAPI";
    }
}
