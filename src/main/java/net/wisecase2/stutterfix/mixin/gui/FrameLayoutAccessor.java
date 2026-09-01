package net.wisecase2.stutterfix.mixin.gui;

import net.minecraft.client.gui.layouts.FrameLayout;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import java.util.List;

@Mixin(FrameLayout.class)
public interface FrameLayoutAccessor {
    @Accessor("elements")
    List<FrameLayout.ChildContainer> getElements();
}