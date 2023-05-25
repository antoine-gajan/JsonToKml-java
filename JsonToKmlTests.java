import java.io.IOException;

public class JsonToKmlTests {
    public static void main(String[] args) {
        JsonToKml main = new JsonToKml();
        try {
            // Convert all files
            for (int i = 1; i <= 5; i++){
                String json_file = String.format("src/traj%d.json", i);
                main.JsonToKml(json_file);
            }
        }
        // If error occurs
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
