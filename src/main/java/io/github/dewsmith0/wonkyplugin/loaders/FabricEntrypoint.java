//? fabric {
package io.github.dewsmith0.wonkyplugin.loaders;

import io.github.dewsmith0.wonkyplugin.WonkyPluginClient;
import net.fabricmc.api.ClientModInitializer;

public class FabricEntrypoint implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WonkyPluginClient.initialize();
    }
}
//?}