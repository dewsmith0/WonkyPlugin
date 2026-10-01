package io.github.dewsmith0.wonkyplugin.config;

import io.github.dewsmith0.wonkyplugin.WonkyPlugin;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import org.figuramc.figura.config.ConfigType;

public class Config {
    public static ConfigType.Category CATEGORY = category("wonkyplugin");
    private static ConfigType.Category category(String name) {
        return new ConfigType.Category(name) {{
            this.name = Component.literal("\uE051")
                    .withStyle(Style.EMPTY.withFont(ResourceLocation.fromNamespaceAndPath("figura", "emoji_portrait")))
                    .append(this.name.copy().withStyle(Style.EMPTY.withFont(Style.DEFAULT_FONT).withColor(WonkyPlugin.COLOR.getRGB())));
       }};
    }
    public static ConfigType.BoolConfig ALLOW_MORE_PORTS;
    static {
        ALLOW_MORE_PORTS = new ConfigType.BoolConfig("allow_more_ports", CATEGORY, true);
    }

    public static void init() {}
        //  ConfigManager.REGISTRY.add(ALLOW_MORE_PORTS);
}

//
//
//    // totally not taken from sillyplugin because it kept resetting for some reason
//    public static class WonkySetting /* extends ConfigType<T> */ {
//        public String name;
//
//        public WonkySetting(String name) {
//            //super(name, defaultValue);
//            this.name = name;
//        }
//
//        public Boolean getBool() {
//            throw new RuntimeException("Cannot get a setting of type " + this.getClass().getSimpleName() + " as a boolean!");
//        }
//
//        public void setBool(Boolean val) {
//            throw new RuntimeException("Cannot set a setting of type " + this.getClass().getSimpleName() + " as a boolean!");
//        }
//
//        public String getString() {
//            throw new RuntimeException("Cannot get a setting of type " + this.getClass().getSimpleName() + " as a string!");
//        }
//
//        public void setString(String val) {
//            throw new RuntimeException("Cannot set a setting of type " + this.getClass().getSimpleName() + " as a string!");
//        }
//        @Override
//        public T parseValue(String newVal) {
//            throw new RuntimeException("Cannot parse setting of type " + this.getClass().getSimpleName() + " from string!");
//        }
//    }
//
//    public static class WonkyBooleanSetting extends WonkySetting/*<Boolean>  implements ConfigType.SerializableConfig */ {
//        private final ConfigType.BoolConfig figConf;
//
//        public WonkyBooleanSetting(String name, Boolean defaultValue) {
//            super(name);
//            figConf = new ConfigType.BoolConfig(name, CATEGORY, defaultValue);
//        }
//
//        @Override
//        public Boolean getBool() {
//            return figConf.value;
//        }
//
//        @Override
//        public void setBool(Boolean val) {
//            figConf.setValue(String.valueOf(val));
//        }
//        @Override
//        public JsonElement serialize() {
//            Gson gson = new Gson();
//            return gson.toJsonTree(this.getBool());
//        }
//
//        @Override
//        public void deserialize(JsonElement element) {
//                this.setBool(element.getAsBoolean());
//        }
//        @Override
//        public Boolean parseValue(String newVal) {
//            return Boolean.valueOf(newVal);
//        }
//        }
