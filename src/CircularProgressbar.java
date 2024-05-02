import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.geometry.VPos;

public class CircularProgressbar extends Canvas {
    private final Color progressColor;
    private final Color centerBorderColor;
    private final Color centerColor;
    private final Color fontColor;
    private final double progressWidth;
    private final double centerBorderWidth;

    public CircularProgressbar(double radius, double progressWidth, double centerBorderWidth) {
        super(radius, radius);
        this.progressWidth = progressWidth;
        this.centerBorderWidth = centerBorderWidth;
        final GraphicsContext gc = getGraphicsContext2D();
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        progressColor = Color.web("#FF6629");
        centerBorderColor = Color.web("E6E6E6");
        centerColor = Color.web("#FFFFFF");
        fontColor = Color.web("#000000");
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    }

    public void draw(double progressPercentage, String timerText) {
        final GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
    
        // Draw center border first
        gc.setFill(centerBorderColor);
        gc.fillArc(progressWidth, progressWidth, getWidth() - (progressWidth * 2), getHeight() - (progressWidth * 2), 0, -360, ArcType.ROUND);
    
        // Draw progress bar above center border
        gc.setFill(progressColor);
        gc.fillArc(progressWidth, progressWidth, getWidth() - (progressWidth * 2), getHeight() - (progressWidth * 2), 90, -progressPercentage * 3.6, ArcType.ROUND); // Adjust to draw counterclockwise
    
        // Draw center color last
        gc.setFill(centerColor);
        gc.fillArc(progressWidth + centerBorderWidth, progressWidth + centerBorderWidth, getWidth() - ((progressWidth + centerBorderWidth) * 2), getHeight() - ((progressWidth + centerBorderWidth) * 2), 0, -360, ArcType.ROUND);
    
        // Draw text
        gc.setFill(fontColor);
        gc.fillText(timerText, getWidth() / 2, getHeight() / 2);
    }
}
