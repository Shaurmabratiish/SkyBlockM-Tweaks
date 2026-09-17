package despairscent.skyblockm.tweaks.keybinds;

import despairscent.skyblockm.tweaks.ModUtils;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;

public abstract class Keybind {

    private final String key;
    private final int button;
    private final String category;

    private KeyBinding keyBinding;

    public Keybind(String key, int button, String category) {
        this.key = ModUtils.i18n(key).getString();
        this.button = button;
        this.category = ModUtils.i18n(category).getString();
    }

    public abstract void execute();

    public KeyBinding getKeyBinding() {
        if (this.keyBinding == null) {
            this.keyBinding = new KeyBinding(this.key, this.button, this.category);
        }
        return this.keyBinding;
    }

    public void register() {
        KeyBindingHelper.registerKeyBinding(this.getKeyBinding());
        KeybindHandler.keybinds.add(this);
    }
}