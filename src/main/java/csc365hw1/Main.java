package csc365hw1;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("/GUI.fxml"));
//        primaryStage.setTitle("Tidal Stations Data");
//        primaryStage.setScene(new Scene(root, 430, 570));
//        primaryStage.setResizable(false);


        FileDataGrabber f = new FileDataGrabber();
        List<String> ids = f.tidalStationIds();

        HashTable h = new HashTable(ids);
        h.insertHash();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
