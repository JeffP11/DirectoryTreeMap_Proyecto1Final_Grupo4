/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1final_grupo4;

import Clases.Directory;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
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

    LinkedList<Directory> treeMap;

    public void directoryButtonAction(ActionEvent event) {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setInitialDirectory(new File("src"));

        File selectedDir = dc.showDialog(null);

        if (selectedDir == null) {
            System.out.println("Not directory selected");
        } else {
            double size = 0;
            Directory dir = new Directory(selectedDir.getName());
            dir.setSize(redondeo(recorrerDirectorio(selectedDir.listFiles(), size, dir), 2));
            //System.out.println("Carpeta: " + selectedDir.getName() + "| size: " + dir.getSize() + " kb");

            treeMap = new LinkedList<Directory>();
            treeMap.add(dir);
            System.out.println("---------- TreeMap ---------");
            iterar(treeMap,0);
        }
    }

    public void iterar(LinkedList<Directory> treeMap,int num) {
        Iterator it = treeMap.iterator();
        while (it.hasNext()) {
            Directory next = (Directory) it.next();

            if (next.getDirectorios().size() > 0) {
                System.out.println(identar(num)+"Carpeta: " + next.getName() + "| size: " + next.getSize());
                iterar(next.getDirectorios(),num+2);
            } else {
                System.out.println(identar(num)+"Archivo: " + next.getName() + "| size: " + next.getSize());
            }
        }
    }

    public String identar(int num) {
        char[] carac = new char[num];
        String iden = "";
        if (num > 0) {
            for (int i = 0; i < num; i++) {
                carac[i] = '-';
            }
            iden = new String(carac);
        }else{
            return iden;
        }
         
        return iden;
    }

    public double recorrerDirectorio(File[] content, double total, Directory dirt) {

        for (File file : content) {
            if (isFile(file)) {
                total += redondeo(file.length() / 1024.0, 2);
                Directory direct = new Directory(file.getName(), redondeo(file.length() / 1024.0, 2));
                //System.out.println("--> Archivo: " + direct.getName() + "| size: " + direct.getSize() + " kb");
                dirt.getDirectorios().add(direct);
            } else {
                double tam = 0.0;
                Directory dir = new Directory(file.getName());
                double size = redondeo(recorrerDirectorio(file.listFiles(), tam, dir), 2);
                dir.setSize(size);
                //System.out.println("Carpeta: " + dir.getName() + "| size: " + dir.getSize() + " kb");
                dirt.getDirectorios().add(dir);
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
