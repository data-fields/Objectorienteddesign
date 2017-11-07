//Represents bibliographic information for books.
public class Book implements Publication 

{
    private final String title, author, publisher, location;
    private final int year;

    /**
     * creates {@code Book} object.
     *
     * @param title     
     * @param author    
     * @param publisher 
     * @param location  
     * @param year      
     */
    public Book(String title, String author, String publisher,
                String location, int year) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.location = location;
        this.year = year;
    }

    @Override
    public String citeMla() {
        return author + ". " + title + ". " + location + ": " +
                publisher + ", " + year + ".";
    }

    @Override
    public String citeApa() {
        return author + " (" + year + "). " + title + ". " +
                location + ": " + publisher + ".";
    }
}
