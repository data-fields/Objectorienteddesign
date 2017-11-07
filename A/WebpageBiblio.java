//Webpage Bibliographic information

public class Webpage implements Publication {
  private final String title;
  private final String url;
  private final String retrieved;
   
  /**
   * Constructs a {@code Webpage} object.
   * @param title the title of the webpage
   * @param url the url of the webpage
   * @param retrieved the time retrieved of the webpage.
   */
  
  public Webpage(String title, String url, String retrieved) {
    this.title = title;
    this.url = url;
    this.retrieved = retrieved;
  }
  
  @Override
  public String citeApa() {
    return title + ". Retrieved " + retrieved + ", from " + url + ".";
  }
  
  @Override
  public String citeMla() {
    return "\"" + title + ".\" Web. " + retrieved + " <~" + url + ">.";
  }

}