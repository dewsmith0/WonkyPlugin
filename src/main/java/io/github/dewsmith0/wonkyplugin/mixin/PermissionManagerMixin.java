package io.github.dewsmith0.wonkyplugin.mixin;

import io.github.dewsmith0.wonkyplugin.WonkyPermissions;
import org.figuramc.figura.permissions.PermissionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PermissionManager.class, remap = false)
public class PermissionManagerMixin {
    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lorg/figuramc/figura/utils/IOUtils;readCacheFile(Ljava/lang/String;Ljava/util/function/Consumer;)V"))
    private static void wonky$addPermissions(CallbackInfo ci) {
        PermissionManager.CUSTOM_PERMISSIONS.put("wonkyplugin", WonkyPermissions.PERMISSIONS);
    }
}