package despairscent.skyblockm.tweaks.modules.notes;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import despairscent.skyblockm.tweaks.files.FileUtils;
import despairscent.skyblockm.tweaks.files.ModFolder;
import despairscent.skyblockm.tweaks.modules.IModuleExecutor;

import java.io.File;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class NotesModule {

    private static File notesFile;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Type NOTES_LIST_TYPE = new TypeToken<List<Note>>(){}.getType();
    public static List<Note> notes = new ArrayList<>();

    public static void init() {
        notesFile = FileUtils.createFile(new ModFolder("SkyBlockM-Tweaks", "notes").getModDir(), "nested.json");
        load();
    }

    public NotesModule() {
        init();
    }

    public static void load() {
        if (Files.exists(notesFile.toPath())) {
            try (Reader reader = Files.newBufferedReader(notesFile.toPath())) {
                List<Note> loadedNotes = GSON.fromJson(reader, NOTES_LIST_TYPE);
                if (loadedNotes != null) {
                    notes = loadedNotes;
                }
            } catch (Exception e) {
                System.err.println("Ошибка при чтении JSON: " + e.getMessage());
            }
        }
    }

    public static void save() {
        try (Writer writer = Files.newBufferedWriter(notesFile.toPath())) {
            GSON.toJson(notes, writer);
        } catch (Exception e) {
            System.err.println("Ошибка при сохранении JSON: " + e.getMessage());
        }
    }

    public static void addNote(String name, Note.NoteStatus status, int color) {
        int newId = 1;

        if (!notes.isEmpty()) {
            Note lastNote = notes.get(notes.size() - 1);
            newId = lastNote.id + 1;
        }

        Note newNote = new Note();
        newNote.id = newId;
        newNote.name = name;
        newNote.status = status;
        newNote.color = color;

        notes.add(newNote);

        save();
    }

    public static boolean removeNote(int id) {
        boolean removed = notes.removeIf(note -> note.id == id);

        if (removed) {
            save();
        }

        return removed;
    }

    public File getNotesFile() {
        return notesFile;
    }
}

