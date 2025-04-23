module com.dam.adp.fitness360proyecto3eval {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.dam.adp.fitness360proyecto3eval to javafx.fxml;
    exports com.dam.adp.fitness360proyecto3eval;
}