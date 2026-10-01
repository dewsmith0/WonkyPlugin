package io.github.dewsmith0.wonkyplugin.lua;

import io.github.dewsmith0.wonkyplugin.duck.AvatarExtensions;
import org.figuramc.figura.avatar.Avatar;
import org.figuramc.figura.lua.LuaNotNil;
import org.figuramc.figura.lua.LuaWhitelist;
import org.figuramc.figura.lua.docs.LuaMethodDoc;
import org.figuramc.figura.lua.docs.LuaMethodOverload;
import org.figuramc.figura.lua.docs.LuaTypeDoc;
import org.luaj.vm2.LuaError;

import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentLinkedQueue;

// "inspired" from https://github.com/Figurean-Creations/Bytura/blob/1.21.4/common/src/main/java/org/figuramc/figura/lua/api/net/FiguraWebSocket.java
@LuaWhitelist
@LuaTypeDoc(name = "WebSocket", value = "websocket.instance")
public class WonkyWebSocket {
    private final Avatar owner;
    private volatile WebSocket ws;
    private final StringBuilder textBuffer = new StringBuilder();
    private final ConcurrentLinkedQueue<String> incoming = new ConcurrentLinkedQueue<>();
    private volatile boolean closed = false;
    private volatile int closeCode = -1;
    private volatile String closeReason = "";
    private volatile Throwable errorObject = null;

    WonkyWebSocket(Avatar owner) {
        this.owner = owner;
        ((AvatarExtensions) owner).wonky$getOpenWebSockets().add(this);
    }

    final WebSocket.Listener listener = new WebSocket.Listener() {
        @Override
        public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
            textBuffer.append(data);
            if (last) {
                incoming.add(textBuffer.toString());
                textBuffer.setLength(0);
            }
            webSocket.request(1);
            return null;
        }

        @Override
        public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
            closed = true;
            closeCode = statusCode;
            closeReason = reason == null ? "" : reason;
            return null;
        }

        @Override
        public void onError(WebSocket webSocket, Throwable error) {
            closed = true;
            errorObject = error;
        }
    };

    void attach(WebSocket ws) {
        this.ws = ws;
    }

    @LuaWhitelist
    @LuaMethodDoc(
            value = "websocket.poll",
            overloads = @LuaMethodOverload(
                    returnType = String.class
            )
    )
    public String poll() {
        return incoming.poll();
    }

    @LuaWhitelist
    @LuaMethodDoc(
            value = "websocket.has_message",
            overloads = @LuaMethodOverload(
                    returnType = Boolean.class
            )
    )
    public boolean hasMessage() {
        return !incoming.isEmpty();
    }


    @LuaWhitelist
    @LuaMethodDoc(
            value = "websocket.send",
            overloads = @LuaMethodOverload(
                    argumentTypes = String.class,
                    argumentNames = "text"
            )
    )
    public void send(@LuaNotNil String text) {
        if (ws == null || closed)
            throw new LuaError("Cannot send on a closed WebSocket");
//        if (Configs.LOG_NETWORKING.value != 3) {
//            WonkyPlugin.LOGGER.info("Sent %d chars".formatted(text.length()));
//        }
        ws.sendText(text, true);
    }

    @LuaWhitelist
    @LuaMethodDoc("websocket.close")
    public void close() {
        closed = true;
        if (ws != null)
            ws.sendClose(WebSocket.NORMAL_CLOSURE, "");
        ((AvatarExtensions) owner).wonky$getOpenWebSockets().remove(this);
    }

    @LuaWhitelist
    @LuaMethodDoc(
            value = "websocket.is_closed",
            overloads = @LuaMethodOverload(
                    returnType = Boolean.class
            )
    )
    public boolean isClosed() {
        return closed;
    }

    @LuaWhitelist
    @LuaMethodDoc(
            value = "websocket.has_error",
            overloads = @LuaMethodOverload(
                    returnType = Boolean.class
            )
    )
    public boolean hasError() {
        return errorObject != null;
    }

    @LuaWhitelist
    @LuaMethodDoc(
            value = "websocket.get_close_code",
            overloads = @LuaMethodOverload(
                    returnType = Integer.class
            )
    )
    public int getCloseCode() {
        return closeCode;
    }

    @LuaWhitelist
    @LuaMethodDoc(
            value = "websocket.get_close_reason",
            overloads = @LuaMethodOverload(
                    returnType = String.class
            )
    )
    public String getCloseReason() {
        return closeReason;
    }

    public void forceClose() {
        closed = true;
        if (ws != null) {
            try {
                ws.abort();
            } catch (Exception ignored) {}
        }
    }

    @Override
    public String toString() {
        return "WebSocket(closed=%s)".formatted(closed);
    }
}
