package io.github.dewsmith0.wonkyplugin.mixin;

import io.github.dewsmith0.wonkyplugin.WonkyPermissions;
import org.figuramc.figura.lua.FiguraLuaPrinter;
import org.figuramc.figura.lua.FiguraLuaRuntime;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FiguraLuaPrinter.class, remap = false, priority = 500)
public class FiguraLuaPrinterMixin {
    @Inject(method = "lambda$static$1", at = @At(value = "RETURN"), cancellable = true)
    private static void wonky$printJsonPermission(FiguraLuaRuntime runtime, CallbackInfoReturnable<LuaValue> cir) {
        // thanks sillyplugin
        LuaValue orig = cir.getReturnValue();
        LuaFunction func = orig.checkfunction();
        cir.setReturnValue(new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                if (runtime.owner.permissions.get(WonkyPermissions.PRINT_JSON) == 1)
                    return func.invoke(args);
                runtime.owner.noPermissions.add(WonkyPermissions.PRINT_JSON);
                return LuaValue.NIL;
            }
        });
    }
}
