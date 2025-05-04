package coinbase;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class coinbaseFileRead {
    public static void main(String[] args) throws IOException {
        // Create a Path object for the specified file
        Path filePath = Paths.get("/Users/adityabharadwaj/codeforces/coinbase/example.txt");

        // Read the entire file content into a string
        List<String> content2 = Files.readAllLines(filePath);
        for (String s: content2) {
            System.out.println(s);
        }
        
        String content = Files.readString(filePath);
        String[] lines = content.split("\n");
        List<List<String>> logs = new ArrayList<>();
        for (int i=0; i<lines.length; i++) {
            logs.add(new ArrayList<>());
        }
        for (int i=1; i<lines.length; i++) {
            String tempString = lines[i];
            String[] words = tempString.split(",");
            for (String s: words) {
                logs.get(i).add(s);
            }
        }

        for (List<String> s: logs) {
            System.out.println(s);
        }
    }    
}
