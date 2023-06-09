/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sptv21clickme;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author user
 */
public class FXMLClickMeController implements Initializable {
    private int level=1;
    private String textLineClicked="";
    private double delay = 1000;
    private Timeline timeline;
    private int clickedToWindow;
    @FXML
    private Label lbLevel;
    @FXML
    private Button btnClickMe;
    @FXML
    private Label lbClickedToWindows;
//    @FXML
//    private AnchorPane anchorPane;
    @FXML
    private AnchorPane anchorPane;
    private Scene scene;
    
    @FXML
    private void handleMouseClick(MouseEvent event) {
        if (event.getTarget() instanceof Button || timeline.getStatus().equals(Timeline.Status.STOPPED)) {
            return; // Ignore clicks on the button
        }
      clickedToWindow++;
      lbClickedToWindows.setText(textLineClicked + level + ": " + clickedToWindow); 
    }
    
    @FXML
    private void clickButtonAction(ActionEvent event) {
        if(timeline.getStatus().equals(Timeline.Status.RUNNING)){
            timeline.stop();
            btnClickMe.setText("Continue");
            textLineClicked=lbClickedToWindows.getText();
            if(delay < 0){
                showMessage();
                delay = 1000;
                level = 0;
                lbClickedToWindows.setText("");
                textLineClicked = "";
            }
        }else{
            level++;
            delay = delay-100;
            lbLevel.setText("Level "+level);
            timeline.getKeyFrames().clear();
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(delay), e -> changeButtonPosition()));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
            btnClickMe.setText("Click Me!");
            textLineClicked += "\n";
            clickedToWindow=0;
            double red = Math.random()* 0.5 + 0.5;
            double green = Math.random()* 0.5 + 0.5;
            double blue = Math.random()* 0.5 + 0.5;
            Color randomColor = new Color(red, green, blue, 1); 
            anchorPane.setStyle("-fx-background-color: " + toRGBCode(randomColor) + ";");

        }
    }
    private String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
    private void showMessage() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Ты достиг совершенства!");
        alert.setHeaderText(null);
        alert.setContentText("Результат твоего последнего подвига "+ clickedToWindow+" кликов");

        // Устанавливаем родительское окно для модального окна
        Stage stage = (Stage) btnClickMe.getScene().getWindow();
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(stage);

        alert.showAndWait();
    }
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        scene = anchorPane.getScene();
        timeline = new Timeline(new KeyFrame(Duration.millis(delay), event -> changeButtonPosition()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    @FXML
    private void changeButtonPosition() {
        Random random = new Random();
        double buttonWidth = btnClickMe.getWidth();
        double buttonHeight = btnClickMe.getHeight();
        double sceneWidth = ((AnchorPane) btnClickMe.getParent()).getWidth() - buttonWidth;
        double sceneHeight = ((AnchorPane) btnClickMe.getParent()).getHeight() - buttonHeight;
        double newX = random.nextDouble() * sceneWidth;
        double newY = random.nextDouble() * sceneHeight;
        btnClickMe.setLayoutX(newX);
        btnClickMe.setLayoutY(newY);
    }
}
