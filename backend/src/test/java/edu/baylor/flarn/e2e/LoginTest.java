package edu.baylor.flarn.e2e;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginTest {
    private WebDriver driver;
    JavascriptExecutor js;

    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    @Ignore
    public void testAdminLogin() {
        String adminUsername = System.getenv("ADMIN_USERNAME");
        String adminPassword = System.getenv("ADMIN_PASSWORD");

        driver.get("https://flarn.netlify.com/login");
        driver.manage().window().setSize(new Dimension(1280, 680));
        driver.findElement(By.cssSelector("span:nth-child(1) .form-control")).click();
        driver.findElement(By.cssSelector("span:nth-child(1) .form-control")).sendKeys(adminUsername);
        driver.findElement(By.cssSelector("span:nth-child(2) > .form-group .form-control")).sendKeys(adminPassword);
        driver.findElement(By.cssSelector(".btn")).click();
        driver.findElement(By.linkText("Manage Users")).click();
        js.executeScript("window.scrollTo(0,100)");
        driver.findElement(By.linkText("Manage Categories")).click();
        driver.close();
    }

    @Test
    @Ignore
    public void testLoginFailed() {
        String adminUsername = System.getenv("ADMIN_USERNAME");
        String adminPassword = "wrongPass";

        driver.get("https://flarn.netlify.com/login");
        driver.manage().window().setSize(new Dimension(1280, 680));
        driver.findElement(By.cssSelector("span:nth-child(1) .form-control")).click();
        driver.findElement(By.cssSelector("span:nth-child(1) .form-control")).sendKeys(adminUsername);
        driver.findElement(By.cssSelector("span:nth-child(2) > .form-group .form-control")).sendKeys(adminPassword);
        driver.findElement(By.cssSelector(".btn")).click();
        driver.findElement(By.cssSelector(".alert-inner--text")).getText().equals("Invalid username and password");
        driver.close();
    }
}
