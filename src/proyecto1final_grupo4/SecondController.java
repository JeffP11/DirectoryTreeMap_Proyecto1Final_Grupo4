package proyecto1final_grupo4;

import Clases.Directory;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.imageio.ImageIO;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author jeffg
 */
public class SecondController implements Initializable {

    @FXML
    private AnchorPane barra;
    @FXML
    private TextField textfield;
    @FXML
    private Button visualize;
    @FXML
    private AnchorPane center;
    @FXML
    private Button save;

    LinkedList<Directory> treeMap;

    private double xOffset = 0;
    private double yOffset = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void quitButton(ActionEvent event) {
        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void getVector(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    private void moveWindow(MouseEvent event) {
        Stage stage = (Stage) barra.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    @FXML
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
            textfield.setText(selectedDir.getAbsolutePath());
            visualize.setDisable(false);
            treeMap = new LinkedList<>();
            treeMap.add(dir);
            System.out.println("---------- TreeMap ---------");
            iterar(treeMap, 0);
        }
        center.getChildren().clear();
        save.setDisable(true);
    }

    @FXML
    private void saveButtonAction(ActionEvent event) {
        boolean result = false;
        try {
            String nombre = "captura";
            String fecha = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            WritableImage snapshot = center.snapshot(null, null);

            BufferedImage bufferedImage;
            bufferedImage = SwingFXUtils.fromFXImage(snapshot, null);
            File file1 = new File(nombre + fecha + ".png");
            result = ImageIO.write(bufferedImage, "png", file1);
        } catch (IOException ex) {
            Logger.getLogger(SecondController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (result) {
            Alert dialog = new Alert(AlertType.INFORMATION);
            dialog.setTitle("Confirmación");
            dialog.setHeaderText(null);
            dialog.setContentText("Captura guardada con éxito!");
            dialog.initStyle(StageStyle.TRANSPARENT);
            dialog.showAndWait();
            
        } else {

        }
    }

    public boolean isFile(File file) {
        return !file.isDirectory();
    }

    public double redondeo(double tam, int decimales) {
        return new BigDecimal(tam)
                .setScale(decimales, RoundingMode.HALF_EVEN).doubleValue();
    }

    public String identar(int num) {
        char[] carac = new char[num];
        String iden = "";
        if (num > 0) {
            for (int i = 0; i < num; i++) {
                carac[i] = '-';
            }
            iden = new String(carac);
        } else {
            return iden;
        }

        return iden;
    }

    public void iterar(LinkedList<Directory> treeMap, int num) {
        Iterator it = treeMap.iterator();
        while (it.hasNext()) {
            Directory next = (Directory) it.next();

            if (next.getDirectorios().size() > 0) {
                System.out.println(identar(num) + "Carpeta: " + next.getName() + "| size: " + next.getSize());
                iterar(next.getDirectorios(), num + 2);
            } else {
                System.out.println(identar(num) + "Archivo: " + next.getName() + "| size: " + next.getSize());
            }
        }
    }

    public double recorrerDirectorio(File[] content, double total, Directory dirt) {
        for (File file : content) {
            if (isFile(file)) {
                total += redondeo(file.length() / 1024.0, 2);
                Directory direct = new Directory(file.getName(), redondeo(file.length() / 1024.0, 2));
                dirt.getDirectorios().add(direct);
            } else {
                double tam = 0.0;
                Directory dir = new Directory(file.getName());
                double size = redondeo(recorrerDirectorio(file.listFiles(), tam, dir), 2);
                dir.setSize(size);
                dirt.getDirectorios().add(dir);
                total += size;
            }
        }
        return total;
    }

    public void setLabelSize(Label lb, double amount) {
        lb.setStyle("-fx-font-weight: bold; -fx-font-size: 15");
        DecimalFormat two = new DecimalFormat("0.00");
        if (amount < 1024) {
            lb.setText("(" + amount + " Bytes" + ")");
        } else if (amount > 1024 && amount < 1024 * 1024) {
            lb.setText("(" + two.format(amount / 1024) + " KB" + ")");
        } else if (amount > 1024 * 1024 && amount < 1024 * 1024) {
            lb.setText("(" + two.format(amount / 1024 * 1024) + " MB" + ")");
        } else {
            lb.setText("(" + two.format(amount / 1024 * 1024 * 1024) + " GB" + ")");
        }

    }

    public Color getRandomColor() {
        Random rd = new Random();
        float r = rd.nextFloat();
        float g = rd.nextFloat();
        float b = rd.nextFloat();
        Color randomColor = new Color(r, g, b, 1);
        return randomColor;
    }

    @FXML
    public void visualizeButtonAction(ActionEvent event) throws IOException {
        VBox container = new VBox();
        Pane SizeTotal = new Pane();

        HBox graphics = new HBox();
        graphics.setMaxWidth(960);
        graphics.setMaxHeight(650);

        Rectangle graphicSizeTotal = new Rectangle();
        graphicSizeTotal.setWidth(960);
        graphicSizeTotal.setHeight(25);
        graphicSizeTotal.setFill(Color.CORAL);
        graphicSizeTotal.setStroke(Color.WHITE);

        Label extensionSize = new Label();
        setLabelSize(extensionSize, treeMap.getFirst().getSize());

        SizeTotal.getChildren().addAll(graphicSizeTotal, extensionSize);
        container.getChildren().addAll(SizeTotal, graphics);
        Painting(graphics, "h", treeMap);
        center.getChildren().addAll(container);
        save.setDisable(false);
    }

    public void Painting(Pane graphics, String box, LinkedList<Directory> treeMap) {
        String type = box;
        if (!treeMap.isEmpty()) {
            double sizetotal = treeMap.getFirst().getSize();
            treeMap.getFirst().getDirectorios().forEach(directory -> {
                if (!directory.getDirectorios().isEmpty()) {
                    if (type.equals("h")) {
                        VBox paneH = new VBox();
                        double factor1 = graphics.getMaxWidth() * (directory.getSize() / sizetotal);
                        double factor2 = graphics.getMaxHeight();
                        Rectangle rec = new Rectangle(factor1, factor2);
                        rec.setFill(getRandomColor());
                        paneH.getChildren().add(rec);
                        graphics.getChildren().add(paneH);
                        Painting(paneH, "v", directory.getDirectorios());
                    } else {
                        HBox paneV = new HBox();
                        double factor1 = graphics.getMaxWidth();
                        double factor2 = graphics.getMaxHeight() * (directory.getSize() / sizetotal);
                        Rectangle rec = new Rectangle(factor1, factor2);
                        rec.setFill(getRandomColor());
                        paneV.getChildren().add(rec);
                        graphics.getChildren().add(rec);
                        Painting(paneV, "h", directory.getDirectorios());
                    }
                } else {
                    Rectangle rec = new Rectangle(graphics.getMaxWidth(), graphics.getMinHeight());
                    rec.setFill(getRandomColor());
                    graphics.getChildren().add(rec);
                }
            });

        } else {
            Rectangle colors = new Rectangle(graphics.getWidth(), graphics.getHeight(),
                    new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE, new Stop[]{
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

    /*private void llenar() {
        System.out.println("primero: " + this.treeMap.get(0).getDirectorios().size());

        int divisiones = this.treeMap.get(0).getDirectorios().size();
        double tamanio = this.treeMap.get(0).getSize();
        HBox hbox = new HBox(divisiones);
        double espacio = 960 - (5 * (divisiones - 1));
        for (int i = 0; i < divisiones; i++) {
            double peso = this.treeMap.get(0).getDirectorios().get(i).getSize();
            double porcentaje = (peso / tamanio);
            double ancho = porcentaje * espacio;
            double alto = 650;
            Rectangle rectangle = new Rectangle(0, 0, ancho, alto);
            if (this.treeMap.get(0).getDirectorios().get(i).getDirectorios().size() > 0) {
                System.out.println("Carpeta: " + this.treeMap.get(0).getDirectorios().get(i).getName() + "| size: " + this.treeMap.get(0).getDirectorios().get(i).getSize());
                rectangle.setFill(Color.GRAY);
            } else {
                System.out.println("--> Archivo: " + this.treeMap.get(0).getDirectorios().get(i).getName() + "| size: " + this.treeMap.get(0).getDirectorios().get(i).getSize());
                rectangle.setFill(Color.WHEAT);
            }
            hbox.getChildren().add(rectangle);
        }

        center.getChildren().add(hbox);
    }*/
}
