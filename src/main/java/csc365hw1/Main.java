package csc365hw1;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        DataPuller dp = new DataPuller();
        dp.getSentences();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
