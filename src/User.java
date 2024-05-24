import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.application.Platform;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class User {

    public static void profile(Stage profileStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.getStylesheets().add("/assets/profileStyle.css");
        borderPane.getStyleClass().add("bgColor");

        Text profileText = new Text("Profile");
        BorderPane.setAlignment(profileText, Pos.TOP_CENTER);
        borderPane.setTop(profileText);
        profileText.getStyleClass().add("profileText");

        Label usernameLabel = new Label("Username");
        usernameLabel.getStyleClass().add("labelText");
        TextField usernameField = new TextField();
        // usernameField.setText("Josh");

        try {
            Connection connection = Dbconnect.getConnect();
            String sql = "SELECT username FROM users WHERE id =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, App.currentUserId);
            ResultSet rs = statement.executeQuery();
            rs.next();
            usernameField.setText(rs.getString("username"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        usernameField.getStyleClass().add("inputText");
        VBox usernameBox = new VBox(usernameLabel, usernameField);
        usernameField.setPrefWidth(280);
        usernameField.setPrefHeight(30);
        usernameBox.setSpacing(4);

        Label emailLabel = new Label("Email");
        emailLabel.getStyleClass().add("labelText");
        TextField emailField = new TextField();
        // emailField.setText("josh@email.com");

        try {
            Connection connection = Dbconnect.getConnect();
            String sql = "SELECT email FROM users WHERE id =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, App.currentUserId);
            ResultSet rs = statement.executeQuery();
            rs.next();
            emailField.setText(rs.getString("email"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        emailField.getStyleClass().add("inputText");
        VBox emailBox = new VBox(emailLabel, emailField);
        emailField.setPrefWidth(280);
        emailField.setPrefHeight(30);
        // emailField.getStyleClass().add("input");
        emailBox.setSpacing(4);

        VBox inputBox = new VBox(usernameBox, emailBox);
        inputBox.setSpacing(18);
        BorderPane.setAlignment(inputBox, Pos.CENTER);
        borderPane.setCenter(inputBox);

        Button profileBtn = new Button("Edit Profile");
        profileBtn.setPrefWidth(280);
        profileBtn.setPrefHeight(30);
        profileBtn.getStyleClass().add("profileBtn");

        profileBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    editProfile(new Stage());
                    profileStage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Hyperlink logoutLink = new Hyperlink("Log Out");
        logoutLink.setPadding(new Insets(10, 100, 0, 100));
        logoutLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    App app = new App();
                    app.start(new Stage());
                    profileStage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        logoutLink.getStyleClass().add("linkText");
        VBox btnBox = new VBox(profileBtn, logoutLink);
        BorderPane.setAlignment(btnBox, Pos.BOTTOM_CENTER);
        borderPane.setBottom(btnBox);

        Scene scene = new Scene(borderPane, 320, 341);
        profileStage.setTitle("Profile");
        profileStage.setScene(scene);
        profileStage.show();

    }

    public static void editProfile(Stage editProfileStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.getStylesheets().add("/assets/profileStyle.css");
        borderPane.getStyleClass().add("bgColor");

        Text profileText = new Text("Edit Profile");
        BorderPane.setAlignment(profileText, Pos.TOP_CENTER);
        borderPane.setTop(profileText);
        profileText.getStyleClass().add("profileText");

        Label usernameLabel = new Label("Username");
        usernameLabel.getStyleClass().add("labelText");
        TextField usernameField = new TextField();
        // usernameField.setText("Josh");

        try {
            Connection connection = Dbconnect.getConnect();
            String sql = "SELECT username FROM users WHERE id =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, App.currentUserId);
            ResultSet rs = statement.executeQuery();
            rs.next();
            usernameField.setText(rs.getString("username"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        VBox usernameBox = new VBox(usernameLabel, usernameField);
        usernameField.setPrefWidth(280);
        usernameField.setPrefHeight(30);
        usernameBox.setSpacing(4);

        Label emailLabel = new Label("Email");
        emailLabel.getStyleClass().add("labelText");
        TextField emailField = new TextField();
        // emailField.setText("josh@email.com");

        try {
            Connection connection = Dbconnect.getConnect();
            String sql = "SELECT email FROM users WHERE id =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, App.currentUserId);
            ResultSet rs = statement.executeQuery();
            rs.next();
            emailField.setText(rs.getString("email"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        VBox emailBox = new VBox(emailLabel, emailField);
        emailField.setPrefWidth(280);
        emailField.setPrefHeight(30);
        // emailField.getStyleClass().add("input");
        emailBox.setSpacing(4);

        VBox inputBox = new VBox(usernameBox, emailBox);
        inputBox.setSpacing(18);
        BorderPane.setAlignment(inputBox, Pos.CENTER);
        borderPane.setCenter(inputBox);

        Button passBtn = new Button("Change Password");
        passBtn.setPadding(new Insets(0, 0, 20, 0));
        passBtn.getStyleClass().add("passBtn");

        passBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    chagePass(new Stage());
                    editProfileStage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button profileBtn = new Button("Edit Profile");
        profileBtn.setPrefWidth(280);
        profileBtn.setPrefHeight(30);
        profileBtn.getStyleClass().add("profileBtn");

        profileBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String username = usernameField.getText();
                    String email = emailField.getText();

                    Connection connection = Dbconnect.getConnect();
                    String sql = "UPDATE users SET username =?, email =? WHERE id =?";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, username);
                    statement.setString(2, email);
                    statement.setInt(3, App.currentUserId);

                    statement.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Profile");
                    alert.setHeaderText(null);
                    alert.setContentText("Profile Updated");
                    alert.showAndWait();

                    editProfileStage.close();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                User.profile(new Stage());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        });

        VBox btnBox = new VBox(passBtn, profileBtn);
        BorderPane.setAlignment(btnBox, Pos.BOTTOM_CENTER);
        borderPane.setBottom(btnBox);

        Scene scene = new Scene(borderPane, 320, 341);
        editProfileStage.setTitle("Profile");
        editProfileStage.setScene(scene);
        editProfileStage.show();

    }

    public static void chagePass(Stage profilePassStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.getStylesheets().add("/assets/profileStyle.css");
        borderPane.getStyleClass().add("bgColor");

        Text profileText = new Text("Change Password");
        BorderPane.setAlignment(profileText, Pos.TOP_CENTER);
        borderPane.setTop(profileText);
        profileText.getStyleClass().add("profileText");

        Label usernameLabel = new Label("Current Password");
        usernameLabel.getStyleClass().add("labelText");
        PasswordField usernameField = new PasswordField();
        VBox usernameBox = new VBox(usernameLabel, usernameField);
        usernameField.setPrefWidth(280);
        usernameField.setPrefHeight(30);
        usernameBox.setSpacing(4);

        Label emailLabel = new Label("New Password");
        emailLabel.getStyleClass().add("labelText");
        PasswordField emailField = new PasswordField();
        VBox emailBox = new VBox(emailLabel, emailField);
        emailField.setPrefWidth(280);
        emailField.setPrefHeight(30);
        emailBox.setSpacing(4);

        Label newLabel = new Label("Confirm New Password");
        newLabel.getStyleClass().add("labelText");
        PasswordField newField = new PasswordField();
        VBox newBox = new VBox(newLabel, newField);
        newField.setPrefWidth(280);
        newField.setPrefHeight(30);
        newBox.setSpacing(4);

        VBox inputBox = new VBox(usernameBox, emailBox, newBox);
        inputBox.setSpacing(18);
        BorderPane.setAlignment(inputBox, Pos.CENTER);
        borderPane.setCenter(inputBox);

        Button profileBtn = new Button("Edit Profile");
        profileBtn.setPrefWidth(280);
        profileBtn.setPrefHeight(30);
        profileBtn.getStyleClass().add("profileBtn");
        VBox btnBox = new VBox(profileBtn);
        BorderPane.setAlignment(btnBox, Pos.BOTTOM_CENTER);
        borderPane.setBottom(btnBox);

        profileBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String currentPass = usernameField.getText();
                    String newPass = emailField.getText();
                    String newPass2 = newField.getText();
                    String hashedCurrentPass = hashPassword(currentPass);
                    String hashedNewPass = hashPassword(newPass);
                    String hashedNewPass2 = hashPassword(newPass2);

                    Connection connection = Dbconnect.getConnect();

                    if (newPass.equals(newPass2)) {
                        String sqlPass = "SELECT password FROM users WHERE id=?";
                        PreparedStatement statement = connection.prepareStatement(sqlPass);
                        statement.setInt(1, App.currentUserId);
                        ResultSet rs = statement.executeQuery();

                        if (rs.next()) {
                            String storedHashedPass = rs.getString("password");
                            if (hashedCurrentPass.equals(storedHashedPass)) {
                                String sqlUpdate = "UPDATE users SET password=? WHERE id=?";
                                PreparedStatement statement2 = connection.prepareStatement(sqlUpdate);
                                statement2.setString(1, hashedNewPass);
                                statement2.setInt(2, App.currentUserId);
                                statement2.executeUpdate();
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Change Password");
                                alert.setHeaderText(null);
                                alert.setContentText("Successfully changed password");
                                alert.showAndWait();
                                profilePassStage.close();
                                // Todolist td = new Todolist();
                                // td.show(showStage);

                                App app = new App();
                                app.start(profilePassStage);
                                connection.close();
                            } else {
                                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                errorAlert.setTitle("Change Password");
                                errorAlert.setHeaderText(null);
                                errorAlert.setContentText("Old password does not match");
                                errorAlert.showAndWait();
                            }
                        }
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Change Password");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Passwords do not match");
                        errorAlert.showAndWait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Scene scene = new Scene(borderPane, 320, 341);
        profilePassStage.setTitle("Change Password");
        profilePassStage.setScene(scene);
        profilePassStage.show();

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