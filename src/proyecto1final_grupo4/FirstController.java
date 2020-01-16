/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1final_grupo4;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

        if (selectedDir == null) {
            System.out.println("Not directory selected");
        } else {
            double size = 0;
            System.out.println("Carpeta: " + selectedDir.getName() + "| size: " + recorrerDirectorio(selectedDir.listFiles(), size) + " kb");
        }
    }

    public double recorrerDirectorio(File[] content, double total) {
        
        for (File file : content) {
            if (isFile(file)){
                System.out.println("--> Archivo: " + file.getName() + "| size: " + redondeo(file.length() / 1024.0, 2) + " kb");
                total += redondeo(file.length() / 1024.0, 2);
            }else{
                double tam = 0.0;
                double size = redondeo(recorrerDirectorio(file.listFiles(), tam),2);
                System.out.println("Carpeta: " + file.getName() + "| size: " + size + " kb");
                total += size;
            }    
        }
        return total;
    }

    public boolean isFile(File file) {
        return !file.isDirectory();
    }

    public double redondeo(double tam, int decimales) {
        return new BigDecimal(tam)
                .setScale(decimales, RoundingMode.HALF_EVEN).doubleValue();
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
