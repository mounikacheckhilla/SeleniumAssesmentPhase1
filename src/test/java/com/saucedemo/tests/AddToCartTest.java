package com.saucedemo.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Paths;
import java.time.Duration;

public class AddToCartTest {

    public static void main(String[] args) {

        // Set the path to the ChromeDriver
        String chromeDriverPath = Paths.get("src", "main", "resources", "drivers", "chromedriver.exe").toAbsolutePath().toString();
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();


        try {
            // Login to the page
            driver.get("https://www.saucedemo.com/");
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();

            //Use WebDriverWait to wait for the visibility of the "Add to Cart" button
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement productButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[text()='Add to cart'])[1]")));
            productButton.click();

            //Go to the shopping cart page
            WebElement cartIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("shopping_cart_link")));
            cartIcon.click();

            //Verify that the selected product is in the cart
            WebElement cartProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='cart_item']")));
            if (cartProduct != null) {
                System.out.println("Test Passed: Product is added to the cart.");
            } else {
                System.out.println("Test Failed: Product not found in the cart.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
