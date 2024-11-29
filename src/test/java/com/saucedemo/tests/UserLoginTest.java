package com.saucedemo.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.nio.file.Paths;
import java.time.Duration;

public class UserLoginTest {
    public static void main(String[] args) {
        // Set the path to the ChromeDriver
        String chromeDriverPath = Paths.get("src", "main", "resources", "drivers", "chromedriver.exe").toAbsolutePath().toString();
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        try {
            // Navigate to the login page
            driver.get("https://www.saucedemo.com");

            // Enter username and password
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");

            // Click the login button
            driver.findElement(By.id("login-button")).click();

            // Explicit wait to verify redirection to the home page
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement productsHeader = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.className("title"))
            );

            // Verify if the header text matches "Products"
            if (productsHeader.getText().equals("Products")) {
                System.out.println("Login successful and user is redirected to the home page.");
            } else {
                System.out.println("Login failed or redirection did not occur.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
