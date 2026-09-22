package io.github.dewsmith0.wonkyplugin;

import io.github.dewsmith0.wonkyplugin.config.Config;
import net.fabricmc.api.ClientModInitializer;

public class WonkyPluginClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Config.init();

    }
}
