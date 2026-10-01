package io.github.dewsmith0.wonkyplugin.mixin;

import io.github.dewsmith0.wonkyplugin.duck.AvatarExtensions;
import io.github.dewsmith0.wonkyplugin.lua.WonkyWebSocket;
import org.figuramc.figura.avatar.Avatar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(value = Avatar.class, remap = false)
public class AvatarMixin implements AvatarExtensions  {
    @Unique
    public final ArrayList<WonkyWebSocket> wonky$openWebSockets = new ArrayList<>();
    @Override
    public ArrayList<WonkyWebSocket> wonky$getOpenWebSockets() {
        return wonky$openWebSockets;
    }
    @Inject(method = "clean", at = @At(value = "TAIL"))
    public void clean(CallbackInfo ci) {
        for (WonkyWebSocket socket : wonky$openWebSockets) {
            socket.forceClose();
        }
    }
}
