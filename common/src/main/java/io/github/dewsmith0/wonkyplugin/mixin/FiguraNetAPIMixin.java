package io.github.dewsmith0.wonkyplugin.mixin;

import io.github.dewsmith0.wonkyplugin.WonkyPlugin;
import io.github.dewsmith0.wonkyplugin.config.Config;
import org.figuramc.figura.lua.api.net.NetworkingAPI;
import org.luaj.vm2.LuaError;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.MalformedURLException;
import java.net.URL;

@Mixin(value = NetworkingAPI.class, remap = false)
public class FiguraNetAPIMixin {
    @Inject(method = "isLinkAllowed", at = @At(value = "INVOKE", target = "Ljava/net/URL;getPort()I"), cancellable = true)
    public void wonky$isLinkAllowed(String link, CallbackInfoReturnable<Boolean> cir) {
        if (Config.ALLOW_MORE_PORTS.value) {
            try {
                URL url = new URL(link);
                if (url.getPort() >= 13114 && url.getPort() <= 13124) {
                    cir.setReturnValue(true);
                    WonkyPlugin.LOGGER.debug("Allowed connection to port {}", url.getPort());
                }
            } catch (MalformedURLException e) {
                throw new LuaError("WonkyPlugin failed to parse the URL [%s], even though it already was parsed before?".formatted(link));
            }
        }
    }
}
