package net.schwankner.tftplibrary;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Alexander Schwankner on 13.01.18.
 */
public class FileSystem {

    public static byte[] readFileToBlob(String filename) {
        try {
            return IOUtils.toByteArray(new java.io.FileInputStream(filename));

        } catch (FileNotFoundException e) {
            System.out.println("InputFile not found! " + filename);
            //@todo: throw execption and send error message
        } catch (IOException e) {
            System.out.println(e.getMessage());
            //@todo: throw execption and send error message
        }
        return null;
    }

    public static void writeBlobToFile(byte[] blob,String filename){

        try {
            FileUtils.writeByteArrayToFile(new File(filename), blob);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        System.out.println("File "+filename+" written with "+blob.length+" bytes");
    }
}
