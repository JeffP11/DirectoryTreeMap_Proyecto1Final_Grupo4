/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1final_grupo4;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author jeffg
 */
public class Proyecto1Final_Grupo4 extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("first.fxml"));
        Scene scene = new Scene(root, 600, 400);
        makeStageDrageable(root, primaryStage);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.getIcons().add(new Image("\\files\\treemap.png"));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void makeStageDrageable(Parent root, Stage primaryStage) {
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
