package csc365hw1;

import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class GUIController {
    private DataPuller dp;

    @FXML
    private Label gLabel;

    @FXML
    void handleButtonAction(ActionListener event) throws UnirestException {
            gLabel.setText("Getting Data and Hashing...");
            dp = new DataPuller();
            if(dp.getSentences()){
                gLabel.setText("Data pulled and hashed!");
            } else {
                gLabel.setText("Error with connection.");
            }

    }

}