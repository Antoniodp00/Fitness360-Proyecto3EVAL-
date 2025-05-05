# Fitness360 Project

## JavaFX Structure

This project uses JavaFX for its user interface. The following folder structure has been set up for organizing JavaFX resources:

### Source Code Structure

- `src/main/java/com/dam/adp/fitness360proyecto3eval/controllers/` - Contains JavaFX controller classes
- `src/main/java/com/dam/adp/fitness360proyecto3eval/views/` - Contains JavaFX view classes and application entry points

### Resources Structure

- `src/main/resources/com/dam/adp/fitness360proyecto3eval/fxml/` - Contains FXML layout files
- `src/main/resources/com/dam/adp/fitness360proyecto3eval/css/` - Contains CSS style files
- `src/main/resources/com/dam/adp/fitness360proyecto3eval/images/` - Contains image resources

## How to Use

### Loading FXML Files

To load an FXML file in your code:

```java
FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/fxml/your-file.fxml"));
Parent root = loader.load();
```

### Applying CSS Styles

To apply CSS styles to your FXML:

1. In FXML file:
```xml
<VBox stylesheets="@../css/styles.css">
    <!-- Content -->
</VBox>
```

2. In Java code:
```java
scene.getStylesheets().add(getClass().getResource("/com/dam/adp/fitness360proyecto3eval/css/styles.css").toExternalForm());
```

### Using Images

To use images in your application:

```java
Image image = new Image(getClass().getResourceAsStream("/com/dam/adp/fitness360proyecto3eval/images/your-image.png"));
```

## Running the Application

The main application class is `com.dam.adp.fitness360proyecto3eval.views.MainApplication`. Run this class to start the application.