package csc365hw1;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        DataPuller dp = new DataPuller();
        HashTable h = new HashTable(dp.gatherData());
        h.insertHash();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
