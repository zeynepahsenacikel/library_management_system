import java.util.*;
import java.text.*;

public class LibraryManagementSystem {
    private Map<String, User> users = new HashMap<>();
    private Map<String, LibraryItem> items = new HashMap<>();
    private List<String> output = new ArrayList<>();

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    public void addItem(LibraryItem item) {
        items.put(item.getId(), item);
    }

    public void processCommand(String commandLine) {
        try {
            String[] parts = commandLine.split(",");
            String command = parts[0].trim();

            if (command.equals("borrow,")) {
                borrowItem(parts[1], parts[2], parts[3]);
            } else if (command.equals("return,")) {
                returnItem(parts[1], parts[2]);
            } else if (command.equals("pay,")) {
                payDebt(parts[1]);
            } else if (command.equals("displayUsers")) {
                displayUsers();
            } else if (command.equals("displayItems")) {
                displayItems();
            } else {
                output.add("Invalid command: " + command);
            }
        } catch (Exception e) {
            output.add("Invalid command format.");
        }
    }

    private void borrowItem(String userId, String itemId, String borrowDate) {
        LibraryItem item = items.get(itemId);
        User user = users.get(userId);

        if (user != null && item != null && item.isAvailable()) {
            item.available = false;
            item.borrowDate = borrowDate;
            item.borrowedBy = user;

            output.add(user.getName() + " successfully borrowed! " + item.getName());
        } else {
            output.add(user.getName() + " cannot borrow " + item.getName()+ ", it is not available!");
        }

        if (user.getDebt() > 0) {
            output.add(user.getName() + " cannot borrow " + item.getName()+
                    ", you must first pay the penalty amount! " + (int)user.getDebt() + "$");
            return;
        }

        if (!item.isAvailable()) {
            output.add(user.getName() + "cannot borrow " + item.getName()+
                    ", it is not available!");
            return;
        }

        if (user instanceof Student) {
            if (item.getType().equals("reference")) {
                output.add(user.getName() + " cannot borrow reference item!");
                return;
            }
        } else if (user instanceof Guest) {
            if (item.getType().equals("rare") || item.getType().equals("limited")) {
                output.add(user.getName() + "cannot borrow rare or limited item!");
                return;
            }
        }

        if (user.borrowedItems.size() >= user.getMaxItems()) {
            output.add(user.getName() + "cannot borrow " + item.getName() + " since the borrow limit has been reached!" );
            return;
        }

         user.borrowItem(item);
         item.borrow(user, borrowDate);
         output.add(user.getName() + "successfully borrowed! " + item.getName() + ".");
    }

    private void returnItem(String userId, String itemId) {
        LibraryItem item = items.get(itemId);
        User user = users.get(userId);

        if (user == null || item == null) return;
        if (!user.hasItem(item)) return;

        try{
            Date borrowDate = sdf.parse(item.getBorrowedDate());
            Date returnDate = sdf.parse("25/03/2025");

            long diff = (returnDate.getTime() - borrowDate.getTime()) / (1000 * 60 * 60 * 24);
            int maxDays = user.getMaxDays();
            if (diff > maxDays) {
                double penalty = (diff - maxDays) *1.0;
                user.addDebt(penalty);
            }

            user.returnItem(item);
            item.returnBack(user, "25/03/2025");
            output.add(user.getName() + "successfully returned! " + item.getName());
        } catch (ParseException e) {
            output.add("Invalid date format during return.");
        }
    }

    private void payDebt(String userId) {
        User user = users.get(userId);
        if (user == null) return;
        user.payDebt();
        output.add(user.getName() + "has paid penalty");
    }

    private void displayUsers() {
        List<User> userList = new ArrayList<>(users.values());
        Collections.sort(userList, new Comparator<User>() {
            public int compare(User u1, User u2) {
                return u1.getId().compareTo(u2.getId());
            }
        });
        for (User user : users.values()) {
            output.add(user.getDetailedInfo());
        }
    }

    public void displayItems() {
        List<LibraryItem> itemList = new ArrayList<>(items.values());
        Collections.sort(itemList, new Comparator<LibraryItem>() {
            public int compare(LibraryItem o1, LibraryItem o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        for (LibraryItem item : itemList) {
            output.add(item.getDetailedInfo());
        }
    }

    public List<String> getOutput() {
        return output;
    }
}

    /*public void loadUsers(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] p = line.split(",");
            String type = p[0];
            switch (type) {
                case "S":
                    users.add(new Student(p[1], p[2], p[3], p[4], p[5], p[6]));
                    break;
                case "A":
                    users.add(new AcademicStuff(p[1], p[2], p[3], p[4], p[5], p[6]));
                    break;
                case "G":
                    users.add(new Guest(p[1], p[2], p[3], p[4]));
                    break;
            }
        }
        reader.close();
    }

    public void loadItems(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null){
            String[] p = line.split(",");
            switch (p[0]) {
                case "B":
                    items.add(new Book(p[1], p[2], p[3], p[4], p[5]));
                    break;
                case "M":
                    items.add(new Magazine(p[1], p[2], p[3], p[4], p[5]));
                    break;
                case "D":
                    items.add(new DVD(p[1], p[2], p[3], p[4], p[5], p[6]));
                    break;
            }
        }
        reader.close();
    }

    public void processCommands(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length < 2) {
                System.out.println("Invalid command: " + line);
                continue;
            }

            String command = parts[0];
            String userID = parts[1];
            User user = getUserById(userID);

            if (user == null) {
                System.out.println("User not found: " + userID);
                continue;
            }

            switch (command) {
                case "borrow":
                    if (parts.length < 4) {
                        System.out.println("Invalid borrow command: " + line);
                        continue;
                    }
                    String itemID = parts[2];
                    LibraryItem item = getItemById(itemID);
                    LocalDate borrowDate = LocalDate.parse(parts[3], formatter);

                    if (item != null && item.isAvailable() && !item.getType().equals("reference") && user.canBorrow()) {
                        user.borrowItem(item, borrowDate);
                        item.setAvailable(false);
                    }
                    break;

                case "return":
                    if (parts.length < 3) {
                        System.out.println("Invalid return command: " + line);
                        continue;
                    }
                    itemID = parts[2];
                    item = getItemById(itemID);
                    if (item != null) {
                        LocalDate returnDate = LocalDate.now();
                        user.returnItem(item, returnDate);
                    }
                    break;

                case "pay":
                    user.payDebt();
                    break;

                default:
                    System.out.println("Invalid command: " + command);
                    break;
            }
        }
        reader.close();
    }

    public User getUserById(String id) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    public LibraryItem getItemById(String id) {
        for (LibraryItem i : items) {
            if (i.getId().equals(id)) {
                return i;
            }
        }
        return null;
    }

    public void printStatus() {
        for (User user : users) {
            System.out.println(user.getInfo());
        }
    }
}*/
