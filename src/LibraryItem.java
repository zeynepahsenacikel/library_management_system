import java.util.Date;

public abstract class LibraryItem {
    protected String id;
    protected String name;
    protected String type;
    protected String borrowDate = null;
    protected User borrowedBy = null;
    protected boolean available = true;

    public LibraryItem(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getId() {
        return name;
    }

    public String getName() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void borrow(User user, String date) {
        available = false;
        borrowDate = date;
        borrowedBy = user;
    }

    public void returnBack(User user, String date) {
        available = true;
        borrowDate = null;
        borrowedBy = null;
    }

    public User getBorrowedBy() {
        return borrowedBy;
    }

    public String getBorrowedDate() {
        return borrowDate;
    }

    public abstract String getDetailedInfo();
}


class Book extends LibraryItem {
    private String author;
    private String genre;

    public Book(String id, String name, String type, String author, String genre) {
        super(id, name, type);
        this.author = author;
        this.genre = genre;
    }

    @Override
    public String getDetailedInfo() {
        String availabilityStatus = available? "Available" : "Borrowed";
        String borrowInfo = "";
        if (!available) {
            borrowInfo = "Borrowed"+ " Borrowed Date: " + borrowDate + " Borrowed By: " + borrowedBy;
        }
        return "------ Item Information for " +id+ " ------"+
                "\nID: " + id + " Name: " + name + " Status: " + availabilityStatus +
                "\nAuthor: " + author + " Genre: " + genre + "\n";
    }
}

class Magazine extends LibraryItem {
    private String publisher;
    private String category;

    public Magazine(String id, String name, String type, String publisher, String category) {
        super(id, name, type);
        this.publisher = publisher;
        this.category = category;
    }

    @Override
    public String getDetailedInfo() {
        String availabilityStatus = available? "Available" : "Borrowed";
        String borrowInfo = "";
        if (!available) {
            borrowInfo = "Borrowed" + " Borrowed Date: " + borrowDate + " Borrowed By: " + borrowedBy;
        }
        return "------ Item Information for " +id+ " ------"+
                "\nID: " + id + " Name: " + name + " Status: Borrowed"+ " Borrowed Date: " + borrowDate + " Borrowed By: " + borrowedBy+
                "\nPublisher: " + publisher + " Category: " + category + "\n";
    }
}

class DVD extends LibraryItem {
    private String director;
    private String category;
    private String runtime;

    public DVD(String id, String name, String type, String director, String category, String runtime) {
        super(id, name, type);
        this.director = director;
        this.category = category;
        this.runtime = runtime;
    }

    @Override
    public String getDetailedInfo() {
        String availabilityStatus = available? "Available" : "Borrowed";
        String borrowInfo = "";
        if (!available) {
            borrowInfo = "Borrowed" + " Borrowed Date: " + borrowDate + " Borrowed By: " + borrowedBy;
        }
        return "------ Item Information for " +id+ " ------"+
                "\nID: " + id + " Name: " + name + " Status: Borrowed"+ " Borrowed Date: " + borrowDate + " Borrowed By: " + borrowedBy+
                "\nDirector: " + director + " Category: " + category + " Runtime: " + runtime + "\n";
    }
}