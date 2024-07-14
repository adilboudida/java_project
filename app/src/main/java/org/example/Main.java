import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java -jar task.jar <inputFile> <semitone> <outputFile>");
            return;
        }

        String inputFile = args[0];
        int semitone = Integer.parseInt(args[1]);
        String outputFile = args[2];

        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<int[]>>() {}.getType();
            List<int[]> notes = gson.fromJson(new FileReader(inputFile), listType);

            for (int[] note : notes) {
                transpose(note, semitone);
                if (note[0] < -3 || note[0] > 5 || note[1] < 1 || note[1] > 12) {
                    System.out.println("Error: Note out of range");
                    return;
                }
            }

            FileWriter writer = new FileWriter(outputFile);
            gson.toJson(notes, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void transpose(int[] note, int semitone) {
        int totalNotes = note[0] * 12 + note[1] - 1 + semitone;
        note[0] = totalNotes / 12;
        note[1] = totalNotes % 12 + 1;
    }
}
