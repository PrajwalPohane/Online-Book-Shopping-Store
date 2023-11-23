import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteBook {

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

        // Take input for ISBN of the book to delete
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter ISBN of the book to delete: ");
        String isbnToDelete = scanner.nextLine();

        // Delete book based on ISBN
        deleteBook(isbnToDelete);

        // Close the scanner
        scanner.close();
    }

    private static void deleteBook(String isbn) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String deleteBookQuery = "DELETE FROM books WHERE isbn = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteBookQuery)) {
                preparedStatement.setString(1, isbn);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println(rowsAffected + " row(s) deleted for the book with ISBN: " + isbn);
                } else {
                    System.out.println("No book found with ISBN: " + isbn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
