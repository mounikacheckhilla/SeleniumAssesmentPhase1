package com.saucedemo.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class FilterTest {

    public static void main(String[] args) {
        // Set the path to the ChromeDriver
        String chromeDriverPath = Paths.get("src", "main", "resources", "drivers", "chromedriver.exe").toAbsolutePath().toString();
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();


        try {
            //Login to page
            driver.get("https://www.saucedemo.com/");
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();

            //Wait for the home page to load
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("product_sort_container")));

            //Select the filter option (Price high to low)
            WebElement filterDropdown = driver.findElement(By.className("product_sort_container"));
            filterDropdown.click(); // Open the dropdown

            WebElement highToLowOption = driver.findElement(By.xpath("//option[@value='hilo']"));
            highToLowOption.click(); // Select 'Price (high to low)'

            //Wait for the results to be filtered
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_item_price")));

            //Verify that the products are sorted by price (high to low)
            List<WebElement> priceElements = driver.findElements(By.className("inventory_item_price"));
            List<Double> prices = new ArrayList<>();

            for (WebElement priceElement : priceElements) {
                prices.add(extractPrice(priceElement.getText()));
            }

            // Check if the list is sorted in descending order
            boolean isSorted = true;
            for (int i = 0; i < prices.size() - 1; i++) {
                if (prices.get(i) < prices.get(i + 1)) {
                    isSorted = false;
                    break;
                }
            }

            //Output the result
            if (isSorted) {
                System.out.println("Test Passed: Products are sorted by price (high to low).");
            } else {
                System.out.println("Test Failed: Products are not sorted by price (high to low).");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    // Helper method to convert price string to double
    private static double extractPrice(String priceText) {
        return Double.parseDouble(priceText.replace("$", "").trim());
    }
}
