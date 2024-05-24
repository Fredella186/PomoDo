import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Todolist {

  public static String currentTaskId;
  public static List<String> tasksId;
  public static List<String> tasks;
  public static List<String> tasksLabel;
  public static List<String> tasksDesc;
  public static List<String> tasksDate;
  public static List<String> tasksClock;
  public static List<String> tasksCategory;
  public static List<String> tasksNewCategory;

<<<<<<< Updated upstream
  public static String[] getTaskId() throws SQLException {

    Connection connection = Dbconnect.getConnect();
    String sql = "SELECT id FROM tasks WHERE created_by = ?";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, App.currentUserId);

    ResultSet resultSet = statement.executeQuery();

    List<String> tasksIdList = new ArrayList<>();
    Todolist.tasksId = tasksIdList;

    while (resultSet.next()) {
      tasksIdList.add(resultSet.getString(1));
    }

    // Convert the list to a String array (if needed)
    String[] tasksIdArray = tasksIdList.toArray(new String[tasksIdList.size()]);

    resultSet.close();
    statement.close();
    connection.close();

    return tasksIdArray;
  }

  public static String[] getTask() throws SQLException {
    Connection connection = Dbconnect.getConnect();
    String sql = "SELECT title FROM tasks WHERE created_by = ?";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, App.currentUserId);

    ResultSet resultSet = statement.executeQuery();

    List<String> tasksList = new ArrayList<>();
    Todolist.tasks = tasksList;

    while (resultSet.next()) {
      tasksList.add(resultSet.getString(1));
    }

    // Convert the list to a String array (if needed)
    String[] tasksArray = tasksList.toArray(new String[tasksList.size()]);

    resultSet.close();
    statement.close();
    connection.close();

    return tasksArray;
  }

  public static String[] getTasksLabel() throws SQLException {
    Connection connection = Dbconnect.getConnect();
    String sql = "SELECT label FROM tasks WHERE created_by = ?";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, App.currentUserId);

    ResultSet resultSet = statement.executeQuery();

    List<String> tasksLabelList = new ArrayList<>();
    Todolist.tasksLabel = tasksLabelList;

    while (resultSet.next()) {
      tasksLabelList.add(resultSet.getString(1));
    }

    // Convert the list to a String array (if needed)
    String[] tasksLabelArray = tasksLabelList.toArray(new String[tasksLabelList.size()]);

    resultSet.close();
    statement.close();
    connection.close();

    return tasksLabelArray;
  }

  public static String[] getTasksDesc() throws SQLException {
    Connection connection = Dbconnect.getConnect();
    String sql = "SELECT description FROM tasks WHERE created_by = ?";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, App.currentUserId);

=======
  public static String getUsername() throws SQLException {
    Connection connection = Dbconnect.getConnect();
    String sql = "SELECT username FROM users WHERE id = ?";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, App.currentUserId);
    ResultSet resultSet = statement.executeQuery();

    String username = "";
    if (resultSet.next()) {
      username = resultSet.getString("username");
    }
    return username;
  }

  public static void screenCapture() throws AWTException, IOException, SQLException {
    Robot robot = new Robot();
    Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    BufferedImage image = robot.createScreenCapture(rectangle);

    String directoryPath = System.getProperty("user.dir") + "/src/screenshots";
    File directory = new File(directoryPath);

    if (!directory.exists()) {
      if (directory.mkdirs()) {
        System.out.println("Directory created successfully!");
      } else {
        System.out.println("Failed to create directory!");
        return;
      }
    }

    // File Name
    String username = getUsername();
    LocalDate currentDate = LocalDate.now();
    LocalTime currentTime = LocalTime.now();
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH-mm-ss");

    String formattedDate = currentDate.format(dateFormatter);
    String formattedTime = currentTime.format(timeFormatter);

    String fileName = formattedDate + "_" + formattedTime + "_" + username + ".png";

    File file = new File(directory, fileName);
    ImageIO.write(image, "png", file);

    System.out.println("file berhasil disave di " + file.getAbsolutePath());
  }

  public static String[] getTaskId() throws SQLException {

    Connection connection = Dbconnect.getConnect();
    String sql = "SELECT id FROM tasks WHERE user_id = ?";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, App.currentUserId);

    ResultSet resultSet = statement.executeQuery();

    List<String> tasksIdList = new ArrayList<>();
    Todolist.tasksId = tasksIdList;

    while (resultSet.next()) {
      tasksIdList.add(resultSet.getString(1));
    }

    // Convert the list to a String array (if needed)
    String[] tasksIdArray = tasksIdList.toArray(new String[tasksIdList.size()]);

    resultSet.close();
    statement.close();
    connection.close();

    return tasksIdArray;
  }

  public static String[] getTask() throws SQLException {
    Connection connection = Dbconnect.getConnect();
    String sql = "SELECT title FROM tasks WHERE user_id = ?";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, App.currentUserId);

    ResultSet resultSet = statement.executeQuery();

    List<String> tasksList = new ArrayList<>();
    Todolist.tasks = tasksList;

    while (resultSet.next()) {
      tasksList.add(resultSet.getString(1));
    }

    // Convert the list to a String array (if needed)
    String[] tasksArray = tasksList.toArray(new String[tasksList.size()]);

    resultSet.close();
    statement.close();
    connection.close();

    return tasksArray;
  }

  public static String[] getTasksLabel() throws SQLException {
    Connection connection = Dbconnect.getConnect();
    String sql = "SELECT label FROM tasks WHERE user_id = ?";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, App.currentUserId);

    ResultSet resultSet = statement.executeQuery();

    List<String> tasksLabelList = new ArrayList<>();
    Todolist.tasksLabel = tasksLabelList;

    while (resultSet.next()) {
      tasksLabelList.add(resultSet.getString(1));
    }

    // Convert the list to a String array (if needed)
    String[] tasksLabelArray = tasksLabelList.toArray(new String[tasksLabelList.size()]);

    resultSet.close();
    statement.close();
    connection.close();

    return tasksLabelArray;
  }

  public static String[] getTasksDesc() throws SQLException {
    Connection connection = Dbconnect.getConnect();
    String sql = "SELECT description FROM tasks WHERE user_id = ?";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, App.currentUserId);

