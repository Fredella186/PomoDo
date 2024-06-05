import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.AWTException;
import java.io.*;
import javax.imageio.*;
import java.util.concurrent.*;

import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class ScreenCapture {
    // indicator for looping screenCapture
    public static boolean running = true;

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

    public static int getRandomInterval() {
        int min = 10;
        int max = 30;
        int duration = (int) (Math.random() * (max - min + 1)) + min;
        System.out.println("Interval: " + duration);
        return duration;
    }

    public static String startScreenCapture() throws AWTException, IOException, SQLException {
        Robot robot = new Robot();
        Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage image = robot.createScreenCapture(rectangle);

        Connection connection = Dbconnect.getConnect();
        String sql = "SELECT title FROM tasks WHERE created_by=? AND id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, App.currentUserId);
        statement.setString(2, Todolist.currentTaskId);
        ResultSet resultSet = statement.executeQuery();

        String taskName = null;
        if (resultSet.next()) {
            taskName = resultSet.getString("title");
        }

        String directoryPath = System.getProperty("user.dir") + "/src/screenshots/" + taskName;
        File directory = new File(directoryPath);

        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directory created successfully!");
            } else {
                System.out.println("Failed to create directory!");
                return null;
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

        System.out.println("File berhasil disave di " + file.getAbsolutePath());
        return fileName; // Mengembalikan nama file gambar
    }

    public static void startScreenCaptureLoop() {
        new Thread(() -> {
            while (running) {
                try {
                    // // Capture screen and get file name
                    // String fileName = ScreenCapture.startScreenCapture();
                    // if (fileName != null) {
                    // // // Save the file name to the database
                    // Todolist.saveFileNameToDatabase(fileName);
                    // }
                    // Delay between captures
                    running = true;
                    startScreenCapture();
                    TimeUnit.SECONDS.sleep(getRandomInterval());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Loop stopped.");
        }).start();
    }

    public static void imagesToPdf() throws IOException, SQLException {
        Connection connection = Dbconnect.getConnect();
        String sql = "SELECT title FROM tasks WHERE created_by=? AND id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, App.currentUserId);
        statement.setString(2, Todolist.currentTaskId);
        ResultSet resultSet = statement.executeQuery();

        String taskName = null;
        if (resultSet.next()) {
            taskName = resultSet.getString("title");
        }

        String directoryPath = System.getProperty("user.dir") + "/src/screenshots/" + taskName;
        File directory = new File(directoryPath);

        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directory created successfully!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }

        PDDocument document = new PDDocument();
        File fontFile = new File(
                "C:\\Users\\ACER\\Documents\\GitHub\\PomoDo\\src\\assets\\fonts\\Satoshi-Variable.ttf");

        // Memuat font dari file
        PDType0Font font = PDType0Font.load(document, fontFile);

        for (File file : directory.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".png")) {
                PDPage page = new PDPage();
                document.addPage(page);
                PDImageXObject pdImage = PDImageXObject.createFromFile(file.getAbsolutePath(), document);
                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                // Menghitung skala gambar agar tetap proporsional
                float imageWidth = pdImage.getWidth();
                float imageHeight = pdImage.getHeight();
                float pageWidth = page.getMediaBox().getWidth();
                float pageHeight = page.getMediaBox().getHeight();
                float margin = 50;
                float availableWidth = pageWidth - 2 * margin;
                float availableHeight = pageHeight - 2 * margin - 20; // 20 for the text space
                float scale = Math.min(availableWidth / imageWidth, availableHeight / imageHeight);
                float scaledWidth = imageWidth * scale;
                float scaledHeight = imageHeight * scale;
                float imageX = (pageWidth - scaledWidth) / 2;
                float imageY = (pageHeight - scaledHeight) / 2;

                // Menggambar gambar di halaman
                contentStream.drawImage(pdImage, imageX, imageY, scaledWidth, scaledHeight);

                // Menambahkan teks di bawah gambar menggunakan font yang diimpor
                contentStream.beginText();
                contentStream.setFont(font, 12);
                contentStream.newLineAtOffset(imageX, imageY - 15);
                contentStream.showText(file.getName());
                contentStream.endText();

                contentStream.close();
            }
        }

        document.save(directoryPath + ".pdf");
        document.close();
    }
}
