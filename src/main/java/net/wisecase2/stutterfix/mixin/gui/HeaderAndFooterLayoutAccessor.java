package net.wisecase2.stutterfix.mixin.gui;

import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HeaderAndFooterLayout.class)
public interface HeaderAndFooterLayoutAccessor {
    @Accessor("header")
    FrameLayout getHeader();

    @Accessor("footer")
    FrameLayout getFooter();

    // En 1.21.1 el campo se llama "body" y es un FrameLayout
    @Accessor("body")
    FrameLayout getBody();
}