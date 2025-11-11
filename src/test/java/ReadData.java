import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

public class ReadData {
    public static void main(String[] args) throws IOException {
        String filePath = "src/main/resources/title.akas.tsv.gz";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(filePath))))) {

            String line;
            int count = 0;

            // Skip header
//            br.readLine();

            System.out.println(br.readLine());

            while ((line = br.readLine()) != null && count < 5000) {
                String[] parts = line.split("\t");
                if (Objects.equals(parts[3], "US")) {

                    System.out.println(Arrays.toString(parts)); // Example columns
                    count++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
