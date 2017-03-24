package pl.com.bottega.dms.application;

import pl.com.bottega.dms.infrastructure.CesarInputStream;
import pl.com.bottega.dms.infrastructure.CesarOutputStream;

import java.io.*;

/**
 * Created by maciek on 18.03.2017.
 */
public class IOCesarCryptApp {
    public static void main(String[] args) throws IOException {
        String fileInputPath = "c:\\tmp\\cesarInput.txt";
        String fileOutputCryptPath = "c:\\tmp\\cesarOutputCrypt.txt";
        String fileOutputDecryptPath = "c:\\tmp\\cesarOutputDecrypt.txt";
        int key = 3;

        int data;
        try {
            InputStream inputStreamToCrypt = new CesarInputStream(new FileInputStream(fileInputPath), key);
            OutputStream outputStreamCrypt = new FileOutputStream(fileOutputCryptPath);
            while ((data = inputStreamToCrypt.read()) != -1) {
                outputStreamCrypt.write(data);
            }
            inputStreamToCrypt.close();
            outputStreamCrypt.close();
            InputStream inputStreamToDecrypt = new FileInputStream(fileOutputCryptPath);
            OutputStream outputStreamDecrypt = new CesarOutputStream(new FileOutputStream(fileOutputDecryptPath), key);
            while ((data = inputStreamToDecrypt.read()) != -1) {
                outputStreamDecrypt.write(data);
            }

            inputStreamToDecrypt.close();
            outputStreamDecrypt.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
