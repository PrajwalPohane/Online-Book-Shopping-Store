import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SellerLogin {

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

        boolean loginSuccessful = false;

        // Create Scanner outside the loop
        Scanner scanner = new Scanner(System.in);

        // Loop until seller login is successful
        while (!loginSuccessful) {
            // Take input for seller login
            System.out.println("Enter seller login information:");
            System.out.print("Username: ");
            String sellerUsername = scanner.nextLine();
            System.out.print("Password: ");
            String sellerPassword = scanner.nextLine();

            // Check seller login
            loginSuccessful = checkSellerLogin(sellerUsername, sellerPassword);

            if (loginSuccessful) {
                System.out.println("Seller login successful!");
            } else {
                System.out.println("Invalid seller username or password. Please try again.");
            }
        }

        // Close the scanner after the loop
        scanner.close();
    }

    private static boolean checkSellerLogin(String username, String password) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String selectQuery = "SELECT * FROM sellers WHERE username = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();

                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
