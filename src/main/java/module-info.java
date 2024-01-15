module com.i192.praktika.programavimopraktika {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires com.almasb.fxgl.all;
    requires jinput;

    opens com.i192.praktika.programavimopraktika to javafx.fxml;
    exports com.i192.praktika.programavimopraktika;
    exports com.i192.praktika.programavimopraktika.fxml;
    opens com.i192.praktika.programavimopraktika.fxml to javafx.fxml;
    exports com.i192.praktika.programavimopraktika.spritesheet;
    opens com.i192.praktika.programavimopraktika.spritesheet to javafx.fxml;
}
