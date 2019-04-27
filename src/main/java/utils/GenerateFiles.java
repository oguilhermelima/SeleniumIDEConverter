package utils;

import files.Model;
import models.Project;
import models.Test;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GenerateFiles {
    /**
     *  @param recursion True if the method called itself;
     * */
    public static void generateJavaFiles(boolean recursion) {

        try {
            // Check if exists .side file in folder
            boolean existSideFile = false;
            // Instantiate the File Chooser
            JFileChooser chooser = new JFileChooser();
            // Define Multiple Selection = true
            chooser.setMultiSelectionEnabled(true);
            // Accept only .side o .json files. Description in English - "Selenium IDE files"
            chooser.setFileFilter(new FileNameExtensionFilter("Arquivos do Selenium IDE", "side", "json"));
            // If the method called itself
            if (!recursion)
                // Show help dialog - English: "Please, select the Selenium IDE Tests"
                JOptionPane.showMessageDialog(null, "Por favor, selecione os testes do Selenium IDE");
            // Open the dialog and returns a success/error integer (0 to success, -1 to error)
            int findFolder = chooser.showSaveDialog(chooser);
            // Will receive the tests files
            File[] files = null;
            // If the returned integer is equals 1 (success)
            if (findFolder == JFileChooser.APPROVE_OPTION)
                // Receive all files from folder
                files = chooser.getSelectedFiles();
            // If folder is empty
            if (files != null && files.length > 0) {
                // @code will receive the final converted code and @className the test name
                String code, className;
                // Store the Maps
                List<Map<String,String>> finalClasses = new ArrayList<>();
                // For each file in folder
                for (File file : files) {
                    // If element ends with .side (Selenium IDE file extension)
                    if (file.getPath().toLowerCase().endsWith(".side")) {
                        // Set true the existence of .size file in folder
                        existSideFile = true;
                        // Convert the .side file to Project type (Project.class)
                        @SuppressWarnings("unchecked")
                        List<Project> projects = FilesUtils.convertJsonList(file.getPath(), Project.class);
                        // If projects are not empty
                        if (projects != null && !projects.isEmpty()) {
                            // For each project found
                            for (Project project : projects) {
                                // For each test inside project
                                for (Test test : project.getTests()) {
                                    // className = test name from project
                                    // Encode the String to UTF-8
                                    className = new String(test.getName().getBytes(), StandardCharsets.UTF_8);
                                    // Remove all special characters from the name
                                    className = Normalizer.normalize(className, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
                                    // Set the first letter uppercase and the other to lowercase and remove spaces
                                    className = (className.substring(0, 1)).toUpperCase() + (className.substring(1)).toLowerCase().trim().replace(" ", "");
                                    // Receive the converted code
                                    code = SeleniumConverter.generateJavaCode(test, project.getUrl());
                                    // Replace the specific parts in the model with the final code
                                    code = Model.getModel().replace("CLASSNAME", className).replace("SELENIUMCODE", code);
                                    // This Map will receive all class name and their respective converted tests
                                    Map<String, String> classes = new HashMap<>();
                                    // Add the code in the final list
                                    classes.put(className, code);
                                    // Add the map in list
                                    finalClasses.add(classes);
                                }
                            }
                        }
                    }
                }
                // Show help dialog - English: "Please, select the folder that will store the Java files"
                JOptionPane.showMessageDialog(null, "Por favor, selecione o diretório no qual serão salvos os arquivos Java");
                // Set Selection Mode = Directory only
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                // Accept all
                chooser.setFileFilter(null);
                // Open the dialog and returns a success/error integer (0 to success, -1 to error)
                findFolder = chooser.showSaveDialog(chooser);
                // If the returned integer is equals 1 (success) and the method received side files
                if (findFolder == JFileChooser.APPROVE_OPTION && existSideFile) {
                    // Receive the selected folder
                    File folder = chooser.getSelectedFile();
                    // For each Map in List, generate the .java file. The method receive the class name and the java code
                    finalClasses.forEach(classCode -> FilesUtils.generateJavaFile(classCode.values().toString(), folder.getPath() + "\\", classCode.keySet().toString()));
                    // Show success message.English: "Java Files generated with success"
                    JOptionPane.showMessageDialog(null, "Arquivos Java gerados com sucesso");
                    // Open the window with the Java files
                    Desktop.getDesktop().open(new File(folder.getPath()));
                }
            }
            // If .side not found
            if (!existSideFile && findFolder != JFileChooser.CANCEL_OPTION){
                // Show error dialog and give for the user an option to find other folder.
                // English - "No test file found, do you want to select another folder?"
                int option = JOptionPane.showConfirmDialog(null, "Nenhum teste encontrado, deseja selecionar uma nova pasta?", "", JOptionPane.YES_NO_OPTION);
                // If user has pressed the YES button
                if (option == 0)
                    // Call the code again
                    generateJavaFiles(true);
            }
        } catch(Exception e){
            // Will receive the error in String format
            StringWriter errors = new StringWriter();
            // Convert StackTrace to String
            e.printStackTrace(new PrintWriter(errors));
            // Show message dialog with error
            JOptionPane.showMessageDialog(null, "Sorry, this error ocurred in execution: \n" + errors.toString());
            // Print error message
            e.printStackTrace();
        }


    }
}
