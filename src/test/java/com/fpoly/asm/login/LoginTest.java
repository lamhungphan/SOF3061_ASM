package com.fpoly.asm.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class LoginTest {
    WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:5173/");
    }

    @Test
    public void test() throws InterruptedException {
        Thread.sleep(4000);

        WebElement username = driver.findElement(By.id("username"));
        WebElement password = driver.findElement(By.id("password"));

        Thread.sleep(4000);

        username.sendKeys("user2");
        password.sendKeys("1234");

        Thread.sleep(4000);

        driver.findElement(By.id("submit")).click();

        Thread.sleep(4000);

        String result = driver.findElement(By.linkText("Đăng xuất")).getText();
        String expected = "Đăng xuất";

        Assert.assertEquals(result, expected);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
