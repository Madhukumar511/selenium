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
        // 1. Configure Chrome options for the headless Linux container environment
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");          // Runs without a visual GUI window
        options.addArguments("--no-sandbox");             // Bypasses Linux sandbox restrictions
        options.addArguments("--disable-dev-shm-usage");  // Overrides container memory limits
        options.addArguments("--disable-gpu");            // Prevents hardware acceleration errors
        options.addArguments("--window-size=1920,1080");  // Sets standard execution screen size
        
        driver = new ChromeDriver(options);

        // 2. Point Selenium to the live 'web-app' container hosted by Docker Compose
        driver.get("http://web-app/login.html");
    }

    @Test(priority = 1)
    public void validLoginTest() {
        // Fill out valid student credentials
        driver.findElement(By.id("username")).sendKeys("student");
        driver.findElement(By.id("password")).sendKeys("12345");
        driver.findElement(By.id("loginButton")).click();

        // Assert that the success message displays accurately
        String message = driver.findElement(By.id("message")).getText();
        System.out.println("Message: " + message);
        Assert.assertEquals(message, "Login Successful");
    }

    @Test(priority = 2)
    public void invalidLoginTest() {
        // Fill out incorrect credentials to test form security validation
        driver.findElement(By.id("username")).sendKeys("wrong");
        driver.findElement(By.id("password")).sendKeys("wrong");
        driver.findElement(By.id("loginButton")).click();

        // Assert that the proper error message displays
        String message = driver.findElement(By.id("message")).getText();
        System.out.println("Message: " + message);
        Assert.assertEquals(message, "Invalid Username or Password");
    }

    @Test(priority = 3)
    public void checkBackgroundColor() {
        // Capture and verify the UI element box's CSS layout styling
        String bgColor = driver.findElement(By.id("loginBox"))
                               .getCssValue("background-color");
        System.out.println("Background Color: " + bgColor);
        
        // Verifies the lightblue property mapping converts correctly
        Assert.assertEquals(bgColor, "rgba(173, 216, 230, 1)");
    }

    @AfterMethod
    public void tearDown() {
        // 3. Close the browser session cleanly after each test case completes
        if (driver != null) {
            driver.quit();
        }
    }
}
