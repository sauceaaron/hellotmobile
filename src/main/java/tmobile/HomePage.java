package tmobile;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage
{
	WebDriver driver;

	public static By usernameField = By.id("user-name");
	public static By passwordField = By.id("password");
	public static By loginButton = By.cssSelector(".btn_action");

	public HomePage(WebDriver driver)
	{
		this.driver = driver;
	}

	public HomePage open()
	{
		driver.get("https://www.saucedemo.com");
		return new HomePage(driver);
	}

	public String getTitle()
	{
		return driver.getTitle();
	}

	public void enterUsername(String password)
	{
		driver.findElement(usernameField).sendKeys(password);
	}

	public void enterPassword(String password)
	{
		driver.findElement(passwordField).sendKeys(password);
	}

	public void login(String username, String password)
	{
		enterUsername(username);
		enterPassword(password);
		driver.findElement(loginButton).click();
	}
}
