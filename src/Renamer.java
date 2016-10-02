import java.io.File;
import java.util.Scanner;

public class Renamer {
    // para modificar o nome dos ficheiros

    public static void main(String[] args)
    {
        /*
        if(args.length != 1)
        {
            System.out.println("Proper Usage is: JavaRenamer path");
            System.exit(0);
        }
        */

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter folder path: ");
        String folderPath = scanner.next();

        //File folder = new File(args[0]);
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }


        for (File file : listOfFiles)
            if (file.getName().contains(".dat"))
                file.renameTo(new File(file.getParentFile(), file.getName().replace(".dat", ".html")));
    }
}
