package io.github.dewsmith0.wonkyplugin.mixin;

import io.github.dewsmith0.wonkyplugin.WonkyPlugin;
import io.github.dewsmith0.wonkyplugin.config.Config;
import org.figuramc.figura.config.Configs;
import org.figuramc.figura.lua.api.net.NetworkingAPI;
import org.luaj.vm2.LuaError;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

@Mixin(value = NetworkingAPI.class, remap = false)
public class NetworkingAPIMixin {
    @Inject(method = "isLinkAllowed", at = @At(value = "INVOKE", target = "Ljava/net/URL;getPort()I"), cancellable = true)
    public void wonky$isLinkAllowed1(String link, CallbackInfoReturnable<Boolean> cir) {
        if (Config.ALLOW_MORE_PORTS.value) {
            try {
                URL url = new URL(wonky$isLinkAllowed2(link));
                if (url.getPort() >= 13114 && url.getPort() <= 13124) {
                    ArrayList<NetworkingAPI.Filter> filters = Configs.NETWORK_FILTER.getFilters();
                    NetworkingAPI.RestrictionLevel level = NetworkingAPI.RestrictionLevel.getById(Configs.NETWORKING_RESTRICTION.value);
                    WonkyPlugin.LOGGER.debug("Allowed connection to port {}", url.getPort());
                    cir.setReturnValue(switch (level) {
                        case NetworkingAPI.RestrictionLevel.WHITELIST ->
                                filters.stream().anyMatch(f -> f.matches(url.getHost()));
                        case NetworkingAPI.RestrictionLevel.BLACKLIST ->
                                filters.stream().noneMatch(f -> f.matches(url.getHost()));
                        case NetworkingAPI.RestrictionLevel.NONE -> true;
                    });
                }
            } catch (MalformedURLException e) {
                throw new LuaError("WonkyPlugin failed to parse the URL [%s], even though it already was parsed before?".formatted(link));
            }
        }
    }
    @ModifyArg(method = "isLinkAllowed", at = @At(value = "INVOKE", target = "Ljava/net/URL;<init>(Ljava/lang/String;)V"))
    private String wonky$isLinkAllowed2(String link) {
        String urlForValidation = link;
        if (urlForValidation.regionMatches(true, 0, "ws://", 0, 5))
            urlForValidation = "http://" + urlForValidation.substring(5);
        else if (urlForValidation.regionMatches(true, 0, "wss://", 0, 6))
            urlForValidation = "https://" + urlForValidation.substring(6);
        return urlForValidation;
    }
}
