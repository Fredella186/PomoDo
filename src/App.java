import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class App extends Application {

    public static int currentUserId;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {

        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add("/assets/loginStyle.css");
        borderPane.getStyleClass().add("bgColor");

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);

        // Text welcome
        Text welcomeText = new Text("Welcome Back");
        welcomeText.getStyleClass().add("fontWelcome");

        Text detailText = new Text("Please enter your details");
        detailText.setTextAlignment(TextAlignment.CENTER);
        detailText.getStyleClass().add("text");

        VBox textPane = new VBox(welcomeText, detailText);
        textPane.setAlignment(Pos.CENTER);

        // Input
        GridPane inputPane = new GridPane();
        inputPane.setAlignment(Pos.CENTER);
        inputPane.setHgap(10);
        inputPane.setVgap(10);

        // Label dan input email
        Label emailLabel = new Label("Email");
        inputPane.add(emailLabel, 0, 3);
        emailLabel.getStyleClass().add("labelColor");

        TextField emailInput = new TextField();
        inputPane.add(emailInput, 0, 4);
        emailInput.setPrefWidth(399);
        emailInput.setPrefHeight(51);

        // Label dan input password
        Label passLabel = new Label("Password");
        inputPane.add(passLabel, 0, 5);
        passLabel.getStyleClass().add("labelColor");

        PasswordField passInput = new PasswordField();
        inputPane.add(passInput, 0, 6);
        passInput.setPrefWidth(399);
        passInput.setPrefHeight(51);

        GridPane buttonPane = new GridPane();
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setHgap(10);
        buttonPane.setVgap(10);

        // Button Log In
        Button btnLogIn = new Button("Log In");
        buttonPane.add(btnLogIn, 0, 7);
        btnLogIn.getStyleClass().add("button");
        btnLogIn.setPrefWidth(399);
        btnLogIn.setPrefHeight(51);

        btnLogIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    // Get email and password from input fields
                    String email = emailInput.getText();
                    String pass = passInput.getText();
                    String hashedPassword = hashPassword(pass);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
                    LocalDate now = LocalDate.now();
                    String nowDate = now.format(formatter);

                    // Connect to the database
                    Connection connection = Dbconnect.getConnect();

                    // Prepare a query to SELECT user ID with email and password
                    String sql = "SELECT id FROM users WHERE email = ? AND password = ?";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, email);
                    statement.setString(2, hashedPassword);

                    // Execute the query and get the result set
                    ResultSet resultSet = statement.executeQuery();

                    // Check if a user was found
                    if (resultSet.next()) {
                        // Get the user ID from the result set
                        int userId = resultSet.getInt("id");
                        App.currentUserId = userId;

                        System.out.println("Login successful! User ID: " + userId);

                        Todolist.show(primaryStage);
                    } else {
                        // User not found (invalid login)
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Login Failed");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Login failed. Please check your email and password.");
                        errorAlert.showAndWait();
                    }

                    // Update the last login date
                    try {
                        String sqlUpdate = "UPDATE users SET last_login = ? WHERE id = ?";
                        PreparedStatement statementUpdate = connection.prepareStatement(sqlUpdate);
                        statementUpdate.setString(1, nowDate);
                        statementUpdate.setInt(2, App.currentUserId); // Use App.currentUserId
                        statementUpdate.executeUpdate(); // Use executeUpdate() for UPDATE statement
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    resultSet.close();
                    statement.close();
                } catch (SQLException e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Login Failed");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Login failed. Please check your email and password.");
                    errorAlert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        box.getChildren().addAll(textPane, inputPane, buttonPane);
        BorderPane.setAlignment(box, Pos.CENTER);
        borderPane.setCenter(box);

        Text haveAccText = new Text("Don't have an account?");
        haveAccText.getStyleClass().add("accText");
        Hyperlink logInLink = new Hyperlink("Sign Up");

        logInLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Register.register(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        HBox haveAccPane = new HBox(haveAccText, logInLink);
        haveAccPane.setAlignment(Pos.CENTER);
        haveAccPane.setPadding(new Insets(0, 0, 50, 0));
        borderPane.setBottom(haveAccPane);

        Scene scene = new Scene(borderPane, 1280, 768);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static String hashPassword(String password) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256"); // Pilih algoritma
        byte[] bytes = md.digest(password.getBytes()); // Hash password menjadi byte
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(String.format("%02x", bytes[i])); // Konversi byte ke hexadecimal
        }
        return sb.toString(); // Kembalikan string hexadecimal
    }
}