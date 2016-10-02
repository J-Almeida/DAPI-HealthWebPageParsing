import org.jsoup.safety.Whitelist;

public class MyWhiteList extends Whitelist {
    public static Whitelist MyWhitelist() {
        return basic().addTags(new String[]{"img"})
                .addAttributes("img", new String[]{"align", "alt", "height", "src", "title", "width"})
                .addProtocols("img", "src", new String[]{"http", "https"})
                .addTags("h3");

    }
}