package io.github.dewsmith0.wonkyplugin.mixin;

import org.figuramc.figura.lua.LuaWhitelist;
import org.figuramc.figura.lua.api.event.EventsAPI;
import org.figuramc.figura.lua.api.event.LuaEvent;
import org.figuramc.figura.lua.docs.LuaFieldDoc;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(value = EventsAPI.class, remap = false)
public class EventsAPIMixin {
    @Unique
    @LuaWhitelist
    @LuaFieldDoc("events.websocket_data")
    public LuaEvent WEBSOCKET_DATA = new LuaEvent();

    @Unique
    @LuaWhitelist
    @LuaFieldDoc("events.websocket_closed")
    public LuaEvent WEBSOCKET_CLOSED = new LuaEvent();
    @Shadow
    @Final
    private Map<String, LuaEvent> events;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void wonky$registerEvents(CallbackInfo ci) {
        events.put("WEBSOCKET_DATA", WEBSOCKET_DATA);
        events.put("WEBSOCKET_CLOSED", WEBSOCKET_CLOSED);
    }
}
