module com.dam.adp.fitness360proyecto3eval {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;
    requires jbcrypt;

    opens com.dam.adp.fitness360proyecto3eval.baseDatos to java.xml.bind;
    opens com.dam.adp.fitness360proyecto3eval.controllers to javafx.fxml;
    exports com.dam.adp.fitness360proyecto3eval.controllers;
    exports com.dam.adp.fitness360proyecto3eval.views;
}
