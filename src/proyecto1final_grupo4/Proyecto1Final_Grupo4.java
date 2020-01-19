package proyecto1final_grupo4;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Proyecto Final - 1T 2019 / Visualización de Directorios usando Treemaps
 * @author GRUPO 4
 */
public class Proyecto1Final_Grupo4 extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("second.fxml"));
        Scene scene = new Scene(root, 960, 741.4);
        makeStageDrageable(root, primaryStage, scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        //primaryStage.getIcons().add(new Image("\\files\\treemap.png")); //GENERA ERROR EN MACOS
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void makeStageDrageable(Parent root, Stage primaryStage, Scene scene) {
        scene.setFill(Color.TRANSPARENT);
        root.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent event) -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
            primaryStage.setOpacity(0.7f);
        });
        root.setOnDragDone((e) -> {
            primaryStage.setOpacity(1.0f);
        });
        root.setOnMouseReleased((e) -> {
            primaryStage.setOpacity(1.0f);
        });
    }
}
