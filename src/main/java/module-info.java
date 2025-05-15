module com.dam.adp.fitness360proyecto3eval {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;
    requires jbcrypt;
    requires org.slf4j;
    requires ch.qos.logback.classic;

    opens com.dam.adp.fitness360proyecto3eval.baseDatos to java.xml.bind;
    opens com.dam.adp.fitness360proyecto3eval.controller to javafx.fxml;

    exports com.dam.adp.fitness360proyecto3eval.controller;
    exports com.dam.adp.fitness360proyecto3eval.views;
    exports com.dam.adp.fitness360proyecto3eval.model;
    exports com.dam.adp.fitness360proyecto3eval.DAO;
    exports com.dam.adp.fitness360proyecto3eval.utilidades;
}
