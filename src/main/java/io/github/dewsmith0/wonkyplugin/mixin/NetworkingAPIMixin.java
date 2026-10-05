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
import java.util.List;

@Mixin(value = NetworkingAPI.class, remap = false)
public class NetworkingAPIMixin {
    @Inject(method = "isLinkAllowed", at = @At(value = "INVOKE", target = "Ljava/net/URL;getPort()I"), cancellable = true)
    public void wonky$isLinkAllowed1(String link, CallbackInfoReturnable<Boolean> cir) {
        try {
            URL url = new URL(wonky$isLinkAllowed2(link));
            ArrayList<Integer> allowedPorts = new ArrayList<>();
            boolean allowAll = false;
            if (Config.NET_ALLOWED_PORTS.value >= 1)/* DEDICATED */
                allowedPorts.addAll(List.of(13114, 13115, 13116, 13117, 13118, 13119, 13120, 13121, 13122, 13123, 13124));
            if (Config.NET_ALLOWED_PORTS.value >= 2)  /* ALTERNATIVE */
                allowedPorts.addAll(List.of(3000, 8080, 8081, 8082, 8083, 8084, 8085, 8086, 8087, 8088, 8089, 8090));
            if (Config.NET_ALLOWED_PORTS.value == 3) allowAll = true;
            if (allowAll || allowedPorts.contains(url.getPort())) {
                ArrayList<NetworkingAPI.Filter> filters = Configs.NETWORK_FILTER.getFilters();
                NetworkingAPI.RestrictionLevel level = NetworkingAPI.RestrictionLevel.getById(Configs.NETWORKING_RESTRICTION.value);
                WonkyPlugin.LOGGER.debug("Allowed connection to port {}", url.getPort());
                cir.setReturnValue(switch (level) {
                    case WHITELIST -> filters.stream().anyMatch(f -> f.matches(url.getHost()));
                    case BLACKLIST -> filters.stream().noneMatch(f -> f.matches(url.getHost()));
                    case NONE -> true;
                });
            }
        } catch (MalformedURLException e) {
            throw new LuaError("WonkyPlugin failed to parse the URL [%s], even though it already was parsed before?".formatted(link));
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
    @ModifyArg(method = "isLinkAllowed", at = @At(value = "INVOKE", target = "Lorg/luaj/vm2/LuaError;<init>(Ljava/lang/String;)V"))
    private String wonky$clarifyException(String message) {
        int port = Integer.parseInt(message.split("\\s+")[1]);
        return "Port %s not allowed, check WonkyPlugin's \"Allowed NetAPI ports\" setting.".formatted(port);
    }
}