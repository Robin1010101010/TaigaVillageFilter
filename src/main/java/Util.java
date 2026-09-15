import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Util {

    // taken from https://www.baeldung.com/java-clipboard-copy-paste-text
    public void copyToClipboard(String text) {
        System.out.println("copying seed to clipboard");
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection data = new StringSelection(text);
        cb.setContents(data, null);
    }

    public void writeToFile(String text, String fileName) {
        FileWriter fw;
        BufferedWriter bw;

        try {
            System.out.println("writing seed to file");
            fw = new FileWriter(fileName, true);
            bw = new BufferedWriter(fw);
            bw.write(text);
            bw.newLine();
            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createEmptyFile(String fileName) {
        File seedFound = new File("seedFound.txt");
        try {
            seedFound.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
