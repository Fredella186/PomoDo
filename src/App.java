import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
    launch(args);
}
   public void start(Stage primaryStage) throws Exception {
    
    BorderPane borderPane = new BorderPane();
    String css = this.getClass().getResource("/assets/loginStyle.css").toExternalForm();
    borderPane.getStylesheets().add(css);

    borderPane.getStyleClass().add("bgColor");

    VBox box = new VBox();
    box.setAlignment(Pos.CENTER);
    
    GridPane textPane = new GridPane();
    textPane.setAlignment(Pos. CENTER);
    textPane.setHgap(1);
    textPane.setVgap(1);
    
    Text welcomeText = new Text("Welcome");
    textPane.add(welcomeText,0,1);
    welcomeText.getStyleClass().add("fontWelcome");

    Text detailText = new Text("Please enter your details");
    textPane.add(detailText,0,2);

    GridPane inputPane = new GridPane();
    inputPane.setAlignment(Pos. CENTER);
    inputPane.setHgap(10);
    inputPane.setVgap(10);

    Label emailLabel = new Label("Email");
    inputPane.add(emailLabel,0,3);
    emailLabel.getStyleClass().add("labelColor");

    TextField emailInput = new TextField();
    inputPane.add(emailInput,0,4);
    emailInput.setPrefWidth(399);
    emailInput.setPrefHeight(51);

    Label passLabel = new Label("Password");
    inputPane.add(passLabel,0,5);
    passLabel.getStyleClass().add("labelColor");
    
    TextField passInput = new TextField();
    inputPane.add(passInput,0,6);
    passInput.setPrefWidth(399);
    passInput.setPrefHeight(51);

    GridPane buttonPane = new GridPane();
    buttonPane.setAlignment(Pos. CENTER);
    buttonPane.setHgap(10);
    buttonPane.setVgap(10);

    Button btnSignUp = new Button("Log In");
    buttonPane.add(btnSignUp,0,7);
    btnSignUp.getStyleClass().add("button");
    btnSignUp.setPrefWidth(399);
    btnSignUp.setPrefHeight(51);

    btnSignUp.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                Todolist.show(primaryStage); // Call the Todolist's add method to display the stage
                // primaryStage.close(); // Close the login stage after successful login
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    box.getChildren().addAll(textPane, inputPane, buttonPane);
    BorderPane.setAlignment(box, Pos.CENTER);
    borderPane.setCenter(box);

    Scene scene = new Scene(borderPane, 1280, 768);
    primaryStage.setTitle("Login");
    primaryStage.setScene(scene);
    primaryStage.show();
   }
}