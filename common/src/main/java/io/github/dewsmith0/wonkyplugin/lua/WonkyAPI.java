package io.github.dewsmith0.wonkyplugin.lua;

import io.github.dewsmith0.wonkyplugin.WonkyPlugin;
import org.figuramc.figura.avatar.Avatar;
import org.figuramc.figura.lua.FiguraLuaRuntime;
import org.figuramc.figura.lua.LuaNotNil;
import org.figuramc.figura.lua.LuaWhitelist;
import org.figuramc.figura.lua.docs.LuaMethodDoc;
import org.figuramc.figura.lua.docs.LuaMethodOverload;
import org.figuramc.figura.lua.docs.LuaTypeDoc;
import org.figuramc.figura.permissions.Permissions;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


@LuaWhitelist
@LuaTypeDoc(name = "WonkyAPI", value = "wonky")
public class WonkyAPI {
    public final Avatar avatar;
//    @LuaWhitelist
//    public final WebSocketAPI ws = new WebSocketAPI(this);

    private static final Map<Integer, String> BUMPSCOCITY_MESSAGES = Map.of(
            0, "Dear me, where did all the bumpscocity go? Quite unnerving in here with all of it gone.",
            1, "Well I'm quite feeling the lack of bumpscocity in here. Only a single bumpscocit. Still, it'll have to do.",
            12, "Just a light breeze of bumpscocity in here at the moment, not bad. I personally enjoy a bit more bumpscocity, but at this amount it's absolutely reasonable.",
            50, "Ahh, quite a pleasant amount of bumpscocity we've got today, wouldn't you say? Very enjoyable.",
            76, "Well, the bumpscocity in here is really getting up there, isn't it? No matter, the story must carry on no matter how much or how little bumpscocity there is.",
            100, "Who turned up the bumpscocity so high? I like bumpscocity as much as the next person, but a hundred is quite a lot, wouldn't you say? ",
            1000, "My god, the bumpscocity in here is absolutely overwhelming. A thousand?! You people have got to be nuts! How can you stand this much bumpscocity?");

    private static final TreeMap<Integer, String> BUMPSCOCITY_SORTED = new TreeMap<>(BUMPSCOCITY_MESSAGES);

    public WonkyAPI(FiguraLuaRuntime runtime) {
        this.avatar = runtime.owner;
    }

    @LuaWhitelist
    @LuaMethodDoc("wonky.test")
    public String test() {
        return "yoo it works";
    }

    @LuaWhitelist
    @LuaMethodDoc("wonky.get_bumpscocity")
    public String getBumpscocity() {
        // welcome to hell
        try {
            int bumpscocity;
            Class<?> klass = Class.forName("dev.celestial.silly.SillyPermissions");
            Field field = klass.getField("BUMPSCOCITY");
            Permissions permission = (Permissions) field.get(klass);
            bumpscocity = avatar.permissions.get(permission);
            WonkyPlugin.LOGGER.info("Bumpscocity: {}", bumpscocity);
            List<String> matches = new ArrayList<>();
            for (Map.Entry<Integer, String> e : BUMPSCOCITY_SORTED.entrySet()) {
                if (bumpscocity >= e.getKey()) {
                    matches.add(e.getValue());
                    WonkyPlugin.LOGGER.info("bump {}", e.getKey());
                }
            }
            return matches.getLast();
        } catch (IllegalAccessException | ClassNotFoundException | NoSuchFieldException e) {
            throw new LuaError("Failed to read bumpscocity: %s".formatted(e));
        }
    }

    @LuaWhitelist
    @LuaMethodDoc(value = "wonky.find_regex", overloads = {
            @LuaMethodOverload(
                    argumentNames = {"expression", "text"},
                    argumentTypes = {String.class, String.class},
                    returnType = String.class
            )})

    public LuaTable matchRegex(@LuaNotNil String expression, @LuaNotNil String text) {
        try {
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(text);
            LuaTable ret = new LuaTable();
            int i = 1;
            while (matcher.find()) {
                String current = matcher.group();
                ret.set(i, current);
                i++;
            }
            return ret;

        } catch (PatternSyntaxException e) {
            throw new LuaError(e.getMessage());
        }
    }

    @LuaWhitelist
    @LuaMethodDoc(value = "wonky.replace_regex", overloads = {
            @LuaMethodOverload(
                    argumentNames = {"expression", "text", "replacement"},
                    argumentTypes = {String.class, String.class, String.class},
                    returnType = String.class
            )})
    public String replaceRegex(@LuaNotNil String expression, @LuaNotNil String text, @LuaNotNil String replacement) {
        try {
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(text);
            StringBuilder builder = new StringBuilder();
            while (matcher.find()) {
                matcher.appendReplacement(builder, replacement);
            }
            matcher.appendTail(builder);
            return builder.toString();
        } catch (PatternSyntaxException e) {
            throw new LuaError(e.getMessage());
        }

    }

    @Override
    public String toString() {
        return "WonkyAPI";
    }
}