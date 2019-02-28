package utils;

import models.Test;
import java.util.List;
/**
 * Receive an Object (converted from JSON) and convert and create a code with Selenium structure
 * */
public class SeleniumConverter {

    /**
     * Generate Java code from a Selenium IDE code
     * @param test Receive all data from test
     * @param url Receive the url that test will run
     * */
    public static String generateJavaCode(Test test, String url){

        /* Will receive:
            @ target: represents tags that are used to identify some html code. Eg. class, id or name from a div;
            @ command: the action that Selenium will run;
            @ value: (when needed)
        */
        String target, command, value;
        // Will receive the converted Selenium code
        StringBuilder result = new StringBuilder();
        // Contains alternative command targets
        List<List<String>> targets;

        // Loop for each test
        for (int j = 0; j < test.getCommands().size(); j++) {
            // Receive command from Test stored in Selenium Project
            command = test.getCommands().get(j).getCommand().toLowerCase();
            // Receive the command target
            target =  test.getCommands().get(j).getTarget().split(":j_idt")[0];
            // Receive alternative command targets
            targets = test.getCommands().get(j).getTargets();
            // Find the best target option and add other tags
            target = refactorTarget(targets, target);
            // Receive the value
            value =  test.getCommands().get(j).getValue();

            switch (command) {
                // OPEN COMMAND - USED TO OPEN SOME SPECIFIC URL
                case "open":
                    result.append("driver.get(\"").append(url).append("\");\n");
                    break;
                // CLICK COMMAND - USED TO SIMULATE CLICK INTERACTION IN HTML ELEMENTS
                case "click":
                    result.append("\t\tdriver.findElement").append(target).append(".click();\n");
                    break;
                /*
                 * PAUSE COMMAND - USED TO MAKE THE CODE SLEEP SOME TIME (PAUSE)
                 * NORMALLY USED TO MAKE SELENIUM WAIT SOME TIME TO FIND SOME ELEMENT
                 */
                case "pause":
                    result.append("\t\tThread.sleep(").append(target).append(");\n");
                    break;
                /*
                 * SEND KEYS OR TYPE COMMAND - USED TO SEND DATA TO SOME INPUT
                 * KEYS ESC AND ENTER ARE CONVERTED TO A STRING VALUE AND ARE CONSIDERED SEND KEYS EITHER
                 */
                case "type":
                case "sendkeys":
                    // IN "ENTER" KEY PRESS
                    if (value.equals("${KEY_ENTER}"))
                        result.append("\t\tdriver.findElement").append(target).append(".sendKeys(Keys.ENTER);\n");
                        // IN "ESC" KEY PRESS
                    else if (value.equals("${KEY_RETURN}"))
                        result.append("\t\tdriver.findElement").append(target).append(".sendKeys(Keys.RETURN);\n");
                        // IF OTHER KEYS
                    else
                        result.append("\t\tdriver.findElement").append(target).append(".sendKeys(\"").append(value).append("\");\n");
                    break;
                    /*
                     * ASSERT TEXT - VERIFY IF THE RECEIVED TEXT IS EQUAL TO SOME ITEM IN THE PAGE
                     */
                case "assertText":
                    result.append("\t\tdriver.getPageSource().contains(\"").append(target).append("\");\n");
                    break;
            }
        }

        return result.toString();
    }

    private static String refactorTarget(List<List<String>> targets, String target) {
        // For each alternative target
        for (List listTarget : targets) {
            // Replace the target for the new XPATH target (more accurate) if exists
            if (listTarget.get(1).toString().contains("idRelative"))
                target = listTarget.get(0).toString();
        }

        // If target type is = XPATH
        if (target.toLowerCase().contains("xpath"))
            // Add the class By, close the method and replace the symbol =/ with (".
            return "(By." + target.replace("=/", "(\"") + "\")";

        // If target type is = CSS
        else if (target.toLowerCase().contains("css"))
            // Add the class By, close the method and replace the symbol = por (" and replace the word css with cssSelector
            return "(By." + target.replace("=", "(\"").replace("css", "cssSelector") + "\")";

        // If target type is = ID
        else if (target.toLowerCase().contains("id") && !target.contains("xpath"))
            // Add the class By, close the method and replace the symbol = with (".
            return "(By." + target.replace("=", "(\"") + "\")";
        return target;
    }
}

