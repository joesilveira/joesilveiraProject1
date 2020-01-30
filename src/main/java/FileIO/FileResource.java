/*
Joe Silveira

Class to handle file i/o
 */

package FileIO;


import javax.swing.*;

public class FileResource {

    //Class Variables

    public void createNewFile() {

        JFrame frame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select file to save!");

        fileChooser.showSaveDialog(frame);

    }

}
