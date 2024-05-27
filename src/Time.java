import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Time extends Application {

    private static int elapsedTime = 0; // Elapsed time in seconds
    private static Timeline timeline;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage showTimeStage) throws Exception {
        showTime(showTimeStage);
    }

    public static void showTime(Stage showTimeStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add("/assets/timeStyle.css");
        borderPane.setPadding(new Insets(0,0,40,0));
        borderPane.getStyleClass().add("bg"); // Apply bg class here

        Scene scene = new Scene(borderPane, 380, 450); // Adjust scene size

        double progressBarSize = 300; // Adjust the size here
        double progressWidth = 20; // Example progress width
        double centerBorderWidth = 10; // Example center border width

        CircularProgressbar timeBar = new CircularProgressbar(progressBarSize, progressWidth, centerBorderWidth); // Use progressBarSize here
        Text timeText = new Text("00:30"); // Initial time text
        HBox timeBox = new HBox(timeBar); // Remove timeText from here
        timeBox.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(timeBox, Pos.CENTER);
        borderPane.setCenter(timeBox);

        Image refreshTimeImg = new Image(Time.class.getResourceAsStream("/assets/Image/Group 2.png"));
        ImageView refreshTimeView = new ImageView();
        refreshTimeView.setImage(refreshTimeImg);
        refreshTimeView.setOnMouseClicked(event -> resetTimer(timeText, timeBar));

        Image playTimeImg = new Image(Time.class.getResourceAsStream("/assets/Image/Group 1.png"));
        ImageView playTimeView = new ImageView();
        playTimeView.setImage(playTimeImg);
        playTimeView.setOnMouseClicked(event -> toggleTimer(timeText, timeBar));

        HBox playBox = new HBox(refreshTimeView, playTimeView);
        playBox.setAlignment(Pos.CENTER);
        playBox.setSpacing(15);
        BorderPane.setAlignment(playBox, Pos.BOTTOM_CENTER);
        borderPane.setBottom(playBox);

        showTimeStage.setTitle("Time");
        showTimeStage.setScene(scene);
        showTimeStage.show();

        startTimer(timeText, timeBar); // Start the timer here
    }   

    private static void toggleTimer(Text timeText, CircularProgressbar timeBar) {
        if (timeline == null) {
            startTimer(timeText, timeBar);
        } else {
            if (timeline.getStatus() == Timeline.Status.RUNNING) {
                timeline.pause();
            } else {
                timeline.play();
            }
        }
    }

    private static void startTimer(Text timeText, CircularProgressbar timeBar) {
        String[] timeParts = timeText.getText().split(":");
        int totalSeconds = Integer.parseInt(timeParts[0]) * 60 + Integer.parseInt(timeParts[1]);

        timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), event -> {
                if (elapsedTime < totalSeconds) { // Add a check to prevent negative time
                    elapsedTime++;
                    double progress = (double) elapsedTime / totalSeconds;
                    timeText.setText(formatTime(totalSeconds - elapsedTime)); // Update time text
                    timeBar.draw((1 - progress) * 100, timeText.getText()); // Pass progress percentage as double
                } else {
                    timeline.stop(); // Stop the timer when it reaches zero
                }
            })
        );
        timeline.setCycleCount(totalSeconds);
        timeline.play();
    }

    private static void resetTimer(Text timeText, CircularProgressbar timeBar) {
        if (timeline != null) {
            timeline.stop();
        }
        elapsedTime = 0;
        timeText.setText("00:30"); // Reset time to initial value
        timeBar.draw(100, timeText.getText()); // Reset progress bar and pass initial timer text
    }

    private static String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }
}