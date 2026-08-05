package net.wisecase2.stutterfix.gui;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.Options;
import net.minecraft.client.OptionInstance;
import net.minecraft.network.chat.Component;
import net.wisecase2.stutterfix.StutterFix;

@OnlyIn(Dist.CLIENT)
public class StutterFixOptionsGUI extends OptionsSubScreen {

    public StutterFixOptionsGUI(Screen parent, Options options) {
        super(parent, options, Component.translatable("stutterfix.options.name"));
    }

    @Override
    protected void addOptions() {
        int max_threads_count = Runtime.getRuntime().availableProcessors();
        int default_vanilla = StutterFix.getDefaultVanillaMainWorkerExecutorCount();
        int default_stutterfix = StutterFix.getDefaultStutterFixMainWorkerExecutorCount();
        int defaultRenderThreadPriority = (max_threads_count > 4)? 10:5;
        int defaultServerThreadPriority = (max_threads_count > 4)? 8:5;

        // Worker threads count
        this.list.addBig(new OptionInstance<>("stutterfix.options.worker_threads",
                OptionInstance.cachedConstantTooltip(Component.translatable("stutterfix.options.worker_threads.tooltip", default_vanilla, default_stutterfix)),
                (component, integer) -> Options.genericValueLabel(component, Component.translatable("stutterfix.options.threads", integer)),
                new OptionInstance.IntRange(1, max_threads_count),
                StutterFix.threadconfig.mainWorkerExecutorCount,
                (value) -> { 
                    StutterFix.threadconfig.mainWorkerExecutorCount = value; 
                    StutterFix.loadMainWorkerExecutor(); 
                }));

        // Worker threads priority cut
        this.list.addBig(new OptionInstance<>("stutterfix.options.Worker_threads_priority_cut",
                OptionInstance.cachedConstantTooltip(Component.translatable("stutterfix.options.Worker_threads_priority_cut.tooltip")),
                (component, integer) -> Options.genericValueLabel(component, Component.translatable("stutterfix.options.priority_cut", integer)),
                new OptionInstance.IntRange(0, max_threads_count),
                StutterFix.threadconfig.mainWorkerExecutorPriorityCut,
                (value) -> { 
                    StutterFix.threadconfig.mainWorkerExecutorPriorityCut = value; 
                    StutterFix.loadMainWorkerExecutor(); 
                }));

        // Render thread priority
        if(StutterFix.isInitializedRenderThread) {
            this.list.addBig(new OptionInstance<>("stutterfix.options.render_thread_priority",
                    OptionInstance.cachedConstantTooltip(Component.translatable("stutterfix.options.render_thread_priority.tooltip", defaultRenderThreadPriority)),
                    (component, integer) -> Options.genericValueLabel(component, Component.translatable("stutterfix.options.thread_priority", integer)),
                    new OptionInstance.IntRange(1, 10),
                    StutterFix.threadconfig.renderThreadPriority,
                    (value) -> { 
                        StutterFix.threadconfig.renderThreadPriority = value; 
                        StutterFix.configPriorityRenderThread(); 
                    }));
        }

        // Server thread priority
        if(StutterFix.isInitializedServerThread) {
            this.list.addBig(new OptionInstance<>("stutterfix.options.server_thread_priority",
                    OptionInstance.cachedConstantTooltip(Component.translatable("stutterfix.options.server_thread_priority.tooltip", defaultServerThreadPriority)),
                    (component, integer) -> Options.genericValueLabel(component, Component.translatable("stutterfix.options.thread_priority", integer)),
                    new OptionInstance.IntRange(1, 10),
                    StutterFix.threadconfig.serverThreadPriority,
                    (value) -> { 
                        StutterFix.threadconfig.serverThreadPriority = value; 
                        StutterFix.configPriorityServerThread(); 
                    }));
        }

        // Remove yield option
        if(StutterFix.removeYieldOption) {
            this.list.addBig(OptionInstance.createBoolean("stutterfix.options.remove_yield",
                    OptionInstance.cachedConstantTooltip(Component.translatable("stutterfix.options.remove_yield.tooltip")),
                    (component, bool) -> bool ? Component.translatable("stutterfix.options.remove") : Component.translatable("stutterfix.options.keep"),
                    StutterFix.threadconfig.renderRemoveYield,
                    (value) -> { StutterFix.threadconfig.renderRemoveYield = value; }));
        }
    }

    @Override
    public void onClose() {
        StutterFix.saveThread.execute(() -> { StutterFix.threadconfig.saveConfig(); });
        super.onClose();
    }
}