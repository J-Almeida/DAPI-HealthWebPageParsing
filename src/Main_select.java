import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;
import org.jsoup.safety.Whitelist;

public class Main_select
{
    static String rawFolder_1 = "./dataset/test/";
    static String processedFolder_1 = "./dataset/test/processed_1/";

    static Document doc;

    public static void main(String[] args) throws SecurityException {
        File folder_1 = new File(rawFolder_1);
        File[] listOfFiles_1= folder_1.listFiles();

        assert listOfFiles_1 != null;
        if (listOfFiles_1.length > 0)
            for (File file : listOfFiles_1) {
                if (file.isFile()) {
                    CleanFile(file.getName());
                }
            }

    }

    private static void CleanFile(String filename) {

        System.out.println("\nStart\n");
        System.out.println("    >> Reading File: " + rawFolder_1 + filename + "\n");

        File dataset = new File(rawFolder_1 + filename);

        try
        {
            System.out.println("    >> Cleaning Unnecessary Entries. This can take a while, Please Wait.\n");

            doc = Jsoup.parse(dataset, "UTF-8");

            Elements div_s = doc.select("div.content.clear-block");


            System.out.println("PRINTING:");
            System.out.println(div_s.toString());
            System.out.println("\\PRINTING");

            //String docString = "empty";
            String docString = div_s.toString();


            // Gravar ficheiro processado

            // String newFileName = new String(filename.split(Pattern.quote("."))[0] + "_processed.html");
            String newFileName = new String(filename);
            System.out.println("    >> Writing new file: " + processedFolder_1 + newFileName + "\n");
            FileWriter textFile = new FileWriter(processedFolder_1 + newFileName, false);
            textFile.write(docString);
            System.out.println("finished writing file named " + textFile.toString());
            textFile.close();

            System.out.println("Done.\n");
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    static void removeElement_ID(String id)
    {
        Element element = doc.getElementById(id);
        if (element != null)
            element.remove();
    }
    static void removeElements_Class(String className)
    {
        Elements elements = doc.getElementsByClass(className);
        if (!elements.isEmpty())
            elements.remove();
    }
    static void removeElements_Tag(String tag)
    {
        Elements elements = doc.getElementsByTag(tag);
        if (!elements.isEmpty())
            elements.remove();
    }
}