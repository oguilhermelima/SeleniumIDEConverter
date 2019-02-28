package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *   Read and Create JSON, .SIDE an JAVA files
 */
public class FilesUtils {

    // Instantiate the GSON object
    private static Gson gson = new GsonBuilder().create();
    /**
     * Read a JSON/SIDE file and convert to a specified object type
     * @param path Receive the path where JSON is stored
     * @param expectedClass Receive the class type that JSON will be converted
     */

    public static ArrayList convertJsonList(String path, Class expectedClass){
        try {
            // Read the file and convert to String
            String file = new String(Files.readAllBytes(Paths.get(path)));
            // Tranform the String in an Array
            if (!file.startsWith("["))
                file = "[" + file;
            if (!file.endsWith("]"))
                file += "]";
            // Transform the JSON text in the Expected Class type
            return gson.fromJson(file, TypeToken.getParameterized(ArrayList.class, expectedClass).getType());
        } catch (IOException io){
            // WIll receive the error in String format
            StringWriter errors = new StringWriter();
            // Convert StackTrace to String
            io.printStackTrace(new PrintWriter(errors));
            // Show message dialog with error
            JOptionPane.showMessageDialog(null, "Sorry, this error ocurred in execution: \n" + errors.toString());
        }
        return null;
    }

    /**
     * Write text inside a Java File
     * @param path Directory that file will be stored
     * @param code Converted Java code;
     * @param fileName Class Name
     */
    public static void generateJavaFile(String code, String path, String fileName){
        try {
            // Remove all special characters from the name and code
            fileName = fileName.replace("[", "").replace("]", "");
            code = code.replace("[", "").replace("]", "");
            // Generate a .java file
            PrintWriter writer = new PrintWriter(path + fileName + ".java");
            // Write the code in file
            writer.write(code);
            // Close the writer
            writer.close();
        } catch (IOException io){
            // WIll receive the error in String format
            StringWriter errors = new StringWriter();
            // Convert StackTrace to String
            io.printStackTrace(new PrintWriter(errors));
            // Show message dialog with error
            JOptionPane.showMessageDialog(null, "Sorry, this error ocurred in execution: \n" + errors.toString());
        }
    }

}