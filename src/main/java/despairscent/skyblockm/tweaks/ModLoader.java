package despairscent.skyblockm.tweaks;

import despairscent.skyblockm.tweaks.config.Config;
import despairscent.skyblockm.tweaks.keybinds.ConfigKeybind;
import despairscent.skyblockm.tweaks.keybinds.Keybind;
import despairscent.skyblockm.tweaks.keybinds.KeybindHandler;
import despairscent.skyblockm.tweaks.modules.compactgenome.CompactGenomeModule;
import despairscent.skyblockm.tweaks.modules.scrolling.EsTerminalScrollModule;
import despairscent.skyblockm.tweaks.modules.inventorydesyncfix.InventoryDesyncFixModule;
import despairscent.skyblockm.tweaks.modules.scrolling.HelpMenuScroll;
import net.fabricmc.api.ClientModInitializer;

import static despairscent.skyblockm.tweaks.ModUtils.CONFIG;

public class ModLoader implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        CONFIG = Config.load();
        CONFIG.save();

        CompactGenomeModule.init();
        HelpMenuScroll.init();
        EsTerminalScrollModule.init();
        InventoryDesyncFixModule.init();


        /// кейбинды
        KeybindHandler.init();
        new ConfigKeybind().register();

    }

}
