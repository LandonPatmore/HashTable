package csc365hw1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by landon on 2/21/17.
 */
public class PythonRunner {

    public static void pythonRunner() throws IOException {
        // set up the command and parameter
        String pythonScriptPath = "/Users/landon/Desktop/365HW1/crawler.py";
        String[] cmd = new String[2];
        cmd[0] = "python"; // check version of installed python: python -V
        cmd[1] = pythonScriptPath;

        // create runtime to execute external command
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(cmd);

        // retrieve output from python script
        BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line;
        while((line = bfr.readLine()) != null) {
        // display each output line form python script
            System.out.println(line);
        }
    }
}
