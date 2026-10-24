package selenium;

import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // 1. Configure Chrome options for isolated headless environments
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");          // Runs without a visual GUI window layout
        options.addArguments("--no-sandbox");             // Bypasses Linux security sandbox restrictions
        options.addArguments("--disable-dev-shm-usage");  // Overrides container shared memory limits
        options.addArguments("--disable-gpu");            // Prevents hardware acceleration crashes
        options.addArguments("--window-size=1920,1080");  // Sets a standard execution screen size
        
        driver = new ChromeDriver(options);

        // 2. Set the address gateway (GitHub Actions will dynamically adjust this using sed)
        driver.get("http://web-app/login.html");
    }

    @Test(priority = 1)
    public void validLoginTest() {
        driver.findElement(By.id("username")).sendKeys("student");
        driver.findElement(By.id("password")).sendKeys("12345");
        driver.findElement(By.id("loginButton")).click();

        String message = driver.findElement(By.id("message")).getText();
        System.out.println("Message: " + message);
        Assert.assertEquals(message, "Login Successful");
    }

    @Test(priority = 2)
    public void invalidLoginTest() {
        driver.findElement(By.id("username")).sendKeys("wrong");
        driver.findElement(By.id("password")).sendKeys("wrong");
        driver.findElement(By.id("loginButton")).click();

        String message = driver.findElement(By.id("message")).getText();
        System.out.println("Message: " + message);
        Assert.assertEquals(message, "Invalid Username or Password");
    }

    @Test(priority = 3)
    public void checkBackgroundColor() {
        String bgColor = driver.findElement(By.id("loginBox"))
                               .getCssValue("background-color");
        System.out.println("Background Color: " + bgColor);
        Assert.assertEquals(bgColor, "rgba(173, 216, 230, 1)");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
