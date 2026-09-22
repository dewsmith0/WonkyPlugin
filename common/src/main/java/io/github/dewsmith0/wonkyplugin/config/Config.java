package io.github.dewsmith0.wonkyplugin.config;

import io.github.dewsmith0.wonkyplugin.WonkyPlugin;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import org.figuramc.figura.config.ConfigType;

public class Config {
    private static final ConfigType.Category CATEGORY = new ConfigType.Category("wonkyplugin") {{
        this.name = Component.literal("\uE051")
            .withStyle(Style.EMPTY.withFont(ResourceLocation.fromNamespaceAndPath("figura", "emoji_portrait")))
                .append(Component.literal(" wonkyplugin").withStyle(Style.EMPTY.withFont(Style.DEFAULT_FONT).withColor(WonkyPlugin.COLOR.getRGB())));
    }};

    public static final ConfigType.BoolConfig ALLOW_MORE_PORTS = new ConfigType.BoolConfig("allow_more_ports", CATEGORY, false);

    public static void init() {}
}
