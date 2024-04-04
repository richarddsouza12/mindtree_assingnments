package org.example.Tests;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import java.util.List;

public class TestUrls {

    public final int LIMIT = 10;
    public final String WEBSITE_URL = "https://www.flipkart.com";

    @Test
    public void TestUrlsOnFlipkartPage() {

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("headless");
        WebDriver driver = new ChromeDriver( chromeOptions );
        driver.get( WEBSITE_URL );

        //print total links found
        List<WebElement> listAWebElement =  driver.findElements( By.tagName("a") );
        System.out.println( "Total <a href> links found : " +  listAWebElement.size() );

        // *** limit the processing of all links for simplicity
        listAWebElement = listAWebElement.subList( 0 , LIMIT );

        //using foreach loop
        System.out.println( "Printing first " + LIMIT + " elements of <a> using foreach loop :" );
        for( WebElement aWebElement : listAWebElement ) {
            System.out.println( aWebElement.getAttribute("href") );
        }

        //using streams with lambda expression
        System.out.println( "Printing first " + LIMIT + " elements of <a> using streams with lambda expression" );
        listAWebElement.stream().forEach( ( element  ) -> {
            System.out.println( element.getAttribute("href") );
        } );

        //parallel streams with lambda expression
        System.out.println( "Printing first "  + LIMIT + " elements of <a> using parallel streams with lambda expression" );
        listAWebElement.parallelStream().forEach( ( element ) -> {
            System.out.println( element.getAttribute("href") );
        } );

    }


}
