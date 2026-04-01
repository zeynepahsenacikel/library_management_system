# Library Management System (Java OOP)
* A robust, object-oriented Library Management System developed in Java. This project simulates a real-world library environment where different types of users (Students, Academics, Guests) can borrow and return various library items (Books, Magazines, DVDs) while the system automatically handles loan limits, deadlines, and penalty calculations.

## ✨ Features
* Advanced OOP Architecture: Utilizes Abstract Classes and Inheritance for both LibraryItem and User hierarchies.

* Multi-User Support: * Students: Up to 5 items for 30 days.

* Academic Staff: Up to 3 items for 15 days.

* Guests: 1 item for 7 days.

* Diverse Item Catalog: Support for Books, Magazines, and DVDs, each with specific attributes like directors, publishers, or authors.

* Automated Penalty System: Calculates late fees based on the user type and the difference between borrow and return dates.

* Smart Command Processing: Reads operations from a commands.txt file to automate library tasks like borrowing, returning, and debt payment.

* Borrowing Logic: Implements strict rules preventing users with unpaid debts or reached limits from borrowing new items.

## 🚀 Usage
The project is designed to run via the command line and interacts with text-based data files.

* Compile the project:
```javac Main.java LibraryManagementSystem.java LibraryItem.java User.java```

* Run the system:
```java Main```

Ensure that users.txt, items.txt, and commands.txt are present in the root directory.

## 📂 Project Structure
* LibraryItem.java: Abstract base class for library materials.

* User.java: Abstract base class for different user roles and permission levels.

* LibraryManagementSystem.java: The core engine that processes commands and manages data using HashMaps.

* Main.java: The entry point that handles file I/O operations and initializes the system.

## 🛠️ Technologies Used
* Language: Java (JDK 8+)

* Concepts: Polymorphism, Abstraction, Encapsulation, File I/O, Collections Framework (HashMap, ArrayList).

## 📝 Notes
* This project showcases the ability to design a complex system using clean code principles and effective use of Java's collection framework to manage relational data in memory.
