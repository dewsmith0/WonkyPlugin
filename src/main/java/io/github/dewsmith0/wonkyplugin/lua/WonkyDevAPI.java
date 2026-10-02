package io.github.dewsmith0.wonkyplugin.lua;

import org.figuramc.figura.avatar.Avatar;
import org.figuramc.figura.lua.FiguraLuaRuntime;
import org.figuramc.figura.lua.LuaWhitelist;

@LuaWhitelist
public class WonkyDevAPI {
    public Avatar owner;
    public FiguraLuaRuntime runtime;
    public WonkyDevAPI(FiguraLuaRuntime runtime) {
        this.runtime = runtime;
        this.owner = runtime.owner;
    }




}
