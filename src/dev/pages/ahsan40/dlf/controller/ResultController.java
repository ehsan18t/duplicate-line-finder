package dev.pages.ahsan40.dlf.controller;

import dev.pages.ahsan40.dlf.main.Configs;
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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
    private TableColumn<Line, String> colLine;

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
        readLines();

        // Table
        colLine.setCellValueFactory(new PropertyValueFactory<>("lines"));
        colText.setCellValueFactory(new PropertyValueFactory<>("text"));
        colCopies.setCellValueFactory(new PropertyValueFactory<>("copies"));
        table.setItems(getLines());

        // References
        btnMin.setOnMouseClicked(this::btnMinAction);
        btnClose.setOnMouseClicked(this::btnCloseAction);
        btnBack.setOnMouseClicked(this::btnBackAction);
    }

    private void btnBackAction(MouseEvent mouseEvent) {
        Utils.changeScene(Configs.homePage);
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

    private void readLines() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(Main.textFile.getFile().getAbsoluteFile()));
            String str;
            int i = 0;
            while ((str = br.readLine()) != null) {
                i++;
                String key = str;

                // Case-Sensitive Disabled
                if (!Main.textFile.isCaseSensitive())
                    key = str.toLowerCase();

                // Ignore White Space Enabled
                if (Main.textFile.isIgnoreWhiteSpace())
                    key = key.replaceAll(" ", "").trim();

                // Ignore Empty Lines
                if (!Main.textFile.isIgnoreEmptyLines() && str.equals("")) {
                    if (lines.containsKey(str)) {
                        Line l = lines.get(str);
                        l.addLine(i);
                        lines.put(str, l);
                    } else
                        lines.put(str, new Line(i, str));
                } else
                    lines.put(key, new Line(i, str));
            }
            br.close();
        } catch (IOException ignored) {}
    }


    private void btnCloseAction(MouseEvent event) {
        Utils.exit();
    }

    private void btnMinAction(MouseEvent event) {
        Main.primaryStage.setIconified(true);
    }
}
