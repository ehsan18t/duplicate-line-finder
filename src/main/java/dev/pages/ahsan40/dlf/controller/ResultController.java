package dev.pages.ahsan40.dlf.controller;

import dev.pages.ahsan40.dlf.main.Configs;
import dev.pages.ahsan40.dlf.main.Line;
import dev.pages.ahsan40.dlf.main.Main;
import dev.pages.ahsan40.dlf.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    private Button btnRemoveAll;

    @FXML
    private Button btnRemoveDup;


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
        btnRemoveAll.setOnMouseClicked(this::btnRemoveAllAction);
        btnRemoveDup.setOnMouseClicked(this::btnRemoveDupAction);
        btnCopy.setOnMouseClicked(this::btnCopyAction);
        table.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                viewData(table.getSelectionModel().getSelectedItem());
            }
        });
    }


    private void viewData(Line line) {
        if (line != null) {
            Alert alert = new Alert(null, null, ButtonType.OK);

            // Alert Window Settings
            alert.setTitle("View Data");
            alert.setHeaderText(null);
            alert.getDialogPane().setPrefSize(800, 600);

            // Add a custom icon.
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResource(Configs.icon)).toString()));

            // Content
            TextArea t1 = new TextArea(line.getText());
            t1.setEditable(false);
            t1.setPrefSize(700, 250);
            t1.setWrapText(true);

            TextArea t2 = new TextArea(line.getLines());
            t2.setEditable(false);
            t2.setPrefSize(700, 250);
            t2.setWrapText(true);

            // Bind
            VBox layout = new VBox();
            layout.getChildren().add(new Label("Text"));
            layout.getChildren().add(t1);
            layout.getChildren().add(new Label(" "));
            layout.getChildren().add(new Label("Lines"));
            layout.getChildren().add(t2);

            // Show
            alert.getDialogPane().setContent(layout);
            alert.showAndWait();
        }
    }


    private void btnCopyAction(MouseEvent mouseEvent) {
        Line l = table.getSelectionModel().getSelectedItem();
        Utils.copyToClipboard(l.getText());
    }

    private void btnRemoveDupAction(MouseEvent mouseEvent) {
        // Remove item from table
        Line l = table.getSelectionModel().getSelectedItem();
        if (l == null)
            Utils.alert("Error!", "Please Select An Item First!", "Error");
        else if (l.getAllLine().size() == 1)
            Utils.alert("Warning!!", "There are only 1 copy of this line!", "Warning");
        else {
            int tableIndex = table.getSelectionModel().getSelectedIndex();

            // Sorting line list in Desc
            l.getAllLine().sort(Comparator.reverseOrder());
            int lastIndex = l.getAllLine().size() - 1;  // last index
            int firstLineNo = l.getAllLine().get(lastIndex);
            l.getAllLine().remove(lastIndex);   // removed last

            // Remove desired lines
            removeLines(l);

            // Refresh Table View
            l.getAllLine().clear();
            l.addLine(firstLineNo);
            table.getItems().set(tableIndex, l);
        }
    }

    private void btnRemoveAllAction(MouseEvent mouseEvent) {
        // Remove item from table
        Line l = table.getSelectionModel().getSelectedItem();
        if (l != null) {
            table.getItems().remove(l);

            // Sorting line list in Desc
            l.getAllLine().sort(Comparator.reverseOrder());

            // Remove desired lines
            removeLines(l);
        } else
            Utils.alert("Error!", "Please Select An Item First!", "Error");
    }

    private void removeLines(Line l) {
        // Removing item from original list
        for (int i : l.getAllLine()) {
            original.remove(i - 1);
        }

        // Write original list
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(Main.textFile.getFile().getAbsoluteFile()));
            for (Line ln : original) {
                bw.write(ln.getText());
                bw.newLine();
            }
            bw.close();
        } catch (Exception ignored) {
        }
    }

    private void btnBackAction(MouseEvent mouseEvent) {
        Utils.changeScene(Configs.homePage);
    }

    public ObservableList<Line> getLines() {
        ObservableList<Line> allLines = FXCollections.observableArrayList();
        for (Map.Entry<String, Line> l : lines.entrySet()) {
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
        } catch (IOException ignored) {
        }
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
