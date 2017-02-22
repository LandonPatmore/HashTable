package csc365hw1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by landon on 2/21/17.
 */
public class FileDataGrabber {
    private static File F;
    private static List<String> IDS;
    private static String line;

    public FileDataGrabber() {
        F = new File("Active_Tidal_Stations.txt"); //grabs the tidal station file
        IDS = new ArrayList<String>(); //
        line = "";
    }

    public List<String> tidalStationIds() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(F)); //wraps a bufferreader around a filereader to buffer the input

        while((line = bufferedReader.readLine()) != null) {
            IDS.add(line);
        }

        bufferedReader.close();
        return IDS;
    }


}
