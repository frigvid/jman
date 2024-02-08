module com.frigvid.jman {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.frigvid.jman to javafx.fxml;
    exports com.frigvid.jman;
}