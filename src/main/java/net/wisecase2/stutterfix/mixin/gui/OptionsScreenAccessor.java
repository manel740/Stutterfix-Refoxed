package net.wisecase2.stutterfix.mixin.gui;

import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(OptionsScreen.class)
public interface OptionsScreenAccessor {
    @Accessor("layout")
    HeaderAndFooterLayout getLayout();
}