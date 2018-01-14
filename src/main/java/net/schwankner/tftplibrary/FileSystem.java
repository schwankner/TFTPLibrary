package net.schwankner.tftplibrary;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.*;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Alexander Schwankner on 13.01.18.
 */
public class FileSystem {

    public static byte[] readFileToBin(String filename) {
        try {
            return IOUtils.toByteArray(new java.io.FileInputStream(filename));

        } catch (FileNotFoundException e) {
            System.out.println("InputFile not found! " + filename);
            System.exit(1);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }
}
