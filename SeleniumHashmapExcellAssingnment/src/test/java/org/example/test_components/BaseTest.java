package org.example.test_components;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    public WebDriver driver;

    public void initializeWebDriver() throws IOException {

        Properties properties = new Properties();
        String browser_name;
        boolean boolIsHeadlessMode;

        FileInputStream is = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/GolbalData.properties");
        properties.load( is );

        //check override terminal -D params over GolbalData.properties file.
        boolIsHeadlessMode =  System.getProperty("isHeadless") != null
                ? System.getProperty("isHeadless").equals("true")
                : properties.getProperty("isHeadless").equals("true");

        browser_name       = ( System.getProperty("browser") != null )
                ? System.getProperty("browser") : properties.getProperty("browser");


        if( browser_name.equals("chrome") ) {

            /* not needed since selenium v4 onwards */
           /* WebDriverManager.chromedriver().setup();*/

            if( boolIsHeadlessMode ) {

                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("headless");
                this.driver = new ChromeDriver( chromeOptions );
                this.driver.manage().window().setSize( new Dimension( 1440,900 ) );

            } else {

                this.driver = new ChromeDriver();
            }

        }
        else if ( browser_name.equals("firefox") ) {

            /* not needed since selenium v4 onwards */
            /* WebDriverManager.chromedriver().setup();*/
            this.driver = new FirefoxDriver();
        }

        //global implict wait to manage page load elment poling.
        this.driver.manage().window().maximize();
        this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS );

    }


}
