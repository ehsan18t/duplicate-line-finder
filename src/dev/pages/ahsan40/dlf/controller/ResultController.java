package dev.pages.ahsan40.dlf.controller;

import dev.pages.ahsan40.dlf.main.Line;
import dev.pages.ahsan40.dlf.main.Main;
import dev.pages.ahsan40.dlf.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

public class ResultController implements Initializable {

    @FXML
    private Button btnBack;

    @FXML
    private ImageView btnClose;

    @FXML
    private Button btnCopy;

    @FXML
    private ImageView btnMin;

    @FXML
    private Button btnRemove;


    @FXML
    private TableColumn<Line, Integer> colCopies;

    @FXML
    private TableColumn<Line, Integer> colLine;

    @FXML
    private TableColumn<Line, String> colText;

    @FXML
    private TableView<Line> table;

    HashMap<String, Line> lines;

    @FXML
    private Text title;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // init
        lines = new HashMap<>();

        // Table
        colLine.setCellValueFactory(new PropertyValueFactory<>("line"));
        colText.setCellValueFactory(new PropertyValueFactory<>("text"));
        colCopies.setCellValueFactory(new PropertyValueFactory<>("copies"));
        table.setItems(getLines());
    }

    public ObservableList<Line> getLines() {
        ObservableList<Line> allLines = FXCollections.observableArrayList();
        for (Map.Entry<String, Line> l: lines.entrySet()) {
                allLines.addAll(l.getValue());
        }
        // sort by Line No.
        allLines.sort(Comparator.comparing(Line::getLines));
        return allLines;
    }

    private void btnCloseAction(MouseEvent event) {
        Utils.exit();
    }

    private void btnMinAction(MouseEvent event) {
        Main.primaryStage.setIconified(true);
    }
}
