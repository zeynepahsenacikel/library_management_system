import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        LibraryManagementSystem system = new LibraryManagementSystem();

        try {
            BufferedReader userReader = new BufferedReader(new FileReader("users.txt"));
            String line;
            while ((line = userReader.readLine()) != null) {
                String[] parts = line.split(",");
                String t = parts[0];
                String name = parts[1];
                String id = parts[2];
                String phoneNumber = parts[3];

                if (t.equals("S")) {
                    String department = parts[4];
                    String faculty = parts[5];
                    int grade = Integer.parseInt(parts[6]);
                    system.addUser(new Student(id, name, phoneNumber, faculty, department, grade));
                } else if (t.equals("A")) {
                    String department = parts[4];
                    String faculty = parts[5];
                    String title = parts[6];
                    system.addUser(new AcademicStuff(id, name, phoneNumber, faculty, department, title));
                } else if (t.equals("G")) {
                    String occupation = parts[4];
                    system.addUser(new Guest(id, name, phoneNumber, occupation));
                }
            }
            userReader.close();

            BufferedReader itemReader = new BufferedReader(new FileReader("items.txt"));
            while ((line = itemReader.readLine()) != null) {
                String[] parts = line.split(",");
                String t = parts[0];
                String id = parts[1];
                String name = parts[2];

                if (t.equals("B")) {
                    system.addItem(new Book(id, name, parts[5], parts[3], parts[4]));
                } else if (t.equals("M")) {
                    system.addItem(new Magazine(id, name, parts[5], parts[3], parts[4]));
                } else if (t.equals("D")) {
                    system.addItem(new DVD(id, name, parts[6], parts[3], parts[4], parts[5]));
                }
            }
            itemReader.close();


            BufferedReader commandReader = new BufferedReader(new FileReader("commands.txt"));
            while ((line = commandReader.readLine()) != null) {
                system.processCommand(line);
            }
            commandReader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            for (String outputLine : system.getOutput()) {
                writer.write(outputLine);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
    }
}