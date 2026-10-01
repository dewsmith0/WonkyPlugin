package io.github.dewsmith0.wonkyplugin.mixin;

import io.github.dewsmith0.wonkyplugin.WonkyPlugin;
import io.github.dewsmith0.wonkyplugin.lua.WebSocketAPI;
import io.github.dewsmith0.wonkyplugin.lua.WonkyAPI;
import io.github.dewsmith0.wonkyplugin.lua.WonkyDevAPI;
import io.github.dewsmith0.wonkyplugin.lua.WonkyWebSocket;
import org.figuramc.figura.lua.FiguraAPIManager;
import org.figuramc.figura.lua.FiguraLuaRuntime;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Mixin(value = FiguraAPIManager.class, remap = false)
public class FiguraAPIManagerMixin {
    @Shadow
    @Final
    public static Set<Class<?>> WHITELISTED_CLASSES;

    @Shadow
    @Final
    public static Map<String, Function<FiguraLuaRuntime, Object>> API_GETTERS;

    static {
        WHITELISTED_CLASSES.add(WonkyAPI.class);
        WHITELISTED_CLASSES.add(WebSocketAPI.class);
        WHITELISTED_CLASSES.add(WonkyWebSocket.class);
        API_GETTERS.put("wonky", r -> r.owner.isHost ? new WonkyAPI(r) : null);
        API_GETTERS.put("websocket", r -> r.owner.isHost ? new WebSocketAPI(r) : null);
        if (WonkyPlugin.DEV_MODE) {
            WHITELISTED_CLASSES.add(WonkyDevAPI.class);
            API_GETTERS.put("wonky_dev", r -> r.owner.isHost ? new WonkyDevAPI(r) : null);
        }
    }
}