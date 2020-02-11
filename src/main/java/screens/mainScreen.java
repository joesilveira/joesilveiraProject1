package screens;

import javax.swing.*;
import java.awt.*;

public class mainScreen {

    //Joe Silveira
    //Initializes a progress bar and jframe parameters
    public void initProgressBar(JProgressBar progressBar, JFrame frame) {
        //Progress bar init
        progressBar.setStringPainted(true);
        progressBar.setBorderPainted(true);
        progressBar.setForeground(Color.green.darker());

        //frame init
        frame.setSize(500, 200);
        frame.setTitle("Fetching Jobs...");
        frame.add(progressBar);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
