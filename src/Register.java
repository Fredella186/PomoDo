import java.security.*;
import java.sql.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class Register {
    public static void register(Stage registerStage) throws Exception {

        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add("/assets/loginStyle.css");

        borderPane.getStyleClass().add("bgColor");

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);

        // Text welcome
        Text welcomeText = new Text("Welcome");
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

        // Label dan input username
        Label usernameLabel = new Label("Username");
        inputPane.add(usernameLabel, 0, 5);
        usernameLabel.getStyleClass().add("labelColor");

        TextField usernameInput = new TextField();
        usernameInput.setPrefWidth(399);
        usernameInput.setPrefHeight(51);
        inputPane.add(usernameInput, 0, 6);

        // Label dan input password
        Label passLabel = new Label("Password");
        inputPane.add(passLabel, 0, 7);
        passLabel.getStyleClass().add("labelColor");

        PasswordField passInput = new PasswordField();
        inputPane.add(passInput, 0, 8);
        passInput.setPrefWidth(399);
        passInput.setPrefHeight(51);

        GridPane buttonPane = new GridPane();
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setHgap(10);
        buttonPane.setVgap(10);

        // Button Log In
        Button btnSignUp = new Button("Sign Up");
        buttonPane.add(btnSignUp, 0, 7);
        btnSignUp.getStyleClass().add("button");
        btnSignUp.setPrefWidth(399);
        btnSignUp.setPrefHeight(51);

        btnSignUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    // Ambil input email dan password
                    String email = emailInput.getText();
                    String pass = passInput.getText();
                    String username = usernameInput.getText();
                    String hashedPassword = hashPassword(pass);

                    // Validate email and password
                    if (email.isEmpty()) {
                        // Display error message
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Registration Successful");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Please Fill in the Email");
                        successAlert.showAndWait();
                    } else if (pass.isEmpty()) {
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Registration Successful");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Please Fill in the Password");
                        successAlert.showAndWait();
                    } else if (username.isEmpty()) {
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Registration Successful");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Please Fill in the Username");
                        successAlert.showAndWait();
                    }

                    // Connect database
                    Connection connection = Dbconnect.getConnect();
                    // Insert data ke database
                    String sql = "INSERT INTO users (email, password,username) VALUES (?, ?,?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, email);
                    statement.setString(2, hashedPassword);
                    statement.setString(3, username);
                    statement.executeUpdate();

                    // Show a success alert for 5 seconds
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Registration Successful");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Registration Successful. Please Log In");
                    successAlert.showAndWait();

                    // Redirect to the app start page
                    App app = new App();
                    app.start(registerStage);
                } catch (SQLException e) {
                    // Show an error alert
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Registration Failed");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Registration Failed");
                    errorAlert.showAndWait();
                    // App.start(registerStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        box.getChildren().addAll(textPane, inputPane, buttonPane);
        BorderPane.setAlignment(box, Pos.CENTER);
        borderPane.setCenter(box);

        Text haveAccText = new Text("Have an account?");
        haveAccText.getStyleClass().add("accText");
        Hyperlink logInLink = new Hyperlink("Log In");

        logInLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    App app = new App();
                    app.start(registerStage);
                    // App.start(registerStage);
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
        registerStage.setTitle("Register");
        registerStage.setScene(scene);
        registerStage.show();
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