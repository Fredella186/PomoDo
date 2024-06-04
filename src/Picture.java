import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Picture extends Application{

    public static List<String> tasksPic;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage showPicStage) throws Exception {
        showPic(showPicStage);
    }

    public static String[] getPic() throws SQLException{
        Connection connection = Dbconnect.getConnect();
        String sql = "SELECT picture FROM task_picture WHERE user_id = ? AND task_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, App.currentUserId);
        statement.setString(2, Todolist.currentTaskId);
        ResultSet resultSet = statement.executeQuery();
        List<String> tasksPicList = new ArrayList<>();
        Picture.tasksPic = tasksPicList;

        while (resultSet.next()) {
        tasksPicList.add(resultSet.getString(1));
        }

        // Convert the list to a String array (if needed)
        String[] tasksPicArray = tasksPicList.toArray(new String[tasksPicList.size()]);

        resultSet.close();
        statement.close();
        connection.close();

        return tasksPicArray;
    }
    public static void showPic(Stage showPicStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        // borderPane.getStylesheets().add("/assets/addTaskStyle.css");
        
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER); // Center alignment for VBox itself
        vbox.setSpacing(10);
        
        ScrollPane scrollPane = new ScrollPane(vbox); // Use ScrollPane instead of StackPane
        scrollPane.setFitToWidth(true); // ScrollPane width to fit
        
        String[] tasksPic = getPic(); // This method should return an array of image file names
        var i = 1;
        for (String pic : tasksPic) {
            String picPath = System.getProperty("user.dir") + "/src/screenshots/" + pic;
            File picFile = new File(picPath);
            if (picFile.exists()) {
                Text ssText = new Text("Screenshot " + (i++));
                ssText.getStyleClass().add("text");
                ImageView imageView = new ImageView(new Image(picFile.toURI().toString()));
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(300); // set the desired height
                imageView.setFitWidth(640); // set the desired width
                VBox.setMargin(ssText, new Insets(10, 0, 0, 0)); // Optional: margin between Text and ImageView
                vbox.getChildren().addAll(ssText, imageView);
            } else {
                System.out.println("File not found: " + picPath);
            }
        }
        
        borderPane.setCenter(scrollPane); // Set ScrollPane in the center of BorderPane
        BorderPane.setAlignment(scrollPane, Pos.CENTER); // Align ScrollPane to center
        Scene scene = new Scene(borderPane, 640, 768);
        scene.getStylesheets().add("/assets/pictureStyle.css");
        borderPane.getStyleClass().add("bgColor");
        vbox.getStyleClass().add("bgColor");
        showPicStage.setTitle("Picture");
        showPicStage.setScene(scene);
        showPicStage.show();
    }
}