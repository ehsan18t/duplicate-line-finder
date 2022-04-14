package dev.pages.ahsan40.dlf.controller;

import dev.pages.ahsan40.dlf.main.Configs;
import dev.pages.ahsan40.dlf.main.Main;
import dev.pages.ahsan40.dlf.main.TextFile;
import dev.pages.ahsan40.dlf.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private AnchorPane pnlDragDrop;

    @FXML
    private AnchorPane pnlInstructions;

    @FXML
    private ImageView btnClose;

    @FXML
    private ImageView btnMin;

    @FXML
    private Button btnScan;

    @FXML
    private Text title;

    @FXML
    private AnchorPane pnlFileInfo;

    @FXML
    private Text txtFileName;

    @FXML
    private Text txtFileSize;

    @FXML
    private Text txtFilePath;

    @FXML
    private CheckBox chkCaseSensitive;

    @FXML
    private CheckBox chkIgnoreEmptyLines;

    @FXML
    private CheckBox chkIgnoreWhiteSpace;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // init
        title.setText(Configs.title + " " + Configs.version);
        txtFileName.setText("");
        txtFileSize.setText("");
        txtFilePath.setText("");

        // References
        btnMin.setOnMouseClicked(this::btnMinAction);
        btnClose.setOnMouseClicked(this::btnCloseAction);
        pnlDragDrop.setOnMouseClicked(this::fileSelector);
        btnScan.setOnAction(this::btnScanAction);
    }

    private void btnScanAction(ActionEvent actionEvent) {
        Main.textFile = new TextFile(chkIgnoreEmptyLines.isSelected(), chkIgnoreWhiteSpace.isSelected(), chkCaseSensitive.isSelected(), Main.file);
        Utils.changeScene(Configs.resultPage, 600, 800);
    }

    private void handleFile(File file) {
        pnlInstructions.setVisible(false);
        pnlFileInfo.setVisible(true);
        txtFileName.setText(file.getName());
        txtFileSize.setText(String.format("%.2f", file.length() / 1024.0) + "KB");
        txtFilePath.setText(file.getAbsolutePath());
        Main.file = file;
    }

    private void fileSelector(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(Main.primaryStage);
        if (selectedFile == null)
            System.out.println(" - Canceled!");
        else {
            handleFile(selectedFile);
        }
    }

    @FXML
    private void handleFileOverEvent(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasFiles())
            event.acceptTransferModes(TransferMode.COPY);
        else
            event.consume();
    }

    @FXML
    private void handleFileDroppedEvent(DragEvent event) {
        Dragboard db = event.getDragboard();
        File file = db.getFiles().get(0);

        // Handle File
        handleFile(file);
    }

    private void btnCloseAction(MouseEvent event) {
        Utils.exit();
    }

    private void btnMinAction(MouseEvent event) {
        Main.primaryStage.setIconified(true);
    }
}
