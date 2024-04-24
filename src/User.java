import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class User {

    public static void profile(Stage profileStage) throws Exception{
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20,20,20,20));
        borderPane.getStylesheets().add("/assets/profileStyle.css");
        borderPane.getStyleClass().add("bgColor");

        Text profileText = new Text("Profile");
        BorderPane.setAlignment(profileText, Pos.TOP_CENTER);
        borderPane.setTop(profileText);
        profileText.getStyleClass().add("profileText");

        Label usernameLabel = new Label("Username");
        usernameLabel.getStyleClass().add("labelText");
        TextField usernameField = new TextField();
        usernameField.setText("Josh");
        usernameField.getStyleClass().add("inputText");
        VBox usernameBox = new VBox(usernameLabel,usernameField);
        usernameField.setPrefWidth(280);
        usernameField.setPrefHeight(30);
        usernameBox.setSpacing(4);

        Label emailLabel = new Label("Email");
        emailLabel.getStyleClass().add("labelText");
        TextField emailField = new TextField();
        emailField.setText("josh@email.com");
        emailField.getStyleClass().add("inputText");
        VBox emailBox = new VBox(emailLabel,emailField);
        emailField.setPrefWidth(280);
        emailField.setPrefHeight(30);
        // emailField.getStyleClass().add("input");
        emailBox.setSpacing(4);

        VBox inputBox = new VBox(usernameBox,emailBox);
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
        logoutLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    App app = new App();
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

    public static void editProfile(Stage editProfileStage) throws Exception{
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20,20,20,20));
        borderPane.getStylesheets().add("/assets/profileStyle.css");
        borderPane.getStyleClass().add("bgColor");

        Text profileText = new Text("Edit Profile");
        BorderPane.setAlignment(profileText, Pos.TOP_CENTER);
        borderPane.setTop(profileText);
        profileText.getStyleClass().add("profileText");

        Label usernameLabel = new Label("Username");
        usernameLabel.getStyleClass().add("labelText");
        TextField usernameField = new TextField();
        usernameField.setText("Josh");
        VBox usernameBox = new VBox(usernameLabel,usernameField);
        usernameField.setPrefWidth(280);
        usernameField.setPrefHeight(30);
        usernameBox.setSpacing(4);

        Label emailLabel = new Label("Email");
        emailLabel.getStyleClass().add("labelText");
        TextField emailField = new TextField();
        emailField.setText("josh@email.com");
        VBox emailBox = new VBox(emailLabel,emailField);
        emailField.setPrefWidth(280);
        emailField.setPrefHeight(30);
        // emailField.getStyleClass().add("input");
        emailBox.setSpacing(4);

        VBox inputBox = new VBox(usernameBox,emailBox);
        inputBox.setSpacing(18);
        BorderPane.setAlignment(inputBox, Pos.CENTER);
        borderPane.setCenter(inputBox);

        Button passBtn = new Button("Change Password");
        passBtn.getStyleClass().add("passBtn");

        passBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    profilePass(new Stage());
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
        VBox btnBox = new VBox(passBtn,profileBtn);
        BorderPane.setAlignment(btnBox, Pos.BOTTOM_CENTER);
        borderPane.setBottom(btnBox);


        Scene scene = new Scene(borderPane, 320, 341);
        editProfileStage.setTitle("Profile");
        editProfileStage.setScene(scene);
        editProfileStage.show();
        
    }

    public static void profilePass(Stage profilePassStage) throws Exception{
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20,20,20,20));
        borderPane.getStylesheets().add("/assets/profileStyle.css");
        borderPane.getStyleClass().add("bgColor");

        Text profileText = new Text("Change Password");
        BorderPane.setAlignment(profileText, Pos.TOP_CENTER);
        borderPane.setTop(profileText);
        profileText.getStyleClass().add("profileText");

        Label usernameLabel = new Label("Current Password");
        usernameLabel.getStyleClass().add("labelText");
        TextField usernameField = new TextField();
        VBox usernameBox = new VBox(usernameLabel,usernameField);
        usernameField.setPrefWidth(280);
        usernameField.setPrefHeight(30);
        usernameBox.setSpacing(4);

        Label emailLabel = new Label("New Password");
        emailLabel.getStyleClass().add("labelText");
        TextField emailField = new TextField();
        VBox emailBox = new VBox(emailLabel,emailField);
        emailField.setPrefWidth(280);
        emailField.setPrefHeight(30);
        emailBox.setSpacing(4);

        Label newLabel = new Label("New Password");
        newLabel.getStyleClass().add("labelText");
        TextField newField = new TextField();
        VBox newBox = new VBox(newLabel,newField);
        newField.setPrefWidth(280);
        newField.setPrefHeight(30);
        newBox.setSpacing(4);

        VBox inputBox = new VBox(usernameBox,emailBox,newBox);
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


        Scene scene = new Scene(borderPane, 320, 341);
        profilePassStage.setTitle("Change Password");
        profilePassStage.setScene(scene);
        profilePassStage.show();
        
    }
}
