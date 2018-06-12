package code;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Random;

public class Controller {
    private final static int REFRESH_RATE_IN_MS = 100;
    private final static int MAX_BLUR_SIZE = 5;
    private final static int RECTANGLE_PARENT_RATIO = 20;
    private final static double RECTANGLE_OPACITY = 0.1;

    @FXML
    private Pane pane;
    @FXML
    private Canvas canvas;

    private int nodeSize;

    private Random random;
    private BoxBlur boxBlur;
    private GraphicsContext graphicsContext;

    public Controller() {
        random = new Random();
        boxBlur = new BoxBlur();
    }

    public void initialize(int nodeSize) {
        this.nodeSize = nodeSize;
        initializeCanvas();
    }

    private void initializeCanvas() {
        graphicsContext = canvas.getGraphicsContext2D();

        canvas.widthProperty().bind(pane.widthProperty());
        canvas.heightProperty().bind(pane.heightProperty());
        canvas.setEffect(boxBlur);

        initializeAnimation();
        initializeRectangles();
    }

    private void initializeAnimation() {
        Duration duration = Duration.millis(REFRESH_RATE_IN_MS);
        KeyFrame keyFrame = new KeyFrame(duration, e -> updateCanvas());

        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void initializeRectangles() {
        Color rectangleColor1 = Color.rgb(0, 0, 0, RECTANGLE_OPACITY);
        Color rectangleColor2 = Color.rgb(255, 255, 255, RECTANGLE_OPACITY);
        addRectangle(rectangleColor1);
        addRectangle(rectangleColor2);
    }

    private void addRectangle(Color color) {
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(color);
        rectangle.heightProperty().bind(canvas.heightProperty().divide(RECTANGLE_PARENT_RATIO));
        rectangle.widthProperty().bind(canvas.widthProperty());
        pane.getChildren().add(rectangle);

        animateRectangle(rectangle);
    }

    private void animateRectangle(Rectangle rectangle) {
        double canvasHeight = canvas.getHeight();
        double rectangleHeight = rectangle.getHeight();
        int randomHeightRange = (int) (canvasHeight + rectangleHeight);
        int randomPosition = random.nextInt(randomHeightRange) - (int) rectangleHeight;

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    animateRectangle(rectangle);
                }, new KeyValue(rectangle.yProperty(), randomPosition, Interpolator.EASE_BOTH))
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    private void updateCanvas() {
        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();

        int matrixSizeX = (int) (canvasWidth / nodeSize);
        int matrixSizeY = (int) (canvasHeight / nodeSize);

        graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);
        graphicsContext.setFill(Color.BLACK);

        int nodeSizeToPaint = (matrixSizeX * matrixSizeY) / 2;
        for (int i = 0; i < nodeSizeToPaint; i++) {
            int nodePosX = random.nextInt(matrixSizeX) * nodeSize;
            int nodePosY = random.nextInt(matrixSizeY) * nodeSize;
            graphicsContext.fillRect(nodePosX, nodePosY, nodeSize, nodeSize);
        }

        boxBlur.setHeight(random.nextInt(MAX_BLUR_SIZE));
        boxBlur.setWidth(random.nextInt(MAX_BLUR_SIZE));
    }
}