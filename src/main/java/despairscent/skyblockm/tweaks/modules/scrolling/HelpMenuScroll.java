package despairscent.skyblockm.tweaks.modules.scrolling;

import despairscent.skyblockm.tweaks.ModUtils;
import despairscent.skyblockm.tweaks.config.Config;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;

import static despairscent.skyblockm.tweaks.ModUtils.CLIENT;
import static despairscent.skyblockm.tweaks.ModUtils.CONFIG;

public class HelpMenuScroll {

    private static int tick;
    private static int lastClickAt;
    private static int clickedPerTick;

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (CONFIG.helpMenuScrollConfig.enabled) {
                if (ModUtils.isKeyPressed(CONFIG.helpMenuScrollConfig.keyUp)) {
                    doScrollUp(false);
                } else if (ModUtils.isKeyPressed(CONFIG.helpMenuScrollConfig.keyDown)) {
                    doScrollDown(false);
                }
            }

            ++tick;
        });
    }

    public static boolean doScrollDown(boolean wheel) {
        return sendClick(false, wheel);
    }

    public static boolean doScrollUp(boolean wheel) {
        return sendClick(true, wheel);
    }

    private static boolean sendClick(boolean up, boolean wheel) {

        if (MinecraftClient.getInstance().player == null) return false;


        if (!(CLIENT.currentScreen instanceof HandledScreen<?> screen)) {
            return false;
        }
        int slot;
        if (ModUtils.testCustomScreen(true, screen, "guide:interfaces", "\u1000")) {
            slot = up ? 54 : 62;
        } else {
            return false;
        }
        if (lastClickAt != tick) {
            clickedPerTick = 0;
        }
        int limit = wheel ? CONFIG.helpMenuScrollConfig.actionLimitWheel : CONFIG.helpMenuScrollConfig.actionLimitKey;
        if (limit > 0 ?
                tick - lastClickAt > limit :
                clickedPerTick < Math.max(1, -limit)) {
            boolean boost = ModUtils.isKeyPressed(CONFIG.helpMenuScrollConfig.boostKey) ^ (CONFIG.helpMenuScrollConfig.boostKeyType == Config.HelpMenuScrollBoostKeyType.DEACTIVATE);
            CLIENT.interactionManager.clickSlot(screen.getScreenHandler().syncId, slot, 0, boost ? SlotActionType.QUICK_MOVE : SlotActionType.PICKUP, CLIENT.player);
            lastClickAt = tick;
            ++clickedPerTick;
        }

        return true;
    }
}
