package despairscent.skyblockm.tweaks.files;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModFolder {

    private final Path MOD_DIR;

    public ModFolder(String folderName) {
       MOD_DIR =  FabricLoader.getInstance().getGameDir().resolve(folderName);
       init();
    }

    public ModFolder(String folderName, String... nested) {
        Path path =  FabricLoader.getInstance().getGameDir().resolve(folderName);

        for (String s : nested) {
            path = path.resolve(s);
        }

        MOD_DIR = path;

        init();
    }

    private void init() {
        if (!Files.exists(MOD_DIR)) {
            try {
                Files.createDirectory(MOD_DIR);
            } catch (Exception e) {
                assert MinecraftClient.getInstance().player != null;
                MinecraftClient.getInstance().player.sendMessage(Text.of(e.getMessage()));
            }
        }
    }

    public Path getModDir() {
        return MOD_DIR;
    }

}
