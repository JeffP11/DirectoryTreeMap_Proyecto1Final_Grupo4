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
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
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
       
        VBox container = new VBox();
        Pane SizeTotal = new Pane();
        
        HBox graphics = new HBox();
        graphics.setMaxWidth(960);
        graphics.setMaxHeight(500);
        
        Rectangle graphicSizeTotal = new Rectangle();
        graphicSizeTotal.setWidth(960);
        graphicSizeTotal.setHeight(500);
        graphicSizeTotal.setFill(Color.CORAL);
        graphicSizeTotal.setStroke(Color.WHITE);
        
        Label extensionSize = new Label();
        setLabelSize(extensionSize,treeMap.getFirst().getSize());
        
        SizeTotal.getChildren().addAll(graphicSizeTotal,extensionSize);
        
        container.getChildren().addAll(SizeTotal,graphics);
        
        Painting(graphics,"h",treeMap);
        
        
    }
    
    public void Painting(Pane graphics,String box,LinkedList<Directory> treeMap){
        String type = box;
        if(!treeMap.isEmpty()){
            double sizetotal = treeMap.getFirst().getSize();
            treeMap.getFirst().getDirectorios().forEach(directory -> {
                //Label sizeLabel = new Label();
                //setLabelSize(sizeLabel,directory.getSize());
                if(!directory.getDirectorios().isEmpty()){
                    if(type.equals("h")){
                        VBox paneH = new VBox();
                        double factor1 = graphics.getMaxWidth()*(Math.sqrt(directory.getSize()/sizetotal));
                        double factor2 = graphics.getMaxHeight();
                        Rectangle rec = new Rectangle();
                        rec.setFill(getRandomColor());
                        paneH.getChildren().add(rec);
                        graphics.getChildren().add(paneH);
                        Painting(paneH,"v",directory.getDirectorios());
                    }else{
                        HBox paneV = new HBox();
                        double factor1 = graphics.getMaxWidth();
                        double factor2 = graphics.getMaxHeight()*(Math.sqrt(directory.getSize()/sizetotal));
                        Rectangle rec = new Rectangle();
                        rec.setFill(getRandomColor());
                        paneV.getChildren().add(rec);
                        graphics.getChildren().add(rec);
                        Painting(paneV,"h",directory.getDirectorios());
                    }
                    
                }else{
                    Rectangle rec = new Rectangle(graphics.getMaxWidth(),graphics.getMinHeight());
                    rec.setFill(getRandomColor());
                    graphics.getChildren().add(rec);
                }
            });
            
        }else{
            Rectangle colors = new Rectangle(graphics.getWidth(), graphics.getHeight(),
            new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE, new 
                Stop[]{
                   new Stop(0, Color.web("#f8bd55")),
                   new Stop(0.14, Color.web("#c0fe56")),
                   new Stop(0.28, Color.web("#5dfbc1")),
                   new Stop(0.43, Color.web("#64c2f8")),
                   new Stop(0.57, Color.web("#be4af7")),
                   new Stop(0.71, Color.web("#ed5fc2")),
                   new Stop(0.85, Color.web("#ef504c")),
                   new Stop(1, Color.web("#f2660f")),}));
                   colors.widthProperty().bind(graphics.widthProperty());
                   colors.heightProperty().bind(graphics.heightProperty());
                   graphics.getChildren().add(colors);
        }
    }

    public void exitButtonAction(ActionEvent event) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
    public void setLabelSize(Label lb, double amount){
        lb.setStyle("-fx-font-weight: bold; -fx-font-size: 15");
        DecimalFormat two = new DecimalFormat("0.00");
        if(amount < 1024){
            lb.setText("("+amount+" Bytes"+")" );
        }else if(amount > 1024 && amount < 1024*1024){
                lb.setText("("+two.format(amount/1024)+" KB"+")" );
        }else if(amount > 1024*1024 && amount < 1024*1024){
            lb.setText("("+two.format(amount/1024*1024)+" MB"+")" );
        }else{
            lb.setText("("+two.format(amount/1024*1024*1024)+" GB"+")" );
        }
        
    }
    
    public Color getRandomColor(){
        Random rd = new Random();
        float r = rd.nextFloat();
        float g = rd.nextFloat();
        float b = rd.nextFloat();
        Color randomColor = new Color(r, g, b,1);
        return randomColor;
    }
}
