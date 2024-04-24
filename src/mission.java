import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class mission {
    public static void showMission(Stage showMissionStage) throws Exception{
        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add("/assets/missionStyle.css");
        borderPane.getStyleClass().add("bg");

        Text missionText = new Text("Mission");
        BorderPane.setAlignment(missionText, Pos.TOP_CENTER);
        borderPane.setTop(missionText);
        missionText.getStyleClass().add("missionText");

        Text numberText = new Text("29");
        numberText.getStyleClass().add("numberText");
        Image thunderImg = new Image(mission.class.getResourceAsStream("/assets/Image/Vector 74.png"));
        ImageView imageView = new ImageView();
        imageView.setImage(thunderImg);
        HBox dayBox = new HBox(numberText, imageView);

        Text daysText = new Text("days streak");
        daysText.getStyleClass().add("missionText");
        VBox streakBox = new VBox(dayBox, daysText);

        Text cmpnumberText = new Text("1012");
        cmpnumberText.getStyleClass().add("numberText");
        Image checkImg = new Image(mission.class.getResourceAsStream("/assets/Image/Union.png"));
        ImageView checkView = new ImageView();
        checkView.setImage(checkImg);
        HBox taskBox = new HBox(cmpnumberText, checkView);
        
        Text completedText = new Text("tasks completed");
        completedText.getStyleClass().add("missionText");
        VBox completedBox = new VBox(taskBox,completedText);

        HBox missionBox = new HBox(streakBox,completedBox);
        BorderPane.setAlignment(missionBox, Pos.CENTER);
        missionBox.setPadding(new Insets(20,20,20,20));
        borderPane.setCenter(missionBox);

        Image createImg = new Image(mission.class.getResourceAsStream("/assets/Image/Mask Group.png"));
        ImageView createView = new ImageView();
        createView.setImage(createImg);
        Text createText = new Text("Create 3 new tasks");
        createText.getStyleClass().add("createText");
        ProgressBar createBar = new ProgressBar(0);
        createBar.setPrefHeight(15);
        createBar.setPrefWidth(255);
        createBar.setProgress(0.3);
        createBar.getStyleClass().add("progress-bar");

        VBox createBarBox = new VBox(createText, createBar);  
        createBarBox.setSpacing(12);  
        HBox createMainBox = new HBox(createView,createBarBox);
        createMainBox.setSpacing(30);
        createMainBox.setPadding(new Insets(15,15,15,15));

        Image createLowImg = new Image(mission.class.getResourceAsStream("/assets/Image/Mask Group1.png"));
        ImageView createLowView = new ImageView();
        createLowView.setImage(createLowImg);
        Text createLowText = new Text("Complete 5 low tasks");
        createLowText.getStyleClass().add("createText");
        ProgressBar createLowBar = new ProgressBar(0);
        createLowBar.setPrefHeight(15);
        createLowBar.setPrefWidth(255);
        createLowBar.setProgress(0.3);

        VBox createLowBarBox = new VBox(createLowText, createLowBar);
        createLowBarBox.setSpacing(12);
        HBox createLowMainBox = new HBox(createLowView,createLowBarBox);
        createLowMainBox.setSpacing(30);
        createLowMainBox.setPadding(new Insets(15,15,15,15));

        Image createMedImg = new Image(mission.class.getResourceAsStream("/assets/Image/Mask Group1.png"));
        ImageView createMedView = new ImageView();
        createMedView.setImage(createMedImg);
        Text createMedText = new Text("Complete 2 medium tasks");
        createMedText.getStyleClass().add("createText");
        ProgressBar createMedBar = new ProgressBar(0);
        createMedBar.setPrefHeight(15);
        createMedBar.setPrefWidth(255);
        createMedBar.setProgress(0.3);

        VBox createMedBarBox = new VBox(createMedText, createMedBar);
        createMedBarBox.setSpacing(12);
        HBox createMedMainBox = new HBox(createMedView,createMedBarBox);
        createMedMainBox.setSpacing(30);
        createMedMainBox.setPadding(new Insets(15,15,15,15));

        Image createHighImg = new Image(mission.class.getResourceAsStream("/assets/Image/Mask Group1.png"));
        ImageView createHighView = new ImageView();
        createHighView.setImage(createHighImg);
        Text createHighText = new Text("Complete 1 high tasks");
        createHighText.getStyleClass().add("createText");
        ProgressBar createHighBar = new ProgressBar(0);
        createHighBar.setPrefHeight(15);
        createHighBar.setPrefWidth(255);
        createHighBar.setProgress(0.3);

        VBox createHighBarBox = new VBox(createHighText, createHighBar);
        createHighBarBox.setSpacing(12);
        HBox createHighMainBox = new HBox(createHighView,createHighBarBox);
        createHighMainBox.setSpacing(30);
        createHighMainBox.setPadding(new Insets(15,15,15,15));

        VBox missionCompletedBox = new VBox(createMainBox,createLowMainBox,createMedMainBox,createHighMainBox);
        missionCompletedBox.setPrefHeight(480);
        missionCompletedBox.setPrefWidth(450);
        missionCompletedBox.setPadding(new Insets(20,20,20,20));
        missionCompletedBox.getStyleClass().add("bgdown");

        BorderPane.setAlignment(missionCompletedBox, Pos.CENTER);
        borderPane.setBottom(missionCompletedBox);




        Scene scene = new Scene(borderPane, 450, 768);
        showMissionStage.setTitle("Mission");
        showMissionStage.setScene(scene);
        showMissionStage.show();

    }
}
