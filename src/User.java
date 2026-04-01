import java.time.LocalDate;
import java.util.*;
import java.time.temporal.ChronoUnit;

public abstract class User {
    protected String id;
    protected String name;
    protected String phoneNumber;
    protected List<LibraryItem> borrowedItems = new ArrayList<>();
    protected double debt = 0.0;

    public User(String name, String id, String phoneNumber){
        this.name = name;
        this.id = id;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getDebt() {
        return debt;
    }

    public void addDebt(double amount){
        debt += amount;
    }

    public void payDebt() {
        debt = 0.0;
    }

    public void borrowItem(LibraryItem item) {
        borrowedItems.add(item);
    }

    public void returnItem(LibraryItem item) {
        borrowedItems.remove(item);
    }

    public boolean hasItem(LibraryItem item) {
        return borrowedItems.contains(item);
    }

    public abstract int getMaxItems();
    public abstract int getMaxDays();
    public abstract String getDetailedInfo();
}


class Student extends User {
    private String faculty;
    private String department;
    private int grade;

    public Student(String name, String id, String phoneNumber, String faculty, String department, int grade) {
        super(name, id, phoneNumber);
        this.faculty = faculty;
        this.department = department;
        this.grade = grade;
    }

    @Override
    public int getMaxItems() {
        return 5;
    }

    @Override
    public int getMaxDays() {
        return 30;
    }

    @Override
    public String getDetailedInfo() {
        return "------ User Information for " +name+ "------"+
                "\nName: " + id + " Phone: " + phoneNumber +
                "\nFaculty: " + faculty + " Department: " + department + " Grade: " + grade + "th" + "\n";
    }
}


class AcademicStuff extends User {
    private String faculty;
    private String department;
    private String title;

    public AcademicStuff(String name, String id, String phoneNumber, String faculty, String department, String title) {
        super(name, id, phoneNumber);
        this.faculty = faculty;
        this.department = department;
        this.title = title;
    }

    @Override
    public int getMaxItems() {
        return 3;
    }

    @Override
    public int getMaxDays() {
        return 15;
    }

    @Override
    public String getDetailedInfo() {
        return "------ User Information for " +name+ " ------"+
                "\nName: " + title + id + " Phone: " + phoneNumber +
                "\nFaculty: " + faculty + " Department: " + department + "\n";
    }
}

class Guest extends User {
    private String occupation;

    public Guest(String name, String id, String phoneNumber, String occupation) {
        super(name, id, phoneNumber);
        this.occupation = occupation;
    }

    @Override
    public int getMaxItems() {
        return 1;
    }

    @Override
    public int getMaxDays() {
        return 7;
    }

    @Override
    public String getDetailedInfo() {
        return "------ User Information for " +name+ " ------"+
                "\nName: " +id+ " Phone: " +phoneNumber+
                "\nOccupation: " + occupation + "\n";
    }
}