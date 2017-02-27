package csc365hw1;

import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

/**
 * Controller for the GUI
 */

public class GUIController {
    private ArrayList<KeyVal> d;
    private DataPuller dp;
    private HashTable ht;

    @FXML
    private Label gLabel;

    @FXML
    private ComboBox<KeyVal> listBox;

    @FXML
    private ListView<KeyVal> listView;

    /**
     * Initializes the DataPuller and HashTable
     */

    public void initialize(){
        dp = new DataPuller();
        ht = new HashTable();
    }

    /**
     * Handles a button action to get the data from the stock server and also hashes it
     * @throws UnirestException in the case that Unirest can't reach the server for any reason so the app does not crash
     */

    @FXML
    void handleButtonActionGetData() throws UnirestException {
        d = dp.getStockData();

        for(KeyVal dh : d){
            ht.put(dh);
        }

        gLabel.setText("Done getting data and hashing!");

        ObservableList<KeyVal> obList = FXCollections.observableList(d);

        listBox.setItems(obList);
        listBox.getSelectionModel().selectFirst();
    }

    /**
     * Handles a button action to get the currently selected Key from the ComboBox and then shows the similarity
     * metrics of the closest related stocks in the ListView
     */

    @FXML
    void handleButtonActionSubmit(){
        String output = listBox.getSelectionModel().getSelectedItem().toString();

        ObservableList<KeyVal> items =FXCollections.observableArrayList (ht.similarity(output));

        listView.setItems(items);

    }

}