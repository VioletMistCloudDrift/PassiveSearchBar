package team.dovecotmc.passivesearchbar.fabric.mixin;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreativeModeInventoryScreen.class)
public class MixinCreativeModelInventoryScreen {
    @Shadow
    private EditBox searchBox;

    @Redirect(method = "selectTab", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/EditBox;setCanLoseFocus(Z)V"))
    private void passivesearchbar$redirect$selectTab$1(EditBox instance, boolean bl) {
        instance.setCanLoseFocus(true);
    }

    @Redirect(method = "selectTab", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/EditBox;setFocused(Z)V"))
    private void passivesearchbar$redirect$selectTab$2(EditBox instance, boolean bl) {
        instance.setFocused(false);
    }

    @Inject(method = "mouseClicked", at = @At("RETURN"))
    private void passivesearchbar$inject$mouseClicked(double d, double e, int i, CallbackInfoReturnable<Boolean> cir) {
        if (!searchBox.mouseClicked(d, e, i)) searchBox.setFocused(false);
    }

    @Inject(method = "hasClickedOutside", at = @At("RETURN"))
    private void passivesearchbar$inject$hasClickedOutside(double d, double e, int i, int j, int k, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) searchBox.setFocused(false);
    }
}