>>>>>>> Stashed changes
    ResultSet resultSet = statement.executeQuery();

    List<String> tasksDescList = new ArrayList<>();
    Todolist.tasksDesc = tasksDescList;

    while (resultSet.next()) {
      tasksDescList.add(resultSet.getString(1));
    }

    // Convert the list to a String array (if needed)
    String[] tasksDescArray = tasksDescList.toArray(new String[tasksDescList.size()]);

    resultSet.close();
    statement.close();
    connection.close();

    return tasksDescArray;
  }

  public static String[] getTasksCategory() throws SQLException {
    Connection connection = Dbconnect.getConnect();
<<<<<<< Updated upstream
    String sql = "SELECT priority_id FROM tasks WHERE created_by = ?";
    // String sql = "SELECT priority.name FROM task INNER JOIN priority ON
    // task.priority_id = priority.id WHERE task.created_by = ?";
=======
    String sql = "SELECT priority_id FROM tasks WHERE user_id = ?";
    // String sql = "SELECT priority.name FROM task INNER JOIN priority ON
    // task.priority_id = priority.id WHERE task.user_id = ?";
>>>>>>> Stashed changes
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, App.currentUserId);

    ResultSet resultSet = statement.executeQuery();

    List<String> tasksCategoryList = new ArrayList<>();
    Todolist.tasksCategory = tasksCategoryList;

    while (resultSet.next()) {
      tasksCategoryList.add(resultSet.getString(1));
    }

    // Convert the list to a String array (if needed)
    String[] tasksCategoryArray = tasksCategoryList.toArray(new String[tasksCategoryList.size()]);

    resultSet.close();
    statement.close();
    connection.close();

    return tasksCategoryArray;
  }

  public static String[] getNewTasksCategory() throws SQLException {
    Connection connection = Dbconnect.getConnect();
<<<<<<< Updated upstream
    String sql = "SELECT new_priority_id FROM tasks WHERE created_by = ?";
    // String sql = "SELECT priority.name FROM task INNER JOIN priority ON
    // task.priority_id = priority.id WHERE task.created_by = ?";
=======
    String sql = "SELECT new_priority_id FROM tasks WHERE user_id = ?";
    // String sql = "SELECT priority.name FROM task INNER JOIN priority ON
    // task.priority_id = priority.id WHERE task.user_id = ?";
>>>>>>> Stashed changes
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, App.currentUserId);

    ResultSet resultSet = statement.executeQuery();

    List<String> tasksNewCategoryList = new ArrayList<>();
    Todolist.tasksNewCategory = tasksNewCategoryList;

    while (resultSet.next()) {
      tasksNewCategoryList.add(resultSet.getString(1));
    }

    // Convert the list to a String array (if needed)
    String[] tasksNewCategoryArray = tasksNewCategoryList.toArray(new String[tasksNewCategoryList.size()]);

    resultSet.close();
    statement.close();
    connection.close();

    return tasksNewCategoryArray;
  }

  public static String[] getTasksDate() throws SQLException {
    Connection connection = Dbconnect.getConnect();
<<<<<<< Updated upstream
    String sql = "SELECT deadline FROM tasks WHERE created_by = ?";
=======
    String sql = "SELECT deadline FROM tasks WHERE user_id = ?";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, App.currentUserId);

    ResultSet resultSet = statement.executeQuery();

    List<String> tasksDateList = new ArrayList<>();
    Todolist.tasksDate = tasksDateList;

    while (resultSet.next()) {
      tasksDateList.add(resultSet.getString(1));
    }

    // Convert the list to a String array (if needed)
    String[] tasksDateArray = tasksDateList.toArray(new String[tasksDateList.size()]);

    resultSet.close();
    statement.close();
    connection.close();

    return tasksDateArray;
  }

  public static String[] getTasksClock() throws SQLException {
    Connection connection = Dbconnect.getConnect();
    String sql = "SELECT time_work FROM tasks WHERE user_id = ?";
>>>>>>> Stashed changes
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, App.currentUserId);

    ResultSet resultSet = statement.executeQuery();

    List<String> tasksDateList = new ArrayList<>();
    Todolist.tasksDate = tasksDateList;

    while (resultSet.next()) {
      tasksDateList.add(resultSet.getString(1));
    }

    // Convert the list to a String array (if needed)
    String[] tasksDateArray = tasksDateList.toArray(new String[tasksDateList.size()]);

    resultSet.close();
    statement.close();
    connection.close();

    return tasksDateArray;
  }

  public static void add(Stage addStage) throws Exception {
    BorderPane borderPane = new BorderPane();
    borderPane.getStylesheets().add("/assets/addTaskStyle.css");
    borderPane.getStyleClass().add("bgColor");

    VBox box = new VBox();
    box.setAlignment(Pos.CENTER);

    GridPane addPane = new GridPane();
    addPane.setAlignment(Pos.CENTER);
    // addPane.setHgap(5);
    addPane.setVgap(5);

    Text newText = new Text("Add New Task");
    addPane.add(newText, 0, 1);
    newText.getStyleClass().add("addText");

    Label taskLabel = new Label("Task Name");
    addPane.add(taskLabel, 0, 2);
    taskLabel.getStyleClass().add("labelColor");

    TextField taskInput = new TextField();
    addPane.add(taskInput, 0, 3);
    taskInput.setPrefWidth(250);
    taskInput.setPrefHeight(35);

    Label descLabel = new Label("Description");
    addPane.add(descLabel, 0, 4);
    descLabel.getStyleClass().add("labelColor");

    TextArea descInput = new TextArea();
    addPane.add(descInput, 0, 5);
    descInput.setPrefWidth(250);
    descInput.setPrefHeight(50);

    Label dateLabel = new Label("Date to end the task");
    addPane.add(dateLabel, 0, 6);
    dateLabel.getStyleClass().add("labelColor");

    DatePicker dateInput = new DatePicker();
    addPane.add(dateInput, 0, 7);
    dateInput.setPrefWidth(120);
    dateInput.setPrefHeight(35);

    Label timeLabel = new Label("Time");
    addPane.add(timeLabel, 0, 8);
    timeLabel.getStyleClass().add("labelColor");

    ComboBox<String> hourInput = new ComboBox<>();
    for (int hour = 0; hour < 24; hour++) {
      hourInput.getItems().add(String.format("%02d", hour));
    }
    hourInput.setValue("00");
    // addPane.add(hourInput,0,9);

    ComboBox<String> minuteInput = new ComboBox<>();
    for (int minute = 0; minute < 60; minute++) {
      minuteInput.getItems().add(String.format("%02d", minute));
    }
    minuteInput.setValue("00");
    // addPane.add(minuteInput,1,9);
    HBox timePicker = new HBox(hourInput, minuteInput);
    addPane.add(timePicker, 0, 9);

    Label priorityLabel = new Label("Priority");
    addPane.add(priorityLabel, 0, 10);
    priorityLabel.getStyleClass().add("labelColor");

    ChoiceBox<String> priorityInput = new ChoiceBox<>();
    priorityInput.getItems().addAll("Low", "Medium", "High");
    priorityInput.setValue("Low");
    addPane.add(priorityInput, 0, 11);
    priorityInput.setPrefWidth(120);
    priorityInput.setPrefHeight(35);

    Label tagLabel = new Label("Tag");
    addPane.add(tagLabel, 0, 12);
    tagLabel.getStyleClass().add("labelColor");

    TextField tagInput = new TextField();
    addPane.add(tagInput, 0, 13);
    tagInput.setPrefWidth(120);
    tagInput.setPrefHeight(35);

<<<<<<< Updated upstream
    Label colabLabel = new Label("Collaborators");
    addPane.add(colabLabel, 0, 14);
    colabLabel.getStyleClass().add("labelColor");

    TextField colabInput = new TextField();
    addPane.add(colabInput, 0, 15);
    colabInput.setPrefWidth(120);
    colabInput.setPrefHeight(35);

=======
>>>>>>> Stashed changes
    Button addBtn = new Button("Add Task");
    // addPane.add(addBtn,0,13);
    addBtn.setPrefWidth(80);
    addBtn.setPrefHeight(35);
    addBtn.getStyleClass().add("btn");

    HBox btnBox = new HBox(addBtn);
    btnBox.setSpacing(5);
<<<<<<< Updated upstream
    addPane.add(btnBox, 0, 16);
=======
    addPane.add(btnBox, 0, 14);
>>>>>>> Stashed changes

    addBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          // Todolist.show(addStage);
          String taskName = taskInput.getText();
          String description = descInput.getText();
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
          LocalDate taskDate = dateInput.getValue();
          String formatDate = taskDate.format(formatter);
          String priority = priorityInput.getValue();
          String hour = hourInput.getValue();
          String minute = minuteInput.getValue();
          int hourInt = Integer.parseInt(hour);
          int minuteInt = Integer.parseInt(minute);
          LocalTime time = LocalTime.of(hourInt, minuteInt);
          String tag = tagInput.getText();
<<<<<<< Updated upstream
=======
          LocalDate now = LocalDate.now();
          // System.out.print(now);
          String nowDate = now.format(formatter);
>>>>>>> Stashed changes

          int priorityId;

          switch (priority) {
            case "Low":
              priorityId = 3;
              break;
            case "Medium":
              priorityId = 2;
              break;
            case "High":
              priorityId = 1;
              break;
            default:
              System.err.println("Invalid priority: " + priorityInput);
              priorityId = 3;
          }

          Connection connection = Dbconnect.getConnect();
<<<<<<< Updated upstream
          String insertTaskQuery = "INSERT INTO tasks (created_by,priority_id,title,description,deadline,label,new_priority_id) VALUES (?,?,?,?,?,?,?)";
=======
          String insertTaskQuery = "INSERT INTO tasks (user_id,priority_id,title,description,deadline,time_work,label,new_priority_id,date_task_created) VALUES (?,?,?,?,?,?,?,?,?)";
          // String insertTagQuery = "INSERT INTO tag (task_id, name) VALUES
          // (LAST_INSERT_ID(), ?)";
>>>>>>> Stashed changes
          PreparedStatement statement = connection.prepareStatement(insertTaskQuery);

          statement.setInt(1, App.currentUserId);
          statement.setInt(2, priorityId);
          statement.setString(3, taskName);
          statement.setString(4, description);
          statement.setString(5, formatDate);
<<<<<<< Updated upstream
          statement.setString(6, tag);
          statement.setInt(7, priorityId);

          String taskId = "SELECT LAST_INSERT_ID()";
          PreparedStatement statement1 = connection.prepareStatement(taskId);
          ResultSet rs = statement1.executeQuery();
          if (rs.next()) {
            taskId = rs.getString("LAST_INSERT_ID()");
          }

          // Execute the statement
          statement.executeUpdate();
          String insertCollabQuery = "INSERT INTO collaborators (task_id,user_id,time_work) VALUES (?,?,?)";
          PreparedStatement statement2 = connection.prepareStatement(insertCollabQuery);
          statement2.setString(1, taskId);
          statement2.setInt(2, App.currentUserId);
          statement2.setString(3, time.toString());
=======
          statement.setString(6, time.toString());
          statement.setString(7, tag);
          statement.setInt(8, priorityId);
          statement.setString(9, nowDate);

          // Execute the statement
          statement.executeUpdate();
>>>>>>> Stashed changes
          // Stage showStage = new Stage();
          // Todolist.show(showStage);
          // showStage.close();
          Platform.runLater(new Runnable() {
            @Override
            public void run() {
              try {
                Todolist.show(new Stage());
                addStage.close();
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
          });
        } catch (SQLException e) {
          e.printStackTrace();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

    box.getChildren().addAll(addPane);
    BorderPane.setAlignment(box, Pos.CENTER);
    borderPane.setCenter(box);

    Scene scene = new Scene(borderPane, 500, 768);
    addStage.setTitle("Add Task");
    addStage.setScene(scene);
    addStage.show();
  }

  // untuk menampilkan tugas
  public static void show(Stage showStage) throws Exception {
    BorderPane borderPane = new BorderPane();
    borderPane.setPadding(new Insets(20, 20, 20, 20));
    borderPane.getStylesheets().add("/assets/showTaskStyle.css");

    borderPane.getStyleClass().add("bgColor");

    VBox box = new VBox();
    box.setAlignment(Pos.CENTER);

    // Header
    Text holaText = new Text("Hola, ");
    holaText.getStyleClass().add("nameText");
    Text nameText = new Text();
    // nameText.setText("Hola,Josh");
    nameText.getStyleClass().add("nameText");
    try {

      Connection connection = Dbconnect.getConnect();
      String sql = "SELECT username FROM users WHERE id =?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(1, App.currentUserId);
      ResultSet rs = statement.executeQuery();
      rs.next();
      nameText.setText(rs.getString("username"));
    } catch (Exception e) {
      e.printStackTrace();
    }

    HBox nameBox = new HBox(holaText, nameText);
    Text dateText = new Text();
    dateText.setText("15 Mar 2024");
    dateText.getStyleClass().add("dateText");
    Text timeText = new Text();
    timeText.setText("08:00");
    timeText.getStyleClass().add("dateText");

    HBox dateBox = new HBox(dateText, timeText);
    dateBox.setSpacing(3);
    VBox profileBox = new VBox(nameBox, dateBox);

    BorderPane.setAlignment(profileBox, Pos.TOP_LEFT);

    Image missionImg = new Image(Todolist.class.getResourceAsStream("/assets/Image/Vector.png"));
    Button missionBtn = new Button();
    missionBtn.setGraphic(new ImageView(missionImg));
    missionBtn.setPrefWidth(51);
    missionBtn.setPrefHeight(51);
    missionBtn.getStyleClass().add("btn");

    missionBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          mission msn = new mission();
          msn.showMission(new Stage());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

    Image profileImg = new Image(Todolist.class.getResourceAsStream("/assets/Image/User_alt.png"));
    Button profileBtn = new Button();
    profileBtn.setGraphic(new ImageView(profileImg));
    profileBtn.setPrefWidth(51);
    profileBtn.setPrefHeight(51);
    profileBtn.getStyleClass().add("btn");

    profileBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          User user = new User();
          user.profile(new Stage());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

    Image clockImg = new Image(Todolist.class.getResourceAsStream("/assets/Image/Clock.png"));
    Button clockBtn = new Button();
    clockBtn.setGraphic(new ImageView(clockImg));
    clockBtn.setPrefWidth(51);
    clockBtn.setPrefHeight(51);
    clockBtn.getStyleClass().add("btn");

    clockBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          Time time = new Time();
          time.showTime(new Stage()); // Call the Todolist's add method to display the stage
          // primaryStage.close(); // Close the login stage after successful login
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

    Image newTaskImg = new Image(Todolist.class.getResourceAsStream("/assets/Image/Add_ring.png"));
    Button newTaskBtn = new Button("New Task");
    newTaskBtn.setGraphic(new ImageView(newTaskImg));
    newTaskBtn.setPrefWidth(149);
    newTaskBtn.setPrefHeight(51);
    newTaskBtn.getStyleClass().add("taskBtn");

    HBox btnBox = new HBox(missionBtn, profileBtn, clockBtn, newTaskBtn);
    btnBox.setSpacing(10);
    BorderPane.setAlignment(btnBox, Pos.TOP_RIGHT);

    newTaskBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          add(new Stage());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

    Text progressText = new Text("Task Progress");
    progressText.getStyleClass().add("pTask");
    ProgressBar taskProgressBar = new ProgressBar(0.4);
    taskProgressBar.setPrefWidth(1280);
    taskProgressBar.setPrefHeight(15);
    VBox progressBox = new VBox(progressText, taskProgressBar);
    progressBox.setSpacing(15);
    HBox topBox = new HBox(profileBox, btnBox);
    topBox.setSpacing(750);
    VBox taskTopBox = new VBox(topBox, progressBox);
    taskTopBox.setSpacing(20);
    borderPane.setTop(taskTopBox);

    // untuk active dan button filter
    Text activeText = new Text("Active");
    taskTopBox.setPadding(new Insets(0, 0, 20, 0));
    activeText.getStyleClass().add("pTask");
    Button allBtn = new Button("All");
    allBtn.setPrefWidth(51);
    allBtn.setPrefHeight(42);
    allBtn.getStyleClass().add("filterBtn");
    Button lowBtn = new Button("Low");
    lowBtn.setPrefWidth(63);
    lowBtn.setPrefHeight(42);
    lowBtn.getStyleClass().add("filterBtn");

    Button medBtn = new Button("Medium");
    medBtn.setPrefWidth(92);
    medBtn.setPrefHeight(42);
    medBtn.getStyleClass().add("filterBtn");
    Button highBtn = new Button("High");
    highBtn.setPrefWidth(68);
    highBtn.setPrefHeight(42);
    highBtn.getStyleClass().add("filterBtn");

    HBox filterBox = new HBox(allBtn, lowBtn, medBtn, highBtn);
    filterBox.setSpacing(10);
    VBox activeBox = new VBox(activeText, filterBox);
    activeBox.setSpacing(12);

    VBox taskBox = new VBox();
    taskBox.getChildren().addAll(activeBox);
    BorderPane.setAlignment(activeBox, Pos.CENTER);
    // borderPane.setCenter(taskBox);
    taskBox.setSpacing(35);

    Button targetButton = allBtn;

    Platform.runLater(() -> {
      targetButton.fire(); // Simulate a button click after UI setup
    });

    // untuk menampilkan tugas dengan semua priority
    String[] tasksId = getTaskId();
    String[] tasks = getTask();
    String[] tasksLabel = getTasksLabel();
    String[] tasksDesc = getTasksDesc();
    String[] tasksDate = getTasksDate();
<<<<<<< Updated upstream
    // String[] tasksClock = getTasksClock();
=======
    String[] tasksClock = getTasksClock();
>>>>>>> Stashed changes
    String[] tasksCategory = getTasksCategory();
    String[] tasksNewCategory = getNewTasksCategory();

    allBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          VBox taskMainBox = new VBox(10);
          Text priorityText = new Text("Priority");
          priorityText.getStyleClass().add("pTask");
          VBox taskBoxMain = new VBox(10);
          taskMainBox.getChildren().clear();
          // **Execute the for loop only when lowBtn is clicked**
          for (int i = 0; i < tasks.length; i++) {
            int index = i;
            if (tasksNewCategory[i].equals("1") || tasksNewCategory[i].equals("2") || tasksNewCategory[i].equals("3")) {
              CheckBox cb = new CheckBox();
              cb.getStyleClass().add("check-box");
              cb.setAlignment(Pos.CENTER);
              Text task1Text = new Text(tasks[i]);
              task1Text.getStyleClass().add("pTask");
              Label taskLabel = new Label(tasksLabel[i]);
              taskLabel.getStyleClass().add("taskLabel");
              Text desText = new Text(tasksDesc[i]);
              desText.getStyleClass().add("descText");
              Image clockTime = new Image(Todolist.class.getResourceAsStream("/assets/Image/Calendar.png"));
              ImageView timeImg = new ImageView(clockTime);
              timeImg.setFitHeight(24);
              timeImg.setFitWidth(24);
              Text time1Text = new Text(tasksDate[i]);
              time1Text.getStyleClass().add("timeText");
              HBox timeImgTextBox = new HBox(timeImg, time1Text);
              timeImgTextBox.setSpacing(3);
              Image calTime = new Image(Todolist.class.getResourceAsStream("/assets/Image/AlarmClock.png"));
              ImageView calImg = new ImageView(calTime);
              calImg.setFitHeight(24);
              calImg.setFitWidth(24);
<<<<<<< Updated upstream
              // Text clock1Text = new Text(tasksClock[i]);
              // clock1Text.getStyleClass().add("timeText");
              // HBox clockImgTextBox = new HBox(calImg,clock1Text);
              // clockImgTextBox.setSpacing(3);
=======
              Text clock1Text = new Text(tasksClock[i]);
              clock1Text.getStyleClass().add("timeText");
              HBox clockImgTextBox = new HBox(calImg, clock1Text);
              clockImgTextBox.setSpacing(3);
>>>>>>> Stashed changes

              HBox taskPBox = new HBox(10);
              HBox taskDescBox = new HBox(10);
              VBox priorityTaskBox = new VBox();
              HBox priorityBox = new HBox(10);
              priorityBox.setAlignment(Pos.CENTER_LEFT);

              taskPBox.getChildren().addAll(task1Text, taskLabel);
<<<<<<< Updated upstream
              taskDescBox.getChildren().addAll(desText, timeImgTextBox);
=======
              taskDescBox.getChildren().addAll(desText, timeImgTextBox, clockImgTextBox);
>>>>>>> Stashed changes
              priorityTaskBox.getChildren().addAll(taskPBox, taskDescBox);
              priorityBox.getChildren().addAll(cb, priorityTaskBox);
              taskBoxMain.getChildren().add(priorityBox);

              task1Text.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                  // Get details of the task based on tasksId[index]
                  currentTaskId = tasksId[index];
                  try {
                    detailTask(new Stage());
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                }
              });

              cb.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                  // Update UI based on current selection
                  boolean isSelected = cb.isSelected();
                  if (isSelected) {
                    tasksNewCategory[index] = "4";

                    Connection connection = Dbconnect.getConnect();
                    String sql = "UPDATE tasks SET new_priority_id = '4' WHERE id = '" + tasksId[index] + "'";
                    try {
                      PreparedStatement statement = connection.prepareStatement(sql);
                      statement.executeUpdate();
                      showStage.close();
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

                    } catch (SQLException e) {
<<<<<<< Updated upstream
=======
                      // TODO Auto-generated catch block
>>>>>>> Stashed changes
                      e.printStackTrace();
                    }
                  }
                }
              });

            }
          }
          // untuk menampilkan semua tugas dengan tingkat priority low,medium,high
          taskBox.getChildren().clear();
          taskBox.getChildren().addAll(activeBox);
          taskBox.getChildren().addAll(taskBoxMain);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

    lowBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {

          VBox taskMainBox = new VBox(10);
          Text priorityText = new Text("Priority");
          priorityText.getStyleClass().add("pTask");
          VBox taskBoxMain = new VBox(10);
          taskMainBox.getChildren().clear();
          // **Execute the for loop only when lowBtn is clicked**
          for (int i = 0; i < tasks.length; i++) {
            int index = i;
            if (tasksNewCategory[i].equals("3")) {
              CheckBox cb = new CheckBox();
              cb.getStyleClass().add("check-box");
              cb.setAlignment(Pos.CENTER);
              Text task1Text = new Text(tasks[i]);
              task1Text.getStyleClass().add("pTask");
              Label taskLabel = new Label(tasksLabel[i]);
              taskLabel.getStyleClass().add("taskLabel");
              Text desText = new Text(tasksDesc[i]);
              desText.getStyleClass().add("descText");
              Image clockTime = new Image(Todolist.class.getResourceAsStream("/assets/Image/Calendar.png"));
              ImageView timeImg = new ImageView(clockTime);
              timeImg.setFitHeight(24);
              timeImg.setFitWidth(24);
              Text time1Text = new Text(tasksDate[i]);
              time1Text.getStyleClass().add("timeText");
              HBox timeImgTextBox = new HBox(timeImg, time1Text);
              timeImgTextBox.setSpacing(3);
              Image calTime = new Image(Todolist.class.getResourceAsStream("/assets/Image/AlarmClock.png"));
              ImageView calImg = new ImageView(calTime);
              calImg.setFitHeight(24);
              calImg.setFitWidth(24);
<<<<<<< Updated upstream
              // Text clock1Text = new Text(tasksClock[i]);
              // clock1Text.getStyleClass().add("timeText");
              // HBox clockImgTextBox = new HBox(calImg,clock1Text);
              // clockImgTextBox.setSpacing(3);
=======
              Text clock1Text = new Text(tasksClock[i]);
              clock1Text.getStyleClass().add("timeText");
              HBox clockImgTextBox = new HBox(calImg, clock1Text);
              clockImgTextBox.setSpacing(3);
>>>>>>> Stashed changes

              HBox taskPBox = new HBox(10);
              HBox taskDescBox = new HBox(10);
              VBox priorityTaskBox = new VBox();
              HBox priorityBox = new HBox(10);
              priorityBox.setAlignment(Pos.CENTER_LEFT);

              taskPBox.getChildren().addAll(task1Text, taskLabel);
<<<<<<< Updated upstream
              taskDescBox.getChildren().addAll(desText, timeImgTextBox);
=======
              taskDescBox.getChildren().addAll(desText, timeImgTextBox, clockImgTextBox);
>>>>>>> Stashed changes
              priorityTaskBox.getChildren().addAll(taskPBox, taskDescBox);
              priorityBox.getChildren().addAll(cb, priorityTaskBox);
              taskBoxMain.getChildren().add(priorityBox);

              cb.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                  // Update UI based on current selection
                  boolean isSelected = cb.isSelected();
                  if (isSelected) {
                    tasksNewCategory[index] = "4";

                    Connection connection = Dbconnect.getConnect();
                    String sql = "UPDATE tasks SET new_priority_id = '4' WHERE id = '" + tasksId[index] + "'";
                    try {
                      PreparedStatement statement = connection.prepareStatement(sql);
                      statement.executeUpdate();
                      showStage.close();
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

                    } catch (SQLException e) {
                      // TODO Auto-generated catch block
                      e.printStackTrace();
                    }
                  }
                }
              });
            }
          }
          // untuk menghapus semua isi taskBox
          taskBox.getChildren().clear();
          taskBox.getChildren().addAll(activeBox);
          taskBox.getChildren().addAll(taskBoxMain);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

    // untuk menampilkan tugas dengan tingkat priority medium
    medBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          Text priorityText = new Text("Priority");
          priorityText.getStyleClass().add("pTask");
          VBox taskBoxMain = new VBox(10);

          // **Execute the for loop only when lowBtn is clicked**
          for (int i = 0; i < tasks.length; i++) {
            int index = i;
            if (tasksNewCategory[i].equals("2")) {
              CheckBox cb = new CheckBox();
              cb.getStyleClass().add("check-box");
              cb.setAlignment(Pos.CENTER);
              Text task1Text = new Text(tasks[i]);
              task1Text.getStyleClass().add("pTask");
              Label taskLabel = new Label(tasksLabel[i]);
              taskLabel.getStyleClass().add("taskLabel");
              Text desText = new Text(tasksDesc[i]);
              desText.getStyleClass().add("descText");
              Image clockTime = new Image(Todolist.class.getResourceAsStream("/assets/Image/Calendar.png"));
              ImageView timeImg = new ImageView(clockTime);
              timeImg.setFitHeight(24);
              timeImg.setFitWidth(24);
              Text time1Text = new Text(tasksDate[i]);
              time1Text.getStyleClass().add("timeText");
              HBox timeImgTextBox = new HBox(timeImg, time1Text);
              timeImgTextBox.setSpacing(3);
              Image calTime = new Image(Todolist.class.getResourceAsStream("/assets/Image/AlarmClock.png"));
              ImageView calImg = new ImageView(calTime);
              calImg.setFitHeight(24);
              calImg.setFitWidth(24);
<<<<<<< Updated upstream
              // Text clock1Text = new Text(tasksClock[i]);
              // clock1Text.getStyleClass().add("timeText");
              // HBox clockImgTextBox = new HBox(calImg,clock1Text);
              // clockImgTextBox.setSpacing(3);
=======
              Text clock1Text = new Text(tasksClock[i]);
              clock1Text.getStyleClass().add("timeText");
              HBox clockImgTextBox = new HBox(calImg, clock1Text);
              clockImgTextBox.setSpacing(3);
>>>>>>> Stashed changes

              HBox taskPBox = new HBox(10);
              HBox taskDescBox = new HBox(10);
              VBox priorityTaskBox = new VBox();
              HBox priorityBox = new HBox(10);
              priorityBox.setAlignment(Pos.CENTER_LEFT);

              taskPBox.getChildren().addAll(task1Text, taskLabel);
<<<<<<< Updated upstream
              taskDescBox.getChildren().addAll(desText, timeImgTextBox);
=======
              taskDescBox.getChildren().addAll(desText, timeImgTextBox, clockImgTextBox);
>>>>>>> Stashed changes
              priorityTaskBox.getChildren().addAll(taskPBox, taskDescBox);
              priorityBox.getChildren().addAll(cb, priorityTaskBox);
              taskBoxMain.getChildren().add(priorityBox);

              cb.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                  // Update UI based on current selection
                  boolean isSelected = cb.isSelected();
                  if (isSelected) {
                    tasksNewCategory[index] = "4";
                    Connection connection = Dbconnect.getConnect();
                    String sql = "UPDATE tasks SET new_priority_id = '4' WHERE id = '" + tasksId[index] + "'";
                    try {
                      PreparedStatement statement = connection.prepareStatement(sql);
                      statement.executeUpdate();
                      showStage.close();
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
                    } catch (SQLException e) {
                      e.printStackTrace();
                    }
                  }
                }
              });
            }
          }
          // untuk menghapus semua isi taskBox
          taskBox.getChildren().clear();
          taskBox.getChildren().addAll(activeBox);
          taskBox.getChildren().addAll(taskBoxMain);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

    // untuk menampilkan tugas dengan tingkat priority high
    highBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {

          VBox taskMainBox = new VBox(10);
          Text priorityText = new Text("Priority");
          priorityText.getStyleClass().add("pTask");
          VBox taskBoxMain = new VBox(10);

          taskMainBox.getChildren().clear();

          // **Execute the for loop only when lowBtn is clicked**
          for (int i = 0; i < tasks.length; i++) {
            int index = i;
            if (tasksNewCategory[i].equals("1")) {
              CheckBox cb = new CheckBox();
              cb.getStyleClass().add("check-box");
              cb.setAlignment(Pos.CENTER);
              Text task1Text = new Text(tasks[i]);
              task1Text.getStyleClass().add("pTask");
              Label taskLabel = new Label(tasksLabel[i]);
              taskLabel.getStyleClass().add("taskLabel");
              Text desText = new Text(tasksDesc[i]);
              desText.getStyleClass().add("descText");
              Image clockTime = new Image(Todolist.class.getResourceAsStream("/assets/Image/Calendar.png"));
              ImageView timeImg = new ImageView(clockTime);
              timeImg.setFitHeight(24);
              timeImg.setFitWidth(24);
              Text time1Text = new Text(tasksDate[i]);
              time1Text.getStyleClass().add("timeText");
              HBox timeImgTextBox = new HBox(timeImg, time1Text);
              timeImgTextBox.setSpacing(3);
              Image calTime = new Image(Todolist.class.getResourceAsStream("/assets/Image/AlarmClock.png"));
              ImageView calImg = new ImageView(calTime);
              calImg.setFitHeight(24);
              calImg.setFitWidth(24);
<<<<<<< Updated upstream
              // Text clock1Text = new Text(tasksClock[i]);
              // clock1Text.getStyleClass().add("timeText");
              // HBox clockImgTextBox = new HBox(calImg,clock1Text);
              // clockImgTextBox.setSpacing(3);
=======
              Text clock1Text = new Text(tasksClock[i]);
              clock1Text.getStyleClass().add("timeText");
              HBox clockImgTextBox = new HBox(calImg, clock1Text);
              clockImgTextBox.setSpacing(3);
>>>>>>> Stashed changes

              HBox taskPBox = new HBox(10);
              HBox taskDescBox = new HBox(10);
              VBox priorityTaskBox = new VBox();
              HBox priorityBox = new HBox(10);
              priorityBox.setAlignment(Pos.CENTER_LEFT);

              taskPBox.getChildren().addAll(task1Text, taskLabel);
<<<<<<< Updated upstream
              taskDescBox.getChildren().addAll(desText, timeImgTextBox);
=======
              taskDescBox.getChildren().addAll(desText, timeImgTextBox, clockImgTextBox);
>>>>>>> Stashed changes
              priorityTaskBox.getChildren().addAll(taskPBox, taskDescBox);
              priorityBox.getChildren().addAll(cb, priorityTaskBox);
              taskBoxMain.getChildren().add(priorityBox);

              cb.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                  // Update UI based on current selection
                  boolean isSelected = cb.isSelected();
                  if (isSelected) {
                    tasksNewCategory[index] = "4";

                    Connection connection = Dbconnect.getConnect();
                    String sql = "UPDATE tasks SET new_priority_id = '4' WHERE id = '" + tasksId[index] + "'";
                    try {
                      PreparedStatement statement = connection.prepareStatement(sql);
                      statement.executeUpdate();
                      showStage.close();
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

                    } catch (SQLException e) {
                      // TODO Auto-generated catch block
                      e.printStackTrace();
                    }
                  }
                }
              });
            }
          }
          // untuk menghapus semua isi taskBox
          taskBox.getChildren().clear();
          taskBox.getChildren().addAll(activeBox);
          taskBox.getChildren().addAll(taskBoxMain);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    Text doneText = new Text("Done");
    doneText.getStyleClass().add("pTask");

    VBox taskMainBox = new VBox(10);
    VBox taskBoxMain = new VBox(10);

    for (int i = 0; i < tasks.length; i++) {
      int index = i;
      if (tasksNewCategory[i].equals("4")) {
        CheckBox cb = new CheckBox();
        cb.setSelected(true);
        cb.getStyleClass().add("check-box");
        cb.setAlignment(Pos.CENTER);
        Text task1Text = new Text(tasks[i]);
        task1Text.getStyleClass().add("taskDoneText");
        Text desText = new Text(tasksDesc[i]);
        desText.getStyleClass().add("descText");
        Image clockTime = new Image(Todolist.class.getResourceAsStream("/assets/Image/Alarmclockgrey.png"));
        ImageView timeImg = new ImageView(clockTime);
        timeImg.setFitHeight(24);
        timeImg.setFitWidth(24);
        Text time1Text = new Text(tasksDate[i]);
        time1Text.getStyleClass().add("timeDoneText");
        HBox timeImgTextBox = new HBox(timeImg, time1Text);
        timeImgTextBox.setSpacing(3);
        Image calTime = new Image(Todolist.class.getResourceAsStream("/assets/Image/Alarmclockgrey.png"));
        ImageView calImg = new ImageView(calTime);
        calImg.setFitHeight(24);
        calImg.setFitWidth(24);
<<<<<<< Updated upstream
=======
        Text clock1Text = new Text(tasksClock[i]);
        clock1Text.getStyleClass().add("timeDoneText");
        HBox clockImgTextBox = new HBox(calImg, clock1Text);
        clockImgTextBox.setSpacing(3);
>>>>>>> Stashed changes

        HBox taskPBox = new HBox(10);
        HBox taskDescBox = new HBox(10);
        VBox priorityTaskBox = new VBox();
        HBox priorityBox = new HBox(10);
        priorityBox.setAlignment(Pos.CENTER_LEFT);

        taskPBox.getChildren().addAll(task1Text);
<<<<<<< Updated upstream
        taskDescBox.getChildren().addAll(desText, timeImgTextBox);
=======
        taskDescBox.getChildren().addAll(desText, timeImgTextBox, clockImgTextBox);
>>>>>>> Stashed changes
        priorityTaskBox.getChildren().addAll(taskPBox, taskDescBox);
        priorityBox.getChildren().addAll(cb, priorityTaskBox);
        taskBoxMain.getChildren().add(priorityBox);

        cb.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            // Update UI based on current selection
            boolean isSelected = cb.isSelected();
            if (!isSelected) {
              // tasksNewCategory[index] = "4";

              Connection connection = Dbconnect.getConnect();
              String sql = "UPDATE tasks SET new_priority_id = '" + tasksCategory[index] + "' WHERE id = '"
                  + tasksId[index] + "'";
              try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.executeUpdate();
                showStage.close();
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

              } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
            }
          }
        });

      }
    }

    taskMainBox.getChildren().addAll(taskBoxMain);

    VBox centerTaskBox = new VBox(taskBox, doneText, taskMainBox);
    borderPane.setCenter(centerTaskBox);

    Scene scene = new Scene(borderPane, 1280, 768);
    showStage.setTitle("Task");
    showStage.setScene(scene);
    showStage.show();
  }

  public static void detailTask(Stage detailTaskStage) throws Exception {
    BorderPane borderPane = new BorderPane();
    borderPane.getStylesheets().add("/assets/addTaskStyle.css");
    // borderPane.setPadding(new Insets(10, 10, 10, 10));
    // // borderPane.setTop(createMenuBar(detailTaskStage));
    // // borderPane.setCenter(createDetailTaskPane());
    // Scene scene = new Scene(borderPane, 600,1024);

    Connection connection = Dbconnect.getConnect();
    String sql = "SELECT *FROM tasks WHERE created_by = ? AND id =?";
    try {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(1, App.currentUserId);
      statement.setString(2, currentTaskId);
      ResultSet rs = statement.executeQuery();
      List<String> task = new ArrayList<>();

      while (rs.next()) {
        task.add(rs.getString("title"));
        task.add(rs.getString("description"));
        task.add(rs.getString("deadline"));
        task.add(rs.getString("time_work"));
        task.add(rs.getString("new_priority_id"));
        task.add(rs.getString("label"));

      }

      borderPane.getStyleClass().add("bgColor");

      VBox box = new VBox();
      box.setAlignment(Pos.CENTER);

      GridPane addPane = new GridPane();
      addPane.setAlignment(Pos.CENTER);
      // addPane.setHgap(5);
      addPane.setVgap(5);

      Text newText = new Text("Task Detail");
      addPane.add(newText, 0, 1);
      newText.getStyleClass().add("addText");

      Label taskLabel = new Label("Task Name");
      addPane.add(taskLabel, 0, 2);
      taskLabel.getStyleClass().add("labelColor");

      TextField taskInput = new TextField();
      taskInput.setText(task.get(0));
      addPane.add(taskInput, 0, 3);
      taskInput.setPrefWidth(250);
      taskInput.setPrefHeight(35);

      Label descLabel = new Label("Description");
      addPane.add(descLabel, 0, 4);
      descLabel.getStyleClass().add("labelColor");

      TextArea descInput = new TextArea();
      descInput.setText(task.get(1));
      addPane.add(descInput, 0, 5);
      descInput.setPrefWidth(250);
      descInput.setPrefHeight(50);

      Label dateLabel = new Label("Date to end the task");
      addPane.add(dateLabel, 0, 6);
      dateLabel.getStyleClass().add("labelColor");

      DatePicker dateInput = new DatePicker();
      dateInput.setValue(LocalDate.parse(task.get(2)));
      addPane.add(dateInput, 0, 7);
      dateInput.setPrefWidth(120);
      dateInput.setPrefHeight(35);

      Label timeLabel = new Label("Time");
      addPane.add(timeLabel, 0, 8);
      timeLabel.getStyleClass().add("labelColor");

      ComboBox<String> hourInput = new ComboBox<>();
      for (int hour = 0; hour < 24; hour++) {
        hourInput.getItems().add(String.format("%02d", hour));
      }
      ;
      // hourInput.setValue("00");
      // addPane.add(hourInput,0,9);

      ComboBox<String> minuteInput = new ComboBox<>();
      for (int minute = 0; minute < 60; minute++) {
        minuteInput.getItems().add(String.format("%02d", minute));
      }
      // minuteInput.setValue("00");

      String taskTimeWork = task.get(3);
      String[] timeParts = taskTimeWork.split(":");

      // Check if all three parts (hours, minutes, seconds) are present
      if (timeParts.length == 3) {
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        // Set the values for hourInput and minuteInput
        hourInput.setValue(String.format("%02d", hour));
        minuteInput.setValue(String.format("%02d", minute));
      }
      // addPane.add(minuteInput,1,9);
      HBox timePicker = new HBox(hourInput, minuteInput);
      addPane.add(timePicker, 0, 9);

      Label priorityLabel = new Label("Priority");
      addPane.add(priorityLabel, 0, 10);
      priorityLabel.getStyleClass().add("labelColor");

      ChoiceBox<String> priorityInput = new ChoiceBox<>();
      priorityInput.getItems().addAll("Low", "Medium", "High");
      if (task.get(4).equals("1")) {
        priorityInput.setValue("High");
      } else if (task.get(4).equals("2")) {
        priorityInput.setValue("Medium");
      } else {
        priorityInput.setValue("Low");
      }
      addPane.add(priorityInput, 0, 11);
      priorityInput.setPrefWidth(120);
      priorityInput.setPrefHeight(35);

      Label tagLabel = new Label("Tag");
      addPane.add(tagLabel, 0, 12);
      tagLabel.getStyleClass().add("labelColor");

      TextField tagInput = new TextField();
      tagInput.setText(task.get(5));
      addPane.add(tagInput, 0, 13);
      tagInput.setPrefWidth(120);
      tagInput.setPrefHeight(35);

      Button startBtn = new Button("Start Task");
      startBtn.setPrefWidth(100);
      startBtn.setPrefHeight(51);
      startBtn.getStyleClass().add("btn");

      startBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          try {
            Time time = new Time();
            time.showTime(new Stage());
<<<<<<< Updated upstream
=======
            screenCapture();
>>>>>>> Stashed changes
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      });

      Image editImg = new Image(Todolist.class.getResourceAsStream("/assets/Image/Folder_open.png"));
      Button addBtn = new Button();
      addBtn.setGraphic(new ImageView(editImg));
      // addPane.add(addBtn,0,13);
      addBtn.setPrefWidth(51);
      addBtn.setPrefHeight(51);
      addBtn.getStyleClass().add("editBtn");

      addBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          try {
            // Todolist.show(addStage);
            String taskName = taskInput.getText();
            String description = descInput.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
            LocalDate taskDate = dateInput.getValue();
            String formatDate = taskDate.format(formatter);
            String priority = priorityInput.getValue();
            String hour = hourInput.getValue();
            String minute = minuteInput.getValue();
            int hourInt = Integer.parseInt(hour);
            int minuteInt = Integer.parseInt(minute);
            LocalTime time = LocalTime.of(hourInt, minuteInt);
            String tag = tagInput.getText();

            int priorityId;

            switch (priority) {
              case "Low":
                priorityId = 3;
                break;
              case "Medium":
                priorityId = 2;
                break;
              case "High":
                priorityId = 1;
                break;
              default:
                System.err.println("Invalid priority: " + priorityInput);
                priorityId = 3;
            }

            Connection connection = Dbconnect.getConnect();
            String updateTaskQuery = "UPDATE tasks SET title = ?, description = ?, deadline = ?, time_work = ?, label = ?, new_priority_id = ? WHERE id = ? AND created_by = ?";
            PreparedStatement statement = connection.prepareStatement(updateTaskQuery);

            statement.setString(1, taskName);
            statement.setString(2, description);
            statement.setString(3, formatDate);
            statement.setString(4, time.toString());
            statement.setString(5, tag);
            statement.setInt(6, priorityId);
            statement.setString(7, currentTaskId);
            statement.setInt(8, App.currentUserId);

            // Execute the statement
            statement.executeUpdate();

            Stage showStage = new Stage();
            Todolist.show(showStage);
            detailTaskStage.close();
            showStage.close();
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
          } catch (SQLException e) {
            e.printStackTrace();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      });

      Image deleteImg = new Image(Todolist.class.getResourceAsStream("/assets/Image/Trash.png"));
      Button deleteBtn = new Button();
      deleteBtn.setGraphic(new ImageView(deleteImg));
      // addPane.add(addBtn,0,13);
      deleteBtn.setPrefWidth(51);
      deleteBtn.setPrefHeight(51);
      deleteBtn.getStyleClass().add("editBtn");

      deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          Connection connection = Dbconnect.getConnect();
<<<<<<< Updated upstream
          String sql = "DELETE FROM tasks WHERE id =? AND created_by =?";
=======
          String sql = "DELETE FROM tasks WHERE id =? AND user_id =?";
>>>>>>> Stashed changes
          try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, currentTaskId);
            statement.setInt(2, App.currentUserId);
            statement.executeUpdate();
            Stage showStage = new Stage();
            Todolist.show(showStage);
            showStage.close();
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
        }
      });

      HBox btnBox = new HBox(addBtn, deleteBtn);
      btnBox.setSpacing(5);
      HBox editTaskbox = new HBox(startBtn, btnBox);
      editTaskbox.setSpacing(40);
      addPane.add(editTaskbox, 0, 14);

      box.getChildren().addAll(addPane);
      BorderPane.setAlignment(box, Pos.CENTER);
      borderPane.setCenter(box);

      Scene scene = new Scene(borderPane, 500, 768);
      detailTaskStage.setTitle("Detail Task");
      detailTaskStage.setScene(scene);
      detailTaskStage.show();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
