package be.yolanv.tododemo.e2e.base;

import be.yolanv.tododemo.e2e.util.ScreenshotService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BaseSeleniumE2ETest {

    protected final static String GECKODRIVER_PATH = "/path/to/geckodriver";
    protected String BASE_URL = "http://localhost:8080";
    protected WebDriver driver;
    protected WebDriverWait waitDriver;

    private ScreenshotService screenshotService;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.gecko.driver", GECKODRIVER_PATH);
    }

    @BeforeEach
    void setUpBeforeEach() {
        driver = new FirefoxDriver(new FirefoxOptions());
        screenshotService = new ScreenshotService();
        waitDriver = new WebDriverWait(driver, 5L);
        driver.get(BASE_URL);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    protected void captureScreenshot() {
        screenshotService.captureScreenShot(driver, this.getClass());
    }
}
