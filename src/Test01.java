import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.regex.Pattern;

public class Test01 { // classe de testes

    public static final String SOURCE_HTML = "<html><head><title>Jsoup Example</title></head>"
            + "<body><h1>Welcome to JournalDev!!</h1><br />"
            + "<div id=\"id1\">Hello</div>"
            + "<div class=\"class1\">Pankaj</div>"
            + "<a href=\"http://journaldev.com\">Home</a>"
            + "<a href=\"http://wikipedia.org\">Wikipedia</a>"
            + "</body></html>";

    public static void main(String[] args) {
        Document doc = Jsoup.parse(SOURCE_HTML);
        System.out.println("Title="+doc.title());

        //let's add attribute rel="nofollow" to all the links
        doc.select("a[href]").attr("rel", "nofollow");
        //System.out.println(doc.html());

        //change div class="class1" to class2
        doc.select("div.class1").attr("class", "class2");
        //System.out.println(doc.html());

        //change the HTML value of first h1 element
        doc.select("h1").first().html("Welcome to JournalDev.com");
        doc.select("h1").first().append("!!");
        //System.out.println(doc.html());

        //let's make Home link bold
        doc.select("a[href]").first().html("<strong>Home</strong>");
        // System.out.println(doc.html());


        String myString = new String("testing.xml".split(Pattern.quote("."))[0]);
        System.out.println(myString);


        String testParse = new String ("#UID:nhslo3844_12_000018\n" +
                "#DATE:201204-06\n" +
                "#URL:http://www.nhslocal.nhs.uk/story/what-happens-labour-ward\n" +
                "#CONTENT:\n" +
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\" dir=\"ltr\">\n" +
                " <head> ");

        String date_raw = testParse.substring(testParse.indexOf("#DATE:") + 6, testParse.indexOf("#DATE:") + 6 + 9);

        System.out.println("date = |" + date_raw + "|\n");

        String date_year = date_raw.substring(0, 4);
        String date_month = date_raw.substring(4, 6);

        System.out.println("date_year = |" + date_year + "|\n");
        System.out.println("date_month = |" + date_month + "|\n");

        String date_formatted = date_year + "-" + date_month + "-01T00:00:00Z";

        System.out.println("date_formatted = |" + date_formatted + "|\n");

        // #DATE:201204-06
        // <field name="date">2015-06-22T00:00:00Z</field>

        // <field name="sourceurl">http://www.nhslocal.nhs.uk/story/what-happens-labour-ward</field>

        String source_url = testParse.substring(testParse.indexOf("#URL:") + 5, testParse.indexOf("\n#CONTENT"));

        System.out.println("source_url = |" + source_url + "|\n");


        String A1 = new String("testingA");
        String A2 = A1;
        System.out.println("A1: " + A1);
        System.out.println("A2: " + A2);
        A1 = "shelly";
        System.out.println("A1: " + A1);
        System.out.println("A2: " + A2);
    }

}