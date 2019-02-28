package files;

/**
 * Contain the Java base code for the convertion
 * */
public class Model {

    public static String getModel(){
        return  "import org.junit.After;\n" +
                "import org.junit.Assert;\n" +
                "import org.junit.Before;\n" +
                "import org.junit.Test;\n" +
                "import org.openqa.selenium.By;\n" +
                "import org.openqa.selenium.Keys;\n" +
                "import org.openqa.selenium.WebDriver;\n" +
                "import org.openqa.selenium.chrome.ChromeDriver;\n" +
                "import org.openqa.selenium.chrome.ChromeOptions;\n" +
                "\n" +
                "public class CLASSNAME {\n" +
                "\n" +
                "    private WebDriver driver;\n" +
                "\n" +
                "    @Before\n" +
                "    public void start() {\n" +
                "        // Chrome driver\n" +
                "        System.setProperty(\"webdriver.chrome.driver\", \"C:\\\\chromedriver.exe\");\n" +
                "        // Options to Chrome\n" +
                "        ChromeOptions options = new ChromeOptions();\n" +
                "        // Maximize windows\n" +
                "        options.addArguments(\"--start-maximized\");\n" +
                "        // New Chrome Instance\n" +
                "        driver = new ChromeDriver(options);\n" +
                "    }\n" +
                "\n" +
                "    @Test\n" +
                "    public void runCode() throws InterruptedException {\n" +
                "\n" +
                "        SELENIUMCODE\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    @After\n" +
                "    public void end() {\n" +
                "    \tdriver.close();\n" +
                "    }\n" +
                "}";
    }
}
