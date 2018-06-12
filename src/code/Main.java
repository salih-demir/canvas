package code;

import code.util.LogUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {
    private final static int NODE_SIZE = 1;
    private final static int SCENE_WIDTH = 1000;
    private final static int SCENE_HEIGHT = 600;
    private final static String APPLICATION_TITLE = "Canvas Example";
    private final static String APPLICATION_LAYOUT_NAME = "/res/layout/main.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            initializeStage(primaryStage);
        } catch (Exception ex) {
            LogUtil.logException(ex);
        }
    }

    private void initializeStage(Stage stage) throws Exception {
        URL url = getClass().getResource(APPLICATION_LAYOUT_NAME);
        FXMLLoader loader = new FXMLLoader(url);

        Parent root = loader.load();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        stage.setTitle(APPLICATION_TITLE);
        stage.setScene(scene);
        stage.show();

        Controller controller = loader.getController();
        controller.initialize(NODE_SIZE);
    }
}