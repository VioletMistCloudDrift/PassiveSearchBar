package team.dovecotmc.passivesearchbar.forge.mixin;

import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreativeScreen.class)
public class MixinCreativeModelInventoryScreen {

    @Shadow
    private TextFieldWidget searchBox;

    @Redirect(method = "selectTab", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;setCanLoseFocus(Z)V"))
    private void passivesearchbar$redirect$selectTab$1(TextFieldWidget instance, boolean b) {
        instance.setCanLoseFocus(true);
    }

    @Redirect(method = "selectTab", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;setFocus(Z)V"))
    private void passivesearchbar$redirect$selectTab$2(TextFieldWidget instance, boolean b) {
        instance.setFocus(false);
    }

    @Inject(method = "mouseClicked", at = @At("RETURN"))
    private void passivesearchbar$inject$mouseClicked(double d, double e, int i, CallbackInfoReturnable<Boolean> cir) {
        if (!searchBox.mouseClicked(d, e, i)) searchBox.setFocus(false);
    }
}
