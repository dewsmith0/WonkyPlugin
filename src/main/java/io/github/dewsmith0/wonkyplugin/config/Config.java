package io.github.dewsmith0.wonkyplugin.config;

import io.github.dewsmith0.wonkyplugin.WonkyPlugin;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import org.figuramc.figura.config.ConfigManager;
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
        ALLOW_MORE_PORTS = new ConfigType.BoolConfig("allow_more_ports", CATEGORY, false);
    }

    public static void init() {
        ConfigManager.REGISTRY.add(ALLOW_MORE_PORTS);
    }
}