import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class myFileIO {
    public static List<String> toStringList(String filePath) {
        try {
            BufferedReader bfReader = new BufferedReader(new FileReader(filePath));
            String line = null;
            List<String> stringList = new LinkedList<>();
            while((line = bfReader.readLine()) != null) {
                stringList.add(line);
            }
            bfReader.close();
            return stringList;
        } catch(IOException ex) {
            System.out.println("Failed to read " + filePath);
            ex.printStackTrace();
        }
        return null;
    }

    public static void writeToFile(String filePath, List<String> stringList) {
        try {
            PrintWriter pw = new PrintWriter(filePath);
            for(String line : stringList) {
                pw.println(line);
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
