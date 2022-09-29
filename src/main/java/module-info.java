module duplicate.line.finder {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports dev.pages.ahsan40.dlf.main;
    opens dev.pages.ahsan40.dlf.main to javafx.fxml;
    exports dev.pages.ahsan40.dlf.controller;
    opens dev.pages.ahsan40.dlf.controller to javafx.fxml;
    exports dev.pages.ahsan40.dlf.utils;
    opens dev.pages.ahsan40.dlf.utils to javafx.fxml;
}