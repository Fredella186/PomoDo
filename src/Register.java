import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
public class Register {
    public static void register(Stage registerStage) throws Exception{

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
        textPane.setAlignment(Pos. CENTER);
    
        // Input 
        GridPane inputPane = new GridPane();
        inputPane.setAlignment(Pos. CENTER);
        inputPane.setHgap(10);
        inputPane.setVgap(10);
    
        //Label dan input email
        Label emailLabel = new Label("Email");
        inputPane.add(emailLabel,0,3);
        emailLabel.getStyleClass().add("labelColor");
    
        TextField emailInput = new TextField();
        inputPane.add(emailInput,0,4);
        emailInput.setPrefWidth(399);
        emailInput.setPrefHeight(51);
    
        // Label dan input password
        Label passLabel = new Label("Password");
        inputPane.add(passLabel,0,5);
        passLabel.getStyleClass().add("labelColor");
        
        PasswordField passInput = new PasswordField();
        inputPane.add(passInput,0,6);
        passInput.setPrefWidth(399);
        passInput.setPrefHeight(51);
    
        GridPane buttonPane = new GridPane();
        buttonPane.setAlignment(Pos. CENTER);
        buttonPane.setHgap(10);
        buttonPane.setVgap(10);
    
        // Button Log In
        Button btnSignUp = new Button("Sign Up");
        buttonPane.add(btnSignUp,0,7);
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
                    String hashedPassword = hashPassword(pass);

                    // Validate email and password
                    if (email.isEmpty()) {
                        // Display error message
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Registration Successful");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Silahkan Isi Email");
                        successAlert.showAndWait();
                    } else if (pass.isEmpty()) {
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Registration Successful");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Silahkan Isi Password");
                        successAlert.showAndWait();
                    }

                    // Connect database
                    Connection connection = Dbconnect.getConnect();
                    // Insert data ke database
                    String sql = "INSERT INTO user (email, password) VALUES (?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, email);
                    statement.setString(2, hashedPassword);
                    statement.executeUpdate();

                    // Show a success alert for 5 seconds
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Registrasi Berhasil");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Registrasi Berhasil");
                    successAlert.showAndWait();

                    // Redirect to the app start page
                    App app = new App();
                    app.start(registerStage);
                    } catch (SQLException e) {
                    // Show an error alert
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Registrasi Gagal");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Registrasi Gagal. Silahkan Coba Lagi");
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


        HBox haveAccPane = new HBox(haveAccText,logInLink);
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
