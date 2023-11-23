import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class registration {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcUrl = "jdbc:mysql://localhost:3306/bookstore2";
            String username = "root";
            String password = "prajwal.22210207@";
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            Scanner scanner = new Scanner(System.in);
            System.out.println("User Registration Form");
            System.out.print("Username: ");
            String usernameInput = scanner.nextLine();
            System.out.print("Email: ");
            String emailInput = scanner.nextLine();
            System.out.print("Password: ");
            String passwordInput = scanner.nextLine();
            System.out.print("Confirm Password: ");
            String confirmPasswordInput = scanner.nextLine();
            String insertQuery = "INSERT INTO users (username, email, password, confirm_password) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            try {
                preparedStatement.setString(1, usernameInput);
                preparedStatement.setString(2, emailInput);
                preparedStatement.setString(3, passwordInput);
                preparedStatement.setString(4, confirmPasswordInput);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User registered successfully!");
                } else {
                    System.out.println("Failed to register user.");
                }
            } catch (Throwable var15) {
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (Throwable var14) {
                        var15.addSuppressed(var14);
                    }
                }

                throw var15;
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            scanner.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException var16) {
            var16.printStackTrace();
        }

    }
}
