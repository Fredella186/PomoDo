import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Time extends Application {

    private static int elapsedTime = 0; // Elapsed time in seconds
    private static Timeline workTimeline;
    private static Timeline breakTimeline;

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
        Text breakTimeText = new Text(); // Add text to show break time countdown

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

                Connection connection = Dbconnect.getConnect();
                String sql = "UPDATE tasks SET new_priority_id = '4' WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, Todolist.currentTaskId);
                statement.executeUpdate();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Todolist.show(new Stage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
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

        startTimer(timeText, timeBar, elapsedTimeText, breakTimeText, scene); // Start the timer here
    }

    private static void toggleTimer(Text timeText, CircularProgressbar timeBar, Text elapsedTimeText)
            throws SQLException {
        if (workTimeline == null) {
            startTimer(timeText, timeBar, elapsedTimeText, null, null);
        } else {
            if (workTimeline.getStatus() == Timeline.Status.RUNNING) {
                workTimeline.pause();
                saveElapsedTime(elapsedTime); // Save elapsed time to database on pause
            } else {
                workTimeline.play();
            }
        }
    }

    private static void startTimer(Text timeText, CircularProgressbar timeBar, Text elapsedTimeText, Text breakTimeText, Scene scene) {
        String[] timeParts = timeText.getText().split(":");
        int totalSeconds = Integer.parseInt(timeParts[0]) * 3600 + Integer.parseInt(timeParts[1]) * 60
                + Integer.parseInt(timeParts[2]);

        workTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    if (elapsedTime < totalSeconds) { // Add a check to prevent negative time
                        elapsedTime++;
                        double progress = (double) elapsedTime / totalSeconds;
                        timeText.setText(formatTime(totalSeconds - elapsedTime)); // Update time text
                        timeBar.draw((1 - progress) * 100, timeText.getText()); // Pass progress percentage as double
                        elapsedTimeText.setText("Elapsed Time: " + formatTime(elapsedTime)); // Update elapsed time text

                        if (elapsedTime % (1 * 10) == 0) { // Show break notification every 5 minutes
                            workTimeline.pause();
                            Platform.runLater(() -> showBreakNotification(timeText, timeBar, elapsedTimeText, breakTimeText, scene));
                        }
                    } else {
                        workTimeline.stop(); // Stop the timer when it reaches zero
                    }
                }));
        workTimeline.setCycleCount(Timeline.INDEFINITE);
        workTimeline.play();
    }

    private static void showBreakNotification(Text timeText, CircularProgressbar timeBar, Text elapsedTimeText, Text breakTimeText, Scene scene) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Take a 5-minute break?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Break Time");
        alert.setHeaderText(null);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                startBreakTimer(timeText, timeBar, elapsedTimeText, breakTimeText, scene);
            } else {
                workTimeline.play();
            }
        });
    }

    private static void startBreakTimer(Text timeText, CircularProgressbar timeBar, Text elapsedTimeText, Text breakTimeText, Scene scene) {
        final int breakDuration = 1 * 10; // 5 minutes in seconds
        final int[] breakTimeElapsed = {0};
    
        // Save the elements currently at the bottom
        HBox bottomContent = (HBox) ((BorderPane) scene.getRoot()).getBottom();
    
        BorderPane borderPane = (BorderPane) scene.getRoot();
        HBox breakBox = new HBox(breakTimeText); // Create an HBox for the break time text
        breakBox.setAlignment(Pos.CENTER); // Align the break time text to the center
        borderPane.setBottom(breakBox); // Add the break time text to the bottom of the UI
    
        breakTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            breakTimeElapsed[0]++;
            breakTimeText.setText("Break Time: " + formatTime(breakDuration - breakTimeElapsed[0])); // Update break time text
            if (breakTimeElapsed[0] >= breakDuration) {
                breakTimeline.stop();
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Break is over! Ready to continue?", ButtonType.OK);
                    alert.setTitle("Break Over");
                    alert.setHeaderText(null);
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            workTimeline.play();
    
                            // Restore the elements at the bottom
                            borderPane.setBottom(bottomContent);
                        }
                    });
                });
            }
        }));
        breakTimeline.setCycleCount(Timeline.INDEFINITE);
        breakTimeline.play();
    }
    

    private static void stopTimer(Text timeText, CircularProgressbar timeBar, Text elapsedTimeText) {
        if (workTimeline != null) {
            workTimeline.stop();
            try {
                saveElapsedTime(elapsedTime); // Save elapsed time to database on stop
            } catch (SQLException e) {
                e.printStackTrace();
            }
            workTimeline = null; // Clear the timeline
        }
        timeText.setText("--:--:--"); // Set the timer text to "--"
        timeBar.draw(0, "--:--:--"); // Clear the progress bar
        elapsedTimeText.setText("Elapsed Time: " + formatTime(elapsedTime)); // Ensure elapsed time text remains the same
    }

    private static String formatTime(int seconds) {
        int hours = seconds / 3600;
        int remainingSeconds = seconds % 3600;
        int minutes = remainingSeconds / 60;
        int secs = remainingSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }
}