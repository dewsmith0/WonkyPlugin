package io.github.dewsmith0.wonkyplugin.mixin;

import io.github.dewsmith0.wonkyplugin.config.Config;
import org.figuramc.figura.config.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Configs.class, remap = false)
public class ConfigsMixin {
    @Inject(method = "init", at = @At(value = "TAIL"))
    private static void init(CallbackInfo ci) {
        Config.init();
    }
}
