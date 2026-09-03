package net.wisecase2.stutterfix.mixin.gui;

import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.network.chat.Component;
import net.wisecase2.stutterfix.StutterFix;
import net.wisecase2.stutterfix.gui.StutterFixOptionsGUI;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.Supplier;

@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin extends Screen {

    @Final
    @Shadow
    private Options options;

    protected OptionsScreenMixin(Component title) {
        super(title);
    }

    @Shadow
    protected abstract Button openScreenButton(Component message, Supplier<Screen> screenSupplier);

    @Inject(
            method = "init()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/layouts/GridLayout$RowHelper;addChild(Lnet/minecraft/client/gui/layouts/LayoutElement;)Lnet/minecraft/client/gui/layouts/LayoutElement;",
                    ordinal = 0,
                    shift = At.Shift.BEFORE
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void inject_StutterFixOption(CallbackInfo ci, LinearLayout linearlayout, LinearLayout linearlayout1, GridLayout gridlayout, GridLayout.RowHelper rowhelper) {
        if (!StutterFix.threadconfig.hideGui) {
            Button button = this.openScreenButton(Component.translatable("stutterfix.name"), () -> {
                return new StutterFixOptionsGUI((Screen) (Object) this, this.options);
            });

            rowhelper.addChild(button, 2);
        }
    }
}