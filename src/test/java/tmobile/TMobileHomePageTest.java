package tmobile;

import com.saucelabs.saucerest.SauceREST;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class TMobileHomePageTest
{
	RemoteWebDriver driver;
	Boolean passed = true;

	String username = System.getenv("SAUCE_USERNAME");
	String accessKey = System.getenv("SAUCE_ACCESS_KEY");

	@Rule
	public TestName testName = new TestName();

	@Before
	public void setup() throws MalformedURLException
	{
		URL url = new URL("https://ondemand.saucelabs.com/wd/hub");

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platform", "MacOS 10.14");
		capabilities.setCapability("browserName", "safari");
		capabilities.setCapability("version", "latest");
		capabilities.setCapability("username", username);
		capabilities.setCapability("accessKey", accessKey);
		capabilities.setCapability("build", "t-mobile demo");
		capabilities.setCapability("name", testName.getMethodName());

		driver = new RemoteWebDriver(url, capabilities);
	}

	@Test
	public void checkTitle()
	{
		driver.get("https://www.saucedemo.com");
		String title = driver.getTitle();

		try
		{
			assertThat(title).contains("Sauce Labs");
		}
		catch (AssertionError e)
		{
			passed=false;
			throw(e);
		}
	}

	@Test
	public void login()
	{
		String username = "standard_user";
		String password = "secret_sauce";

		new HomePage(driver)
				.open()
				.login(username, password);

		WebDriverWait wait = new WebDriverWait(driver, 10);

		String headerText = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("product_label"))).getText();
		System.out.println(headerText);

		try {
			assertThat(headerText).contains("Products");
		}
			catch (AssertionError e)
		{
			passed=false;
			throw(e);
		}
	}

	@After
	public void cleanup()
	{
		String sessionId = driver.getSessionId().toString();
		driver.quit();

		SauceREST api = new SauceREST(username, accessKey);


		if (passed)
		{
			api.jobPassed(sessionId);
		}
		else {
			api.jobFailed(sessionId);
		}
	}
}
