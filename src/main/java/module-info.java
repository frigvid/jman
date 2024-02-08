module com.frigvid.jman {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens com.frigvid.jman to javafx.fxml;
    exports com.frigvid.jman;
    
    exports com.frigvid.jman.view;
    opens com.frigvid.jman.view;
}