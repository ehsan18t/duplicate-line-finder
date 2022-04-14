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

import java.io.*;
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

    private HashMap<String, Line> lines;
    private ArrayList<Line> original;
    @FXML
    private Text title;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Window Settings
        Main.primaryStage.setTitle(Configs.title + " " + Configs.version);
        title.setText(Configs.title + " " + Configs.version);

        // init
        original = new ArrayList<>();
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
        btnRemove.setOnMouseClicked(this::btnRemoveAction);
        btnCopy.setOnMouseClicked(this::btnCopyAction);
    }

    private void btnCopyAction(MouseEvent mouseEvent) {
        Line l = table.getSelectionModel().getSelectedItem();
        Utils.copyToClipboard(l.getText());
    }

    private void btnRemoveAction(MouseEvent mouseEvent) {
        // Remove item from table
        Line l = table.getSelectionModel().getSelectedItem();
        table.getItems().remove(l);

        // Sorting line list in Desc
        l.getAllLine().sort(Comparator.reverseOrder());

        // Removing item from original list
        for(int i: l.getAllLine()) {
            original.remove(i - 1);
        }

        // Write original list
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(Main.textFile.getFile().getAbsoluteFile()));
            for (Line ln: original) {
                bw.write(ln.getText());
                bw.newLine();
            }
            bw.close();
        } catch (Exception ignored){}
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
                original.add(new Line(i, str, key));

                // Case-Sensitive Disabled
                if (!Main.textFile.isCaseSensitive())
                    key = str.toLowerCase();

                // Ignore White Space Enabled
                if (Main.textFile.isIgnoreWhiteSpace())
                    key = key.replaceAll(" ", "").trim();

                // Ignore Empty Lines Disabled
                if (!Main.textFile.isIgnoreEmptyLines() && str.equals("")) {
                    addLine(str, str, i);
                } else {
                    addLine(key, str, i);
                }

                // ignore Empty Lines Enabled
                if (Main.textFile.isIgnoreEmptyLines())
                    lines.remove("");
            }
            br.close();
        } catch (IOException ignored) {}
    }

    private void addLine(String key, String str, int line) {
        if (lines.containsKey(key)) {
            Line l = lines.get(key);
            l.addLine(line);
            lines.put(key, l);
        } else
            lines.put(key, new Line(line, str, key));
    }


    private void btnCloseAction(MouseEvent event) {
        Utils.exit();
    }

    private void btnMinAction(MouseEvent event) {
        Main.primaryStage.setIconified(true);
    }
}
