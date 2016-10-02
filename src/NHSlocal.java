import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import org.jsoup.safety.Whitelist;


public class NHSlocal
{
    // incluir sempre barra no final dos caminhos de pastas
    static String rawFolder_1 = "C:\\git\\dataset\\wip\\teste3\\";
    static String processedFolder_1 = "C:\\git\\dataset\\wip\\proc1\\";
    static String rawFolder_2 = "C:\\git\\dataset\\wip\\proc1";
    static String processedFolder_2 = "C:\\git\\dataset\\wip\\proc2\\";
    static Document doc;

    public static void main(String[] args) throws SecurityException, MalformedURLException {
        File folder_1 = new File(rawFolder_1);
        File[] listOfFiles_1= folder_1.listFiles();

        assert listOfFiles_1 != null;
        if (listOfFiles_1.length > 0)
            for (File file : listOfFiles_1) {
                if (file.isFile()) {
                    CleanFile_3(file.getName());
                }
            }

        /*
        File folder_2 = new File(rawFolder_2);
        File[] listOfFiles_2= folder_2.listFiles();

        assert listOfFiles_2 != null;
        if (listOfFiles_2.length > 0)
            for (File file : listOfFiles_2) {
                if (file.isFile()) {
                    fixHtml(file.getName());
                }
            }
        */
    }

