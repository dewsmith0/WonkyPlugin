package io.github.dewsmith0.wonkyplugin.mixin;

import io.github.dewsmith0.wonkyplugin.lua.*;
import org.figuramc.figura.lua.docs.FiguraGlobalsDocs;
import org.figuramc.figura.lua.docs.LuaFieldDoc;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = FiguraGlobalsDocs.class, remap = false)
public class FiguraGlobalsDocsMixin {
    @LuaFieldDoc("globals.wonky")
    public WonkyAPI wonky;

    @LuaFieldDoc("globals.websocket")
    public WebSocketAPI websocket;
}