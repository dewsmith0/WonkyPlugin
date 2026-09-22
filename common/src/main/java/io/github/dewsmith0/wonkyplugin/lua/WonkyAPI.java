package io.github.dewsmith0.wonkyplugin.lua;
import org.figuramc.figura.lua.LuaNotNil;
import org.figuramc.figura.lua.LuaWhitelist;
import org.figuramc.figura.lua.NbtToLua;
import org.figuramc.figura.lua.docs.LuaMethodDoc;
import org.figuramc.figura.lua.docs.LuaMethodOverload;
import org.figuramc.figura.lua.docs.LuaTypeDoc;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import net.minecraft.nbt.ByteArrayTag;


@LuaWhitelist
@LuaTypeDoc(name = "WonkyAPI", value = "wonky")
public class WonkyAPI {

    public WonkyAPI() {
    }

    @LuaWhitelist
    @LuaMethodDoc("wonky.test")
    public String test() {
        return "yoo it works";
    }


    @Override
    public String toString() {
        return "WonkyAPI";
    }
}