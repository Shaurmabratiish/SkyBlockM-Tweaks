package despairscent.skyblockm.tweaks.keybinds;

import despairscent.skyblockm.tweaks.ModUtils;
import despairscent.skyblockm.tweaks.config.ClothConfigImplementation;
import me.shedaniel.autoconfig.gui.ConfigScreenProvider;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import org.lwjgl.glfw.GLFW;

public class ConfigKeybind extends Keybind{

    public ConfigKeybind() {
        super("keybind.open_config", GLFW.GLFW_KEY_N, "keybind");
    }

    @Override
    public void execute() {
        ModUtils.executeFromMain(() -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.currentScreen != null) return;
            Screen configScreen = ClothConfigImplementation.generate(null);
            client.setScreen(configScreen);
        });
    }

}
