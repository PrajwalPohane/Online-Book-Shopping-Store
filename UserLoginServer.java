import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.util.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLoginServer {

    public static void main(String[] args) {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create a connection to the database
            String jdbcUrl = "jdbc:mysql://localhost:3306/bookstore2";
            String username = "root";
            String password = "prajwal.22210207@";
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Create an HTTP server with a backlog of 10
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 10);

            // Create a context for the login endpoint
            server.createContext("/login", new UserLoginHandler(connection));

            // Start the server
            server.start();

        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    static class UserLoginHandler implements HttpHandler {
        private final Connection connection;

        public UserLoginHandler(Connection connection) {
            this.connection = connection;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try {
                // Get the input stream to read data from the client
                exchange.getResponseHeaders().add("Content-Type", "text/plain");
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
                exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
                Scanner scanner = new Scanner(exchange.getRequestBody()).useDelimiter("\\A");
                String requestBody = scanner.hasNext() ? scanner.next() : "";

                // Parse the request body (assuming it's in the format "username=value&password=value")
                String[] parts = requestBody.split("&");
                String username = parts[0].split("=")[1];
                String password = parts[1].split("=")[1];

                // Check if the username and password match a record in the 'users' table
                String selectQuery = "SELECT * FROM users WHERE username = ? AND password = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    // Prepare the response
                    String response;
                    if (resultSet.next()) {
                        response = "Login successful!";
                    } else {
                        response = "Invalid username or password.";
                    }

                    // Send the response to the client
                    exchange.sendResponseHeaders(200, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                }

            }catch (SQLException e) {
                e.printStackTrace();
                exchange.sendResponseHeaders(500, 0);
            }
            finally {
                exchange.getResponseBody().close();
            }
        }
    }
}
