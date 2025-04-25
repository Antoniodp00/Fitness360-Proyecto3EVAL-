/*module com.dam.adp.fitness360proyecto3eval {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.xml.bind;
    requires java.sql;

    opens com.dam.adp.fitness360proyecto3eval to javafx.fxml;
    exports com.dam.adp.fitness360proyecto3eval;
}*/

module com.dam.adp.fitness360proyecto3eval {
        requires java.xml.bind;
        requires java.sql;

        opens com.dam.adp.fitness360proyecto3eval.baseDatos to java.xml.bind;
        }
