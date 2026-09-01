package net.wisecase2.stutterfix.mixin.gui;

import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.network.chat.Component;
import net.wisecase2.stutterfix.StutterFix;
import net.wisecase2.stutterfix.gui.StutterFixOptionsGUI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin extends Screen {

    @Shadow
    private Options options;

    @Shadow
    private HeaderAndFooterLayout layout;

    protected OptionsScreenMixin(Component title) {
        super(title);
    }

    @Shadow
    protected abstract Button openScreenButton(Component message, Supplier<Screen> screenSupplier);

    @Inject(method = "init()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/layouts/HeaderAndFooterLayout;visitWidgets(Ljava/util/function/Consumer;)V", shift = At.Shift.BEFORE))
    private void inject_StutterFixOption(CallbackInfo ci) {
        if (!StutterFix.threadconfig.hideGui) {
            if (this.layout != null) {
                Button button = this.openScreenButton(Component.translatable("stutterfix.name"), () -> {
                    return new StutterFixOptionsGUI((Screen) (Object) this, this.options);
                });

                this.layout.addToContents(button);
            }
        }
    }
}