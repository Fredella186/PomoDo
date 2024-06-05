import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public static String getTime() throws SQLException {
        Connection connection = Dbconnect.getConnect();
        String sql = "SELECT target_time FROM tasks WHERE created_by = ? AND id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, App.currentUserId);
        statement.setString(2, Todolist.currentTaskId);

        String targetTime = null;
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            targetTime = resultSet.getString("target_time");
        }
        resultSet.close();
        statement.close();
        connection.close();

        return targetTime;
    }

    public static int getElapsedTime() throws SQLException {
        Connection connection = Dbconnect.getConnect();
        String sql = "SELECT elapsed_time FROM tasks WHERE created_by = ? AND id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, App.currentUserId);
        statement.setString(2, Todolist.currentTaskId);

        int elapsed = 0;
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            elapsed = resultSet.getInt("elapsed_time");
        }
        resultSet.close();
        statement.close();
        connection.close();

        return elapsed;
    }

    public static void saveElapsedTime(int elapsedTime) throws SQLException {
        Connection connection = Dbconnect.getConnect();
        String sql = "UPDATE tasks SET elapsed_time = ? WHERE created_by = ? AND id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, elapsedTime);
        statement.setInt(2, App.currentUserId);
        statement.setString(3, Todolist.currentTaskId);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    public static void showTime(Stage showTimeStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add("/assets/timeStyle.css");
        borderPane.setPadding(new Insets(0, 0, 40, 0));
        borderPane.getStyleClass().add("bg"); // Apply bg class here

        Scene scene = new Scene(borderPane, 380, 450); // Adjust scene size

        double progressBarSize = 300; // Adjust the size here
        double progressWidth = 20; // Example progress width
        double centerBorderWidth = 10; // Example center border width

        CircularProgressbar timeBar = new CircularProgressbar(progressBarSize, progressWidth, centerBorderWidth); // Use
                                                                                                                  // progressBarSize
                                                                                                                  // here
        Text timeText = new Text();
        try {
            Connection connection = Dbconnect.getConnect();
            String sql = "SELECT target_time FROM tasks WHERE created_by=? AND id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, App.currentUserId);
            statement.setString(2, Todolist.currentTaskId);
            ResultSet rs = statement.executeQuery();
            rs.next();
            timeText.setText(rs.getString("target_time"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        elapsedTime = getElapsedTime(); // Initialize elapsed time from database

        Text elapsedTimeText = new Text("Elapsed Time: " + formatTime(elapsedTime)); // Add elapsed time text

        HBox timeBox = new HBox(timeBar); // Remove timeText from here
        timeBox.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(timeBox, Pos.CENTER);
        borderPane.setCenter(timeBox);

        Image playTimeImg = new Image(Time.class.getResourceAsStream("/assets/Image/Group 1.png"));
        ImageView playTimeView = new ImageView();
        playTimeView.setImage(playTimeImg);
        playTimeView.setOnMouseClicked(event -> {
            try {
                toggleTimer(timeText, timeBar, elapsedTimeText);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        Image finishImg = new Image(Time.class.getResourceAsStream("/assets/Image/finish.png"));
        ImageView finishView = new ImageView();
        finishView.setImage(finishImg);
        finishView.setOnMouseClicked(event -> {
            try {
                ScreenCapture.running = false;
                ScreenCapture.imagesToPdf();
                stopTimer(timeText, timeBar, elapsedTimeText);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        HBox playBox = new HBox(playTimeView);
        playBox.setAlignment(Pos.CENTER);
        playBox.setSpacing(15);

        HBox elapsedTimeBox = new HBox(elapsedTimeText, playBox, finishView); // Add playBox and finishView to
                                                                              // elapsedTimeBox
        elapsedTimeBox.setAlignment(Pos.CENTER);
        elapsedTimeBox.setSpacing(15);
        BorderPane.setAlignment(elapsedTimeBox, Pos.BOTTOM_CENTER);
        borderPane.setBottom(elapsedTimeBox);

        showTimeStage.setTitle("Time");
        showTimeStage.setScene(scene);
        showTimeStage.show();

        startTimer(timeText, timeBar, elapsedTimeText); // Start the timer here
    }

    private static void toggleTimer(Text timeText, CircularProgressbar timeBar, Text elapsedTimeText)
            throws SQLException {
        if (timeline == null) {
            startTimer(timeText, timeBar, elapsedTimeText);
        } else {
            if (timeline.getStatus() == Timeline.Status.RUNNING) {
                timeline.pause();
                saveElapsedTime(elapsedTime); // Save elapsed time to database on pause
            } else {
                timeline.play();
            }
        }
    }

    private static void startTimer(Text timeText, CircularProgressbar timeBar, Text elapsedTimeText) {
        String[] timeParts = timeText.getText().split(":");
        int totalSeconds = Integer.parseInt(timeParts[0]) * 3600 + Integer.parseInt(timeParts[1]) * 60
                + Integer.parseInt(timeParts[2]);

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    if (elapsedTime < totalSeconds) { // Add a check to prevent negative time
                        elapsedTime++;
                        double progress = (double) elapsedTime / totalSeconds;
                        timeText.setText(formatTime(totalSeconds - elapsedTime)); // Update time text
                        timeBar.draw((1 - progress) * 100, timeText.getText()); // Pass progress percentage as double
                        elapsedTimeText.setText("Elapsed Time: " + formatTime(elapsedTime)); // Update elapsed time text
                    } else {
                        timeline.stop(); // Stop the timer when it reaches zero
                    }
                }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private static void stopTimer(Text timeText, CircularProgressbar timeBar, Text elapsedTimeText) {
        if (timeline != null) {
            timeline.stop();
            try {
                saveElapsedTime(elapsedTime); // Save elapsed time to database on stop
            } catch (SQLException e) {
                e.printStackTrace();
            }
            timeline = null; // Clear the timeline
        }
        timeText.setText("--:--:--"); // Set the timer text to "--"
        timeBar.draw(0, "--:--:--"); // Clear the progress bar
        elapsedTimeText.setText("Elapsed Time: " + formatTime(elapsedTime)); // Ensure elapsed time text remains the
                                                                             // same
    }

    private static String formatTime(int seconds) {
        int hours = seconds / 3600;
        int remainingSeconds = seconds % 3600;
        int minutes = remainingSeconds / 60;
        int secs = remainingSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }
}