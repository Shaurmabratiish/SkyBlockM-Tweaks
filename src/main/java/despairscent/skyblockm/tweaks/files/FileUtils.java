package despairscent.skyblockm.tweaks.files;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileUtils {

    public static boolean writeToFile(Path folder, String fileName, String content) {

        Path filePath = folder.resolve(fileName);

        try {
            Files.writeString(filePath, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return true;
        }catch (Exception e) {
            assert MinecraftClient.getInstance().player != null;
            MinecraftClient.getInstance().player.sendMessage(Text.of(e.getMessage()));
            return false;
        }
    }

    public static File createFile(Path folder, String fileName) {
        Path filePath = folder.resolve(fileName);
        try {
            if (!Files.exists(folder)) {
                Files.createDirectories(folder);
            }

            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }

            return filePath.toFile();

        } catch (Exception e) {
            assert MinecraftClient.getInstance().player != null;
            MinecraftClient.getInstance().player.sendMessage(Text.of("Ошибка создания файла: " + e.getMessage()));

            return null;
        }
    }

}
