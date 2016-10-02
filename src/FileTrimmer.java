import java.io.*;
import java.util.Scanner;

/**
 * Created by João on 04/12/2015.
 */
public class FileTrimmer {

    public static void main(String[] args) throws IOException {
        FileTrimmer.trim();
    }

    public static void trim() throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter folder path: ");
        String folderPath = scanner.next();

        //File folder = new File(args[0]);
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();

        /*
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
        */

        if (listOfFiles.length != 0)
            for (File file : listOfFiles)
                if (file.isFile())
                    cutTextFile(file, folder, 50000);
    }

    public static void cutTextFile(File inputFile, File folder, Integer maxLines) throws IOException {
        System.out.println("cutting text file " + inputFile.getAbsolutePath());
        File tempFile = new File(folder.getAbsolutePath() + "\\cut\\" + inputFile.getName());

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;
        Integer line = 1;

        while ((currentLine = reader.readLine()) != null && (line++ < maxLines)) {
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        // boolean successful = tempFile.renameTo(inputFile);
        // System.out.println(successful);
    }

}
