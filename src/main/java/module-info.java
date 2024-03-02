module com.frigvid.jman {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens com.frigvid.jman to javafx.fxml;
	exports com.frigvid.jman;
	
	exports com.frigvid.jman.view;
	opens com.frigvid.jman.view;
	exports com.frigvid.jman.view.state;
	opens com.frigvid.jman.view.state;
}
