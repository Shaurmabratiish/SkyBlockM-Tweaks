package despairscent.skyblockm.tweaks.keybinds;

import despairscent.skyblockm.tweaks.ModUtils;
import despairscent.skyblockm.tweaks.config.ClothConfigImplementation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class HomeKeybind  extends Keybind{

    public HomeKeybind() {
        super("keybind.to_home", GLFW.GLFW_KEY_Z, "keybind");
    }

    @Override
    public void execute() {
        ModUtils.executeFromMain(() -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.currentScreen != null) return;
            Objects.requireNonNull(client.getNetworkHandler()).sendChatCommand("is home");
        });
    }
}
