/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1final_grupo4;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jeffg
 */
public class FirstController implements Initializable { 
    @FXML
    private Button directory;
    
    @FXML
    private Button multiFile;
    
    @FXML
    private Button visualize;
    
    @FXML
    private Button exit;
    
    @FXML
    private ListView listView;
    

    public void directoryButtonAction(ActionEvent event) {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setInitialDirectory(new File("src"));
        
        File selectedDir = dc.showDialog(null);
        double size;
        if (selectedDir == null) {
            System.out.println("Not directory selected");
        } else {
            size = selectedDir.length();
            listView.getItems().add(selectedDir.getAbsolutePath());
            visualize.setVisible(true);
            visualize.setDisable(false);
            System.out.println("size: " + size + " " + size/1024);
        }
    }
    
    public void multiFileButtonAction(ActionEvent event) {
        FileChooser fc = new FileChooser();
        
        List<File> selectedFiles = fc.showOpenMultipleDialog(null);
        
        if (selectedFiles != null) {
            selectedFiles.forEach((f) -> {
                listView.getItems().add(f.getAbsolutePath());
            });
            visualize.setVisible(true);
            visualize.setDisable(false);
        } else {
            System.out.println("Files not valid");
        }
    }
    
    public void visualizeButtonAction(ActionEvent event) {
        
    }
    
    public void exitButtonAction(ActionEvent event) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
}
