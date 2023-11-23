import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AddBook {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bookstore2";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "prajwal.22210207@";

    public static void main(String[] args) {
        // Register the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // Take input for book information
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter book information:");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Genre: ");
        String genre = scanner.nextLine();
        System.out.print("Pages: ");
        int pages = scanner.nextInt();
        System.out.print("Price: ");
        double price = scanner.nextDouble();

        // Insert book data
        insertBookData(title, author, isbn, genre, pages, price);

        // Close the scanner
        scanner.close();
    }

    private static void insertBookData(String title, String author, String isbn, String genre, int pages, double price) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String insertBookQuery = "INSERT INTO books (title, author, isbn, genre, pages, price) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertBookQuery)) {
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, author);
                preparedStatement.setString(3, isbn);
                preparedStatement.setString(4, genre);
                preparedStatement.setInt(5, pages);
                preparedStatement.setDouble(6, price);

                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println(rowsAffected + " row(s) inserted for the book.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
