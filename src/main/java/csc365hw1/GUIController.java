package csc365hw1;

import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;


public class GUIController {
    private ArrayList<KeyVal> d;
    private ObservableList obList;
    private DataPuller dp;
    private HashTable ht;

    @FXML
    private Label gLabel;

    @FXML
    private ComboBox<KeyVal> listBox;

    @FXML
    private ListView<KeyVal> listView;


    public void initialize(){
        dp = new DataPuller();
        ht = new HashTable();
    }

    @FXML
    void handleButtonActionGetData() throws UnirestException {
        d = dp.getSentences();

        for(KeyVal dh : d){
            ht.put(dh);
        }

        gLabel.setText("Done getting data and hashing!");

        ObservableList<KeyVal> obList = FXCollections.observableList(d);

        listBox.setItems(obList);
        listBox.getSelectionModel().selectFirst();
    }

    @FXML
    void handleButtonActionSubmit(){
        String output = listBox.getSelectionModel().getSelectedItem().toString();
        System.out.println(output);

        ObservableList<KeyVal> items =FXCollections.observableArrayList (ht.similarity(output));

        listView.setItems(items);

    }

}