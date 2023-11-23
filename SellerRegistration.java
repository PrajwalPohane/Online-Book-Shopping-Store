import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class SellerRegistration {

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

        // Take input for user data
        Scanner scanner = new Scanner(System.in);
        // Take input for seller data
        System.out.println("Enter seller information:");
        System.out.print("Username: ");
        String sellerUsername = scanner.nextLine();
        System.out.print("Email: ");
        String sellerEmail = scanner.nextLine();
        System.out.print("Password: ");
        String sellerPassword = scanner.nextLine();
        System.out.print("Confirm Password: ");
        String sellerConfirmPassword = scanner.next();
        System.out.print("Business Name: ");
        String businessName = scanner.next();
        System.out.print("Address: ");
        String address = scanner.next();

        // Check if password and confirm_password match for the seller
        if (!sellerPassword.equals(sellerConfirmPassword)) {
            System.out.println("Error: Password and Confirm Password do not match for the seller.");
            return; // Exit the program
        }

        // Insert seller data
        insertSellerData(sellerUsername, sellerEmail, sellerPassword, sellerConfirmPassword, businessName, address);

        // Close the scanner
        scanner.close();
    }
    private static void insertSellerData(String username, String email, String password, String confirm_password, String businessName, String address) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String insertSellerQuery = "INSERT INTO sellers (username, email, password, confirm_password, business_name, address) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSellerQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);
                preparedStatement.setString(4, confirm_password);
                preparedStatement.setString(5, businessName);
                preparedStatement.setString(6, address);

                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println(rowsAffected + " row(s) inserted for seller.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
