package io.github.dewsmith0.wonkyplugin;

import org.figuramc.figura.avatar.Avatar;
import org.figuramc.figura.entries.FiguraAPI;
import org.figuramc.figura.lua.LuaWhitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@LuaWhitelist
public class WonkyPlugin implements FiguraAPI {
    public static final String PLUGIN_ID = "wonkyplugin";
    public static final Logger LOGGER = LoggerFactory.getLogger(PLUGIN_ID);
    public static final Color COLOR = new Color(162, 59, 236);
    public WonkyPlugin() {
    }

    public WonkyPlugin(Avatar avatar) {
        // this.avatar = avatar;
    }

    public static void init() {
    }

    @Override
    public FiguraAPI build(Avatar avatar) {
        return new WonkyPlugin(avatar);
    }

    @Override
    public String getName() {
        return PLUGIN_ID;
    }

    @Override
    public Collection<Class<?>> getWhitelistedClasses() {
        List<Class<?>> classesToRegister = new ArrayList<>();
        for (Class<?> aClass : WONKYPLUGIN_CLASSES) {
            if (aClass.isAnnotationPresent(LuaWhitelist.class)) {
                classesToRegister.add(aClass);
            }
        }
        return classesToRegister;
    }

    @Override
    public Collection<Class<?>> getDocsClasses() {
        return List.of();
    }

    public static final Class<?>[] WONKYPLUGIN_CLASSES = new Class[] {
            WonkyPlugin.class
    };

}
