package io.github.dewsmith0.wonkyplugin.config;

import io.github.dewsmith0.wonkyplugin.WonkyPlugin;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import org.figuramc.figura.config.ConfigManager;
import org.figuramc.figura.config.ConfigType;

public class Config {
    public static final ConfigType.Category CATEGORY = category("wonkyplugin");
    private static ConfigType.Category category(String name) {
        return new ConfigType.Category(name) {{
           this.name = Component.literal("\uE051")
                    .withStyle(Style.EMPTY.withFont(ResourceLocation.fromNamespaceAndPath("figura", "emoji_portrait")))
                    .append(this.name.copy().withStyle(Style.EMPTY.withFont(Style.DEFAULT_FONT).withColor(WonkyPlugin.COLOR.getRGB())));
        }};
    }
    public static final ConfigType.EnumConfig NET_ALLOWED_PORTS;
    static {
        NET_ALLOWED_PORTS = new ConfigType.EnumConfig("net_allowed_ports", CATEGORY, 0, 4);
    }

    public static void init() {
        ConfigManager.REGISTRY.add(NET_ALLOWED_PORTS);
    }
}