module com.i192.praktika.programavimopraktika {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.i192.praktika.programavimopraktika to javafx.fxml;
    exports com.i192.praktika.programavimopraktika;
    exports com.i192.praktika.programavimopraktika.fxml;
    opens com.i192.praktika.programavimopraktika.fxml to javafx.fxml;
}