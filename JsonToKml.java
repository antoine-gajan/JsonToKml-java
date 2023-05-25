import org.json.*;
import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class JsonToKml {

    public static String create_point(JSONObject point){
        // Get latitude and long of point
        int id = point.getInt("id");
        float lat = point.getFloat("lat");
        float lng = point.getFloat("lng");
        // Format latitude and longitude
        DecimalFormat lat_format = new DecimalFormat("#.######", new DecimalFormatSymbols(Locale.US));
        String lat_string = lat_format.format(lat);
        String lng_string = lat_format.format(lng);
        // Create KML Point element
        return String.format("\t<Placemark id=\"%d\">\n" +
                "\t<name>%s</name>\n" +
                "\t<Point>\n" +
                "\t\t<coordinates> %s,%s,0.0 </coordinates>\n" +
                "\t</Point>\n" +
                "\t</Placemark>\n", id, id, lng_string, lat_string);
    }
    public void JsonToKml(String json_file) throws IOException {
        // Open json file
        BufferedReader reader = new BufferedReader(new FileReader(json_file));
        String json = "";
        String line;
        while ((line = reader.readLine()) != null) {
            json += line;
        }
        reader.close();
        // Convert it as an array
        JSONArray coordonnees = new JSONArray(json);

        // Create a KML file
        FileWriter output_file = new FileWriter(String.format("%s.kml", json_file.split(".json")[0]));

        // Header of file
        String kmlstart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\">\n"+
                "\t<Document>\n";

        // Append header to file
        output_file.write(kmlstart);

        // Parse JSON file
        for (int i = 0; i < coordonnees.length(); i++) {
            JSONObject point = coordonnees.getJSONObject(i);
            // Create point
            String element = create_point(point);
            // Add to kml file
            output_file.write(element);
        }
        // End of file
        String kmlend = "\t</Document>\n</kml>";
        // Append end of file
        output_file.write(kmlend);
        // Close file
        output_file.close();
        System.out.println(String.format("%s.kml a été créé avec succès !", json_file.split(".json")[0]));

    }

    public static void main(String[] args) {
        if (args.length != 2){
            System.out.println("Le nombre d'arguments fourni n'est pas correct !");
            System.exit(1);
        }
        // Json file to convert
        String json_file = args[1];
        // JsonToKml the json file
        JsonToKml main = new JsonToKml();
        try {
            main.JsonToKml(json_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}