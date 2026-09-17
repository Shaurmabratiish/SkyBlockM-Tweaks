package despairscent.skyblockm.tweaks.keybinds;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import java.util.ArrayList;
import java.util.List;

public class KeybindHandler {

    public static List<Keybind> keybinds = new ArrayList<>();

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!keybinds.isEmpty()) {
                for (Keybind bind : keybinds) {
                    while (bind.getKeyBinding().wasPressed()) {
                        bind.execute();
                    }
                }
            }
        });
    }

}