package neko.violetmist.passivesearchbar.mixin;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainerCreative.class)
public abstract class MixinGuiContainerCreative {
    @Shadow
    private GuiTextField searchField;

    @Shadow
    private boolean clearSearch;

    @Redirect(method = "setCurrentCreativeTab", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiTextField;setCanLoseFocus(Z)V"))
    private void passivesearchbar$redirect$setCurrentCreativeTab$1(GuiTextField instance, boolean canLoseFocusIn) {
        instance.setCanLoseFocus(true);
    }

    @Redirect(method = "setCurrentCreativeTab", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiTextField;setFocused(Z)V"))
    private void passivesearchbar$redirect$setCurrentCreativeTab$2(GuiTextField instance, boolean isFocusedIn) {
        instance.setFocused(false);
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"))
    private void passivesearchbar$inject$mouseClicked(int mouseX, int mouseY, int mouseButton, CallbackInfo ci) {
        searchField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Inject(method = "handleMouseClick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/inventory/GuiContainerCreative;clearSearch:Z", opcode = Opcodes.PUTFIELD))
    private void passivesearchbar$inject$handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type, CallbackInfo ci) {
        clearSearch = false;
    }
}
