import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class library {
    private List<Book> books;
    private List<User> users;

    public library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void registerUser(User user) {
        users.add(user);
    }

    public User authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.checkPassword(password)) {
                return user;
            }
        }
        return null;
    }

    public Book findBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && book.isAvailable()) {
                return book;
            }
        }
        return null;
    }

    public void borrowBook(User user, String title) {
        Book book = findBook(title);
        if (book != null) {
            book.setAvailable(false);
            user.borrowBook(book);
            System.out.println("You have borrowed: " + book);
        } else {
            System.out.println("Book is not available.");
        }
    }

    public void returnBook(User user, String title) {
        Book book = null;
        for (Book b : user.getBorrowedBooks()) {
            if (b.getTitle().equalsIgnoreCase(title)) {
                book = b;
                break;
            }
        }
        if (book != null) {
            book.setAvailable(true);
            user.returnBook(book);
            System.out.println("You have returned: " + book);
        } else {
            System.out.println("You have not borrowed this book.");
        }
    }

    public void displayAvailableBooks() {
        System.out.println("Available books:");
        for (Book book : books) {
            if (book.isAvailable()) {
                System.out.println(book);
            }
        }
    }

    public static void main(String[] args) {
        library library = new library();
        
        // Adding some books
        library.addBook(new Book("1984", "George Orwell", "Dystopian"));
        library.addBook(new Book("To Kill a Mockingbird", "Harper Lee", "Fiction"));
        
        // Registering users
        library.registerUser(new User("user1", "password1"));
        library.registerUser(new User("user2", "password2"));

        Scanner scanner = new Scanner(System.in);
        
        // Simple authentication and book management flow
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = library.authenticateUser(username, password);
        if (user != null) {
            System.out.println("Authentication successful!");

            boolean running = true;
            while (running) {
                System.out.println("\n1. Display available books");
                System.out.println("2. Borrow a book");
                System.out.println("3. Return a book");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        library.displayAvailableBooks();
                        break;
                    case 2:
                        System.out.print("Enter book title to borrow: ");
                        String borrowTitle = scanner.nextLine();
                        library.borrowBook(user, borrowTitle);
                        break;
                    case 3:
                        System.out.print("Enter book title to return: ");
                        String returnTitle = scanner.nextLine();
                        library.returnBook(user, returnTitle);
                        break;
                    case 4:
                        running = false;
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid option.");
                        break;
                }
            }
        } else {
            System.out.println("Authentication failed.");
        }

        scanner.close();
    }
}
