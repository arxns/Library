import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Library {
    private ArrayList<Book> books;
    private final String FILE_NAME = "books.txt";

    public Library() {
        books = new ArrayList<>();
        loadBooks(); // Load books
    }

    public void loadBooks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String title = parts[0];
                String author = parts[1];
                int year = Integer.parseInt(parts[2]);
                books.add(new Book(title, author, year));
            }
        } catch (IOException e) {
            System.out.println("Failed to load books: " + e.getMessage());
        }
    }

    public void saveBooks() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Book book : books) {
                writer.println(book.getTitle() + "," + book.getAuthor() + "," + book.getYear());
            }
        } catch (IOException e) {
            System.out.println("Failed to save books: " + e.getMessage());
        }
    }

    public void addBook(Book book) {
        books.add(book);
        saveBooks(); // Save
    }

    public void removeBook(String title) {
        boolean found = false;
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getTitle().equalsIgnoreCase(title)) {
                books.remove(i);
                System.out.println("Book \"" + title + "\" deleted.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Book \"" + title + "\" don't finded.");
        }
        saveBooks(); // Save
    }

    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("The library dosen't have books.");
            return;
        }

        System.out.println("Books in library:");
        for (Book book : books) {
            System.out.println(book.getTitle() + " by " + book.getAuthor() + " (" + book.getYear() + ")");
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Library library = new Library();
        library.displayBooks();

        System.out.println("Do you want to add new book? (Yes/No):");
        String response = in.nextLine();

        if (response.equalsIgnoreCase("Yes")) {
            System.out.print("Input name of a book: ");
            String title = in.nextLine();

            System.out.print("Input author name: ");
            String author = in.nextLine();

            System.out.print("Input year of created a book: ");
            int year = in.nextInt();
            in.nextLine();

            Book newBook = new Book(title, author, year);
            library.addBook(newBook);
            System.out.println("Book added!");
        }

        library.displayBooks();

        System.out.println("Do you want to delete a book? (Yes/No)");
        String delBook = in.nextLine();

        if (delBook.equalsIgnoreCase("Yes")) {
            System.out.print("Input name of a book: ");
            String titleToDelete = in.nextLine();
            library.removeBook(titleToDelete);
        }

        in.close();
    }
}

class Book {
    private String title;
    private String author;
    private int year;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
}