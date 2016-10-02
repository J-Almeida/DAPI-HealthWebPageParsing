import java.io.*;
import java.util.Scanner;

/**
 * Created by João on 12/11/2015.
 */
public final class FileSplitter {

    public static void main(String[] args) throws IOException {
        //FileTrimmer.trim();
        FileSplitter.split();
    }

    public static void split() throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter file path: ");
        String filePath = scanner.next();

        File inputFile = new File(filePath);

        System.out.println("Splitting text file" + inputFile.getAbsolutePath());

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));


        Integer file = 1;
        File tempFile;
        tempFile = new File(inputFile.getParentFile().getAbsolutePath() + "\\split\\" + file.toString() + ".html");
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));


        String currentLine;
        Integer line = 1;
        while ((currentLine = reader.readLine()) != null) {

            writer.write(currentLine + System.getProperty("line.separator"));

            if (currentLine.contains("#EOR")) {
                System.out.println("Closing out file #" + file + " at line #" + line + "\n");
                writer.close();
                file++;
                tempFile = new File(inputFile.getParentFile().getAbsolutePath() + "\\split\\" + file.toString() + ".html");
                writer = new BufferedWriter(new FileWriter(tempFile)); // desnecessário?
            }

            line++;

        }

        writer.close();
        reader.close();
        // boolean successful = tempFile.renameTo(inputFile);
        // System.out.println(successful);
    }
}
