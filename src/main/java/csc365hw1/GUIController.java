//package csc365hw1;
//
//import com.mashape.unirest.http.exceptions.UnirestException;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//
//import java.util.ArrayLoist;
//
//
//public class GUIController {
//    private ArrayList<KeyVal> d;
//    private ObservableList obList;
//    private DataPuller dp;
//    private HashTable ht;
//
//    @FXML
//    private Label gLabel;
//
//    @FXML
//    private ComboBox<KeyVal> listBox;
//
//    @FXML
//    private Button submitButton;
//
//    public void initialize(){
//        dp = new DataPuller();
//        ht = new HashTable();
//    }
//
//    @FXML
//    void handleButtonActionGetData() throws UnirestException {
//        d = dp.getSentences();
//
//        for(KeyVal dh : d){
//            ht.put(dh);
//        }
//
//        gLabel.setText("Data has been pulled and hashed!");
//
//        ObservableList obList = FXCollections.observableList(d);
//
//        listBox.setItems(obList);
//        listBox.getSelectionModel().selectFirst();
//        System.out.println(ht.similarity("2008-09-10"));
//    }
//
//}