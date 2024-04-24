import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Todolist {
    public static TableView table = new TableView();
    private static final int ITEMS_PER_PAGE = 2;
public static void add(Stage addStage) throws Exception{
    BorderPane borderPane = new BorderPane();
    // String css = getClass().getResource("/assets/loginStyle.css").toExternalForm();
    borderPane.getStylesheets().add("/assets/addTaskStyle.css");

    borderPane.getStyleClass().add("bgColor");

    VBox box = new VBox();
    box.setAlignment(Pos.CENTER);
    
    GridPane addPane = new GridPane();
    addPane.setAlignment(Pos. CENTER);
    // addPane.setHgap(5);
    addPane.setVgap(5);
    
    Text newText = new Text("Add New Task");
    addPane.add(newText,0,1);
    newText.getStyleClass().add("addText");

    Label taskLabel = new Label("Task Name");
    addPane.add(taskLabel,0,2);
    taskLabel.getStyleClass().add("labelColor");

    TextField taskInput = new TextField();
    addPane.add(taskInput,0,3);
    taskInput.setPrefWidth(250);
    taskInput.setPrefHeight(35);

    Label descLabel = new Label("Description");
    addPane.add(descLabel,0,4);
    descLabel.getStyleClass().add("labelColor");

    TextArea descInput = new TextArea();
    addPane.add(descInput,0,5);
    descInput.setPrefWidth(250);
    descInput.setPrefHeight(50);

    Label dateLabel = new Label("Date to end the task");
    addPane.add(dateLabel,0,6);
    dateLabel.getStyleClass().add("labelColor");

    DatePicker dateInput = new DatePicker();
    addPane.add(dateInput,0,7);
    dateInput.setPrefWidth(120);
    dateInput.setPrefHeight(35);

    Label timeLabel = new Label("Time");
    addPane.add(timeLabel,0,8);
    timeLabel.getStyleClass().add("labelColor");

    ComboBox<String> hourInput = new ComboBox<>(); 
    for (int hour = 0; hour < 24; hour++){
        hourInput.getItems().add(String.format("%02d",hour));
    }
    hourInput.setValue("00");
    // addPane.add(hourInput,0,9);
    
    ComboBox<String> minuteInput = new ComboBox<>(); 
    for (int minute = 0; minute < 60; minute++){
        minuteInput.getItems().add(String.format("%02d",minute));
    }
    minuteInput.setValue("00");
    // addPane.add(minuteInput,1,9);
    HBox timePicker = new HBox(hourInput,minuteInput);
    addPane.add(timePicker,0,9);

    Label priorityLabel = new Label("Priority");
    addPane.add(priorityLabel,0,10);
    priorityLabel.getStyleClass().add("labelColor");

    ChoiceBox<String> priorityInput = new ChoiceBox<>();
    priorityInput.getItems().addAll("Low","Medium","High");
    priorityInput.setValue("Low");
    addPane.add(priorityInput,0,11);
    priorityInput.setPrefWidth(120);
    priorityInput.setPrefHeight(35);

    Label tagLabel = new Label("Tag");
    addPane.add(tagLabel,0,12);
    tagLabel.getStyleClass().add("labelColor");

    TextField tagInput = new TextField();
    addPane.add(tagInput,0,13);
    tagInput.setPrefWidth(120);
    tagInput.setPrefHeight(35);
    
    Button addBtn = new Button("Add Task");
    // addPane.add(addBtn,0,13);
    addBtn.setPrefWidth(80);
    addBtn.setPrefHeight(35);
    addBtn.getStyleClass().add("btn");

    Button closeBtn = new Button("Close");
    // addPane.add(closeBtn,1,10);
    closeBtn.setPrefWidth(80);
    closeBtn.setPrefHeight(35);
    // closeBtn.getStyleClass().add("btn");

    HBox btnBox = new HBox(addBtn,closeBtn);
    btnBox.setSpacing(5);
    addPane.add(btnBox,0,14);

    
    addBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try{
                Todolist.show(addStage); 
                String taskName = taskInput.getText();
                String description = descInput.getText();
                String tagName = tagInput.getText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
                LocalDate taskDate = dateInput.getValue();
                String formatDate= taskDate.format(formatter);

                Connection connection = Dbconnect.getConnect();
                String insertTaskQuery = "INSERT INTO task (user_id,priority_id,status_id,title,description,deadline) VALUES (1,1,10,?,?,?)";
                String insertTagQuery = "INSERT INTO tag (task_id, name) VALUES (LAST_INSERT_ID(), ?)";
                PreparedStatement statement = connection.prepareStatement(insertTaskQuery);

                statement.setString(1, taskName);
                statement.setString(2, description);
                // statement.setString(6, tagName);
                statement.setString(3, formatDate);

                // Execute the statement
                statement.executeUpdate();

                // Clear task input fields
                // taskInput.clear();
                descInput.clear();
                // tagInput.clear();
                // dateInput.clearDirty();
            }catch(SQLException e){
                e.printStackTrace();
            }catch (Exception e) {
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
public static void show(Stage showStage) throws Exception{
    BorderPane borderPane = new BorderPane();
    borderPane.setPadding(new Insets(20,20,20,20));
    borderPane.getStylesheets().add("/assets/showTaskStyle.css");

    borderPane.getStyleClass().add("bgColor");

    VBox box = new VBox();
    box.setAlignment(Pos.CENTER);


    // Header
    Text nameText = new Text();
    nameText.setText("Hola,Josh");
    nameText.getStyleClass().add("nameText");

    Text dateText = new Text();
    dateText.setText("15 Mar 2024");
    dateText.getStyleClass().add("dateText");
    Text timeText = new Text();
    timeText.setText("08:00");
    timeText.getStyleClass().add("dateText");

    HBox dateBox = new HBox(dateText,timeText);
    dateBox.setSpacing(3);
    VBox profileBox = new VBox(nameText,dateBox);

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
              msn.showMission(new Stage()); // Call the Todolist's add method to display the stage
              // primaryStage.close(); // Close the login stage after successful login
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
                user.profile(new Stage()); // Call the Todolist's add method to display the stage
                // primaryStage.close(); // Close the login stage after successful login
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

    Image newTaskImg = new Image(Todolist.class.getResourceAsStream("/assets/Image/Add_ring.png"));
    Button newTaskBtn = new Button("New Task");
    newTaskBtn.setGraphic(new ImageView(newTaskImg));
    newTaskBtn.setPrefWidth(149);
    newTaskBtn.setPrefHeight(51);
    newTaskBtn.getStyleClass().add("taskBtn");

    HBox btnBox = new HBox(missionBtn,profileBtn,clockBtn,newTaskBtn);
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

    HBox topBox = new HBox(profileBox, btnBox);
    borderPane.setTop(topBox);

    // untuk active dan button filter
    Text activeText = new Text("Active");
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

    HBox filterBox = new HBox(allBtn,lowBtn,medBtn,highBtn);
    filterBox.setSpacing(10);
    VBox activeBox = new VBox(activeText,filterBox);
    activeBox.setSpacing(12);

    VBox taskBox = new VBox();
    taskBox.getChildren().addAll(activeBox);
    BorderPane.setAlignment(activeBox,Pos.CENTER);
    // borderPane.setCenter(taskBox);
    taskBox.setSpacing(35);

    Button targetButton = allBtn;

    Platform.runLater(() -> {
        targetButton.fire(); // Simulate a button click after UI setup
    });

    // untuk menampilkan tugas dengan semua priority
    allBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          try {
            String[] tasks = {"Conduct User", "Finalize Content", "Implement SEO"};
            String[] tasksLabel = {"Testing", "Coding", "SEO"};
            String[] tasksDesc = {"Coordinate UAT", "Develop front-end", "Optimize website for search engines"};
            String[] tasksDate = {"April 15, 2024", "April 20, 2024", "April 25, 2024"};
            String[] tasksCategory = {"low", "medium", "high", "done"};
    
            VBox taskMainBox = new VBox(10);
            Text priorityText = new Text("Priority");
            priorityText.getStyleClass().add("pTask");
            VBox taskBoxMain = new VBox(10);
            taskMainBox.getChildren().clear();
            // **Execute the for loop only when lowBtn is clicked**
            for (int i = 0; i < tasks.length; i++) {
              if (tasksCategory[i].equals("low") || tasksCategory[i].equals("medium") || tasksCategory[i].equals("high")) {
                CheckBox cb = new CheckBox();
            cb.getStyleClass().add("check-box");
            cb.setAlignment(Pos.CENTER);
            Text task1Text = new Text(tasks[i]);
            task1Text.getStyleClass().add("pTask");
            Label taskLabel = new Label(tasksLabel[i]);
            taskLabel.getStyleClass().add("taskLabel");
            Text desText = new Text(tasksDesc[i]);
            desText.getStyleClass().add("descText");
            Text time1Text = new Text(tasksDate[i]);
            time1Text.getStyleClass().add("timeText");
            
            HBox taskPBox = new HBox(10);
            HBox taskDescBox = new HBox(10);
            VBox priorityTaskBox = new VBox();
            HBox priorityBox = new HBox(10);
            priorityBox.setAlignment(Pos.CENTER_LEFT);

            taskPBox.getChildren().addAll(task1Text, taskLabel);
            taskDescBox.getChildren().addAll(desText, time1Text);
            priorityTaskBox.getChildren().addAll(taskPBox, taskDescBox);
            priorityBox.getChildren().addAll(cb, priorityTaskBox);
            taskBoxMain.getChildren().add(priorityBox);
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

    // untuk menampilkan tugas dengan tingkat priority low
    lowBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          try {
            String[] tasks = {"Conduct User", "Finalize Content", "Implement SEO"};
            String[] tasksLabel = {"Testing", "Coding", "SEO"};
            String[] tasksDesc = {"Coordinate UAT", "Develop front-end", "Optimize website for search engines"};
            String[] tasksDate = {"April 15, 2024", "April 20, 2024", "April 25, 2024"};
            String[] tasksCategory = {"low", "medium", "high", "done"};
    
            VBox taskMainBox = new VBox(10);
            Text priorityText = new Text("Priority");
            priorityText.getStyleClass().add("pTask");
            VBox taskBoxMain = new VBox(10);
            taskMainBox.getChildren().clear();
            // **Execute the for loop only when lowBtn is clicked**
            for (int i = 0; i < tasks.length; i++) {
              if (tasksCategory[i].equals("low")) {
                CheckBox cb = new CheckBox();
            cb.getStyleClass().add("check-box");
            cb.setAlignment(Pos.CENTER);
            Text task1Text = new Text(tasks[i]);
            task1Text.getStyleClass().add("pTask");
            Label taskLabel = new Label(tasksLabel[i]);
            taskLabel.getStyleClass().add("taskLabel");
            Text desText = new Text(tasksDesc[i]);
            desText.getStyleClass().add("descText");
            Text time1Text = new Text(tasksDate[i]);
            time1Text.getStyleClass().add("timeText");
            
            HBox taskPBox = new HBox(10);
            HBox taskDescBox = new HBox(10);
            VBox priorityTaskBox = new VBox();
            HBox priorityBox = new HBox(10);
            priorityBox.setAlignment(Pos.CENTER_LEFT);

            taskPBox.getChildren().addAll(task1Text, taskLabel);
            taskDescBox.getChildren().addAll(desText, time1Text);
            priorityTaskBox.getChildren().addAll(taskPBox, taskDescBox);
            priorityBox.getChildren().addAll(cb, priorityTaskBox);
            taskBoxMain.getChildren().add(priorityBox);
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

      //// untuk menampilkan tugas dengan tingkat priority medium
      medBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          try {
            String[] tasks = {"Conduct User", "Finalize Content", "Implement SEO"};
            String[] tasksLabel = {"Testing", "Coding", "SEO"};
            String[] tasksDesc = {"Coordinate UAT", "Develop front-end", "Optimize website for search engines"};
            String[] tasksDate = {"April 15, 2024", "April 20, 2024", "April 25, 2024"};
            String[] tasksCategory = {"low", "medium", "high", "done"};
    
            VBox taskMainBox = new VBox(10);
            Text priorityText = new Text("Priority");
            priorityText.getStyleClass().add("pTask");
            VBox taskBoxMain = new VBox(10);
    
            // **Execute the for loop only when lowBtn is clicked**
            for (int i = 0; i < tasks.length; i++) {
              if (tasksCategory[i].equals("medium")) {
                CheckBox cb = new CheckBox();
            cb.getStyleClass().add("check-box");
            cb.setAlignment(Pos.CENTER);
            Text task1Text = new Text(tasks[i]);
            task1Text.getStyleClass().add("pTask");
            Label taskLabel = new Label(tasksLabel[i]);
            taskLabel.getStyleClass().add("taskLabel");
            Text desText = new Text(tasksDesc[i]);
            desText.getStyleClass().add("descText");
            Text time1Text = new Text(tasksDate[i]);
            time1Text.getStyleClass().add("timeText");
            
            HBox taskPBox = new HBox(10);
            HBox taskDescBox = new HBox(10);
            VBox priorityTaskBox = new VBox();
            HBox priorityBox = new HBox(10);
            priorityBox.setAlignment(Pos.CENTER_LEFT);

            taskPBox.getChildren().addAll(task1Text, taskLabel);
            taskDescBox.getChildren().addAll(desText, time1Text);
            priorityTaskBox.getChildren().addAll(taskPBox, taskDescBox);
            priorityBox.getChildren().addAll(cb, priorityTaskBox);
            taskBoxMain.getChildren().add(priorityBox);
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
            String[] tasks = {"Conduct User", "Finalize Content", "Implement SEO","Develop Admin"};
            String[] tasksLabel = {"Testing", "Coding", "SEO","Coding"};
            String[] tasksDesc = {"Coordinate UAT", "Develop front-end", "Optimize website for search engines","Design and develop"};
            String[] tasksDate = {"April 15, 2024", "April 20, 2024", "April 25, 2024","April 30, 2024"};
            String[] tasksCategory = {"low", "medium", "high", "done"};

            
            VBox taskMainBox = new VBox(10);
            Text priorityText = new Text("Priority");
            priorityText.getStyleClass().add("pTask");
            VBox taskBoxMain = new VBox(10);

            taskMainBox.getChildren().clear();
    
            // **Execute the for loop only when lowBtn is clicked**
            for (int i = 0; i < tasks.length; i++) {
              if (tasksCategory[i].equals("high")) {
                Button cb = new Button();
            cb.getStyleClass().add("check-box");
            cb.setAlignment(Pos.CENTER);
            Text task1Text = new Text(tasks[i]);
            task1Text.getStyleClass().add("pTask");
            Label taskLabel = new Label(tasksLabel[i]);
            taskLabel.getStyleClass().add("taskLabel");
            Text desText = new Text(tasksDesc[i]);
            desText.getStyleClass().add("descText");
            Text time1Text = new Text(tasksDate[i]);
            time1Text.getStyleClass().add("timeText");
            
            HBox taskPBox = new HBox(10);
            HBox taskDescBox = new HBox(10);
            VBox priorityTaskBox = new VBox();
            HBox priorityBox = new HBox(10);
            priorityBox.setAlignment(Pos.CENTER_LEFT);

            taskPBox.getChildren().addAll(task1Text, taskLabel);
            taskDescBox.getChildren().addAll(desText, time1Text);
            priorityTaskBox.getChildren().addAll(taskPBox, taskDescBox);
            priorityBox.getChildren().addAll(cb, priorityTaskBox);

            taskBoxMain.getChildren().add(priorityBox);
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
      
      String[] tasks = {"Conduct User", "Finalize Content", "Implement SEO","Develop Admin"};
      String[] tasksLabel = {"Testing", "Coding", "SEO","Coding"};
      String[] tasksDesc = {"Coordinate UAT", "Develop front-end", "Optimize website for search engines","Design and develop"};
      String[] tasksDate = {"April 15, 2024", "April 20, 2024", "April 25, 2024","April 30, 2024"};
      String[] tasksCategory = {"low", "medium", "high", "done"};
      
      // Untuk menampilkan tugas dengan kategori priority
      
      VBox taskMainBox = new VBox(10);
      VBox taskBoxMain = new VBox(10);
      
      for (int i = 0; i < tasks.length; i++) {
          if(tasksCategory[i].equals("done")){
              CheckBox cb = new CheckBox();
              cb.getStyleClass().add("check-box");
              cb.setAlignment(Pos.CENTER);
              Text task1Text = new Text(tasks[i]);
              task1Text.getStyleClass().add("taskDoneText");
              Text desText = new Text(tasksDesc[i]);
              desText.getStyleClass().add("descText");
              Text time1Text = new Text(tasksDate[i]);
              time1Text.getStyleClass().add("timeDoneText");
              
              HBox taskPBox = new HBox(10);
              HBox taskDescBox = new HBox(10);
              VBox priorityTaskBox = new VBox();
              HBox priorityBox = new HBox(10);
              priorityBox.setAlignment(Pos.CENTER_LEFT);
              
              taskPBox.getChildren().addAll(task1Text);
              taskDescBox.getChildren().addAll(desText, time1Text);
              priorityTaskBox.getChildren().addAll(taskPBox, taskDescBox);
              priorityBox.getChildren().addAll(cb, priorityTaskBox);
              taskBoxMain.getChildren().add(priorityBox);
              
            }
        }
        
        taskMainBox.getChildren().addAll(taskBoxMain);
        
        
        VBox centerTaskBox = new VBox(taskBox,doneText,taskMainBox);
        borderPane.setCenter(centerTaskBox);
        
        
    Scene scene = new Scene(borderPane, 1280, 768);
    showStage.setTitle("Task");
    showStage.setScene(scene);
    showStage.show();
}
}