    // função antiga de limpeza 1
    private static void CleanFile(String filename) {

        System.out.println("\nStart\n");
        System.out.println("    >> Reading File: " + rawFolder_1 + filename + "\n");

        File dataset = new File(rawFolder_1 + filename);

        try
        {
            System.out.println("    >> Cleaning Unnecessary Entries. This can take a while, Please Wait.\n");

            doc = Jsoup.parse(dataset, "UTF-8");
            String docString = Jsoup.clean(doc.toString(), Whitelist.basicWithImages());


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

    // função antiga de limpeza 2
    private static void CleanFile_2(String filename) {

        System.out.println("\nStart\n");
        System.out.println("    >> Reading File: " + rawFolder_2 + filename + "\n");

        File dataset = new File(rawFolder_2 + filename);

        try
        {
            System.out.println("    >> Cleaning Unnecessary Entries. This can take a while, Please Wait.\n");

            doc = Jsoup.parse(dataset, "UTF-8");

            /*
            //Elements scripts = doc.getElementsByTag("script");
            //Elements link = doc.getElementsByTag("link");
            //Elements li = doc.getElementsByTag("li");
            //Elements span = doc.getElementsByTag("span");
            Elements input = doc.getElementsByTag("input");
            //Elements ul = doc.getElementsByTag("ul");
            //Elements textarea = doc.getElementsByTag("textarea");
            //Elements a = doc.getElementsByTag("a");
            //Elements table = doc.getElementsByTag("table");
            //Elements form = doc.getElementsByTag("form");
            //Elements comments = doc.getElementsByTag("!--"); // testar


            removeElements_Class("nhs-skip-to-content");
            removeElements_Class("right-register");
            removeElements_Class("vote");
            removeElement_ID("header-wrapper");
            removeElement_ID("header-wrapper");

            removeElement_ID("crumbtrail");
            removeElement_ID("left-nav");
            removeElement_ID("footer-wrapper");

            removeElement_ID("email-to-friend-popup");
            removeElement_ID("block-illumina_nhs_conditions-conditions_teaser");

            removeElements_Tag("script");
            removeElements_Tag("meta");
            removeElements_Tag("link");

            // <a href="#" id="nhs-main-content" name="nhs-main-content"></a>
            Element a = doc.getElementById("a");
            if (a != null) {
                Elements a_elements = a.getElementsByAttributeValue("href","#");
                a.remove(); // a_elements.remove
            }

            // "Sharing"
            removeElement_ID("block-block-1");
            */

            // remover <a rel="nofollow">X</a>
            /*
            Element a_nofollow = doc.getElementById("a");
            if (a_nofollow != null) {
                Elements a_elements = a_nofollow.getElementsByAttributeValue("rel","nofollow");
                a_elements.remove();
            }
            */
            Elements links = doc.select("a");
            for (Element link : links) {
                String attribute=link.attr("rel");
                if(attribute.equalsIgnoreCase("nofollow")){ // está a apagar os related links?
                    link.remove();
                }
            }


            // Gravar ficheiro processado

            // String newFileName = new String(filename.split(Pattern.quote("."))[0] + "_processed.html");
            String newFileName = new String(filename.split(Pattern.quote("."))[0] + ".html");
            System.out.println("    >> Writing new file: " + processedFolder_2 + newFileName + "\n");
            FileWriter textFile = new FileWriter(processedFolder_2 + newFileName, false);
            textFile.write(doc.toString());
            textFile.close();

            System.out.println("Done.\n");
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void CleanFile_3(String filename) throws MalformedURLException {

        System.out.println("    >> Reading File: " + rawFolder_1 + filename + "\n");

        File dataset = new File(rawFolder_1 + filename);

        String dataset_str = null;
        try {
            dataset_str = readFile(rawFolder_1 + filename, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("for file " + filename);
        // System.out.println("file contents:\n " + dataset_str);
        System.out.println("index 1 = " + dataset_str.indexOf("#URL:"));
        System.out.println("index 2 = " + dataset_str.indexOf("#CONTENT"));

        // schema fields
        String field_id = dataset_str.substring(dataset_str.indexOf("#UID:") + 5, dataset_str.indexOf("\n#DATE"));
        String field_title;
        String field_text = "";
        String field_date_raw = dataset_str.substring(dataset_str.indexOf("#DATE:") + 6, dataset_str.indexOf("#DATE:") + 6 + 9);
        String field_date_year = field_date_raw.substring(0, 4);
        String field_date_month = field_date_raw.substring(4, 6);
        String field_date_formatted = field_date_year + "-" + field_date_month + "-01T00:00:00Z";

        System.out.println("for file " + filename);
        // System.out.println("file contents:\n " + dataset_str);
        System.out.println("index 1 = " + dataset_str.indexOf("#URL:"));
        System.out.println("index 2 = " + dataset_str.indexOf("#CONTENT"));

        URL field_source_url = new URL(dataset_str.substring(dataset_str.indexOf("#URL:") + 5, dataset_str.indexOf("\n#CONTENT")));

        // só se o url for guardado como URL é que se vê no output da consola

        String field_rawhtml;


        try
        {
            System.out.println(">> Cleaning file.\n");

            doc = Jsoup.parse(dataset, "UTF-8");

            // obtenção do bloco principal de conteúdo da página
            Elements div_s = doc.select("div.content.clear-block");

            String mainBlock = "";

            String originalTitle = doc.title();

          String[] processedTitle = {};

            System.out.println("Original title: " + originalTitle);

            if (originalTitle.contains("|")) {
                processedTitle = originalTitle.split("\\|"); // Since split takes a regex as argument you have to escape all non-intended regex symbols.

                /*
                System.out.println("Split title: ");
                for (String titles : processedTitle)
                    System.out.print(titles + " ; ");
                */
                field_title = processedTitle[0].trim();
            }
            else {
                field_title = originalTitle.trim();
            }

            mainBlock += "<title>" + field_title + "</title>";
            mainBlock += "<h1>" + field_title + "</h1>";

            if (div_s.isEmpty() && doc.getElementById("content-centre") != null)
                mainBlock += doc.getElementById("content-centre").toString();
            mainBlock += div_s.toString();


            // filtro temporário para ficheiros que ficam com "Lifestyle" e "Conditions" no título
            System.out.println("Current newTitle " + field_title);
            if (field_title.equalsIgnoreCase("Conditions") || field_title.equalsIgnoreCase("Lifestyle")) {
                System.out.println("Returning on file " + filename);
                return;
            }

            Document mainBlock_doc = Jsoup.parse(mainBlock, "UTF-8");


            // limpeza do mainBlock_doc
            Elements storyIcons = mainBlock_doc.select("span.node-story-news-icon");
            Elements storyIcons_video = mainBlock_doc.select("span.node-story-video-icon");
            Elements dueForReview = mainBlock_doc.getElementsByTag("em");
                // Elements dueForReview = mainBlock_doc.getElementsContainingText("Due for review");
            Element loginRegisterDownload = mainBlock_doc.getElementById("content-links");

            if (storyIcons != null)
                storyIcons.remove();

            if (storyIcons_video != null)
                storyIcons_video.remove();

            if (dueForReview != null)
                dueForReview.remove();

            if (loginRegisterDownload != null)
                loginRegisterDownload.remove();

            // Gravar ficheiro processado

            // String newFileName = new String(filename.split(Pattern.quote("."))[0] + "_processed.html");
            String newFileName = new String(filename);
            System.out.println("    >> Writing new file: " + processedFolder_1 + newFileName + "\n");
            FileWriter textFile = new FileWriter(processedFolder_1 + newFileName, false);

            // textFile.write(mainBlock_doc.toString()); // escreve só o html no ficheiro
            field_rawhtml = mainBlock_doc.toString();

            textFile.write("<add>\n");
            textFile.write("<doc>\n");
            textFile.write("<field name=\"id\">" + field_id.trim() + "</field>\n");
            textFile.write("<field name=\"title\">" + field_title.trim() + "</field>\n");
            textFile.write("<field name=\"text\">" + field_text + "</field>\n");
            textFile.write("<field name=\"date\">" + field_date_formatted + "</field>\n");
            textFile.write("<field name=\"sourceurl\">" + field_source_url + "</field>\n");
            textFile.write("<field name=\"rawhtml\">" + "<![CDATA[" + field_rawhtml + "]]>" + "</field>\n");
            textFile.write("</doc>\n");
            textFile.write("</add>");

            /*
            System.out.println("field_id: |" + field_id + "|");
            System.out.println("field_title: |" + field_title + "|");
            System.out.println("field_text: |" + field_text + "|");
            System.out.println("field_date_raw: |" + field_date_raw + "|");
            System.out.println("field_date_year: |" + field_date_year + "|");
            System.out.println("field_date_month: |" + field_date_month + "|");
            System.out.println("field_date_formatted: |" + field_date_formatted + "|");
            System.out.println("field_source_url: |" + field_source_url + "|");
            // System.out.println("field_rawhtml: |" + field_rawhtml + "|");
            System.out.println("field_rawhtml: |" + "..." + "|");
            */

            System.out.println("Finished writing file named " + textFile.toString());
            textFile.close();

            System.out.println("Done.\n");
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    static String readFile(String path, Charset encoding) throws IOException {
        System.out.println("trying to read path: " + Paths.get(path).toString());
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    private static void fixHtml(String filename) {

        System.out.println("\nStart\n");
        System.out.println("    >> Reading File: " + processedFolder_1 + filename + "\n");

        File dataset = new File(processedFolder_1 + filename);

        try
        {
            System.out.println("Fixing file\n");

            doc = Jsoup.parse(dataset, "UTF-8");

            // Gravar ficheiro processado

            // String newFileName = new String(filename.split(Pattern.quote("."))[0] + "_processed.html");
            String newFileName = new String(filename.split(Pattern.quote("."))[0] + ".html");
            System.out.println("    >> Writing new file: " + processedFolder_2 + newFileName + "\n");
            FileWriter textFile = new FileWriter(processedFolder_2 + newFileName, false);
            textFile.write(doc.toString());
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