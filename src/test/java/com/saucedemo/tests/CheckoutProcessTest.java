package com.saucedemo.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Paths;
import java.time.Duration;

public class CheckoutProcessTest {
    public static void main(String[] args) {
        // Set the path to the ChromeDriver executable within the project
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

            //Verify that the cart page is displayed
            WebElement cartPageHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title")));
            if (!cartPageHeader.getText().equals("Your Cart")) {
                System.out.println("Test Failed: Unable to navigate to the cart page.");
                return;
            }

            //Verify that the cart contains items
            WebElement cartItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_item")));
            if (cartItem == null) {
                System.out.println("Test Failed: Cart is empty.");
                return;
            }

            //Click the "Checkout" button
            WebElement checkoutButton = driver.findElement(By.id("checkout"));
            checkoutButton.click();

            //Enter valid shipping and payment information
            WebElement firstNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
            firstNameField.sendKeys("Daniel");

            WebElement lastNameField = driver.findElement(By.id("last-name"));
            lastNameField.sendKeys("Mclean");

            WebElement postalCodeField = driver.findElement(By.id("postal-code"));
            postalCodeField.sendKeys("55443");

            WebElement continueButton = driver.findElement(By.id("continue"));
            continueButton.click();

            // Step 6: Complete the checkout process
            WebElement finishButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("finish")));
            finishButton.click();

            // Step 7: Verify that the order is successfully placed
            WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(., 'Thank you for your order!')]")));
            if (confirmationMessage.isDisplayed()) {
                System.out.println("Test Passed: Order placed successfully.");
            } else {
                System.out.println("Test Failed: Confirmation message not found.");
            }

        } catch (Exception e) {
            System.out.println("Test Interrupted: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}
