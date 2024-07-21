package com.evy.framework.drivers;

import com.evy.framework.config.ConfigManager;
import com.evy.framework.constants.BrowserType;
import com.evy.framework.utils.LoggerUtils;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

/**
 * Manages a WebDriver instance for the current thread.
 * <p>
 * Provides methods to initialize, configure, and quit a WebDriver instance.
 * Each thread gets its own WebDriver instance.
 * </p>
 */
public final class Driver {

    private static final ThreadLocal<WebDriver> THREAD_LOCAL = new ThreadLocal<>();
    private static final Driver INSTANCE = new Driver();

    private Driver() {
        // Private constructor to prevent instantiation
    }

    /**
     * Gets the singleton instance of Driver.
     *
     * @return the singleton Driver instance
     */
    public static Driver getInstance() {
        return INSTANCE;
    }

    /**
     * Initializes the WebDriver for the specified browser type.
     * <p>
     * Sets up and configures the WebDriver, storing it in a thread-local variable.
     * </p>
     *
     * @param browserType the type of browser to initialize
     */
    public void init(BrowserType browserType) {
        try {
            WebDriver driver = DriverFactory.getDriver(browserType);
            THREAD_LOCAL.set(driver);
            configureDriver(driver);
            LoggerUtils.info(Driver.class, "WebDriver initialized for browser: " + browserType);
        } catch (Exception e) {
            LoggerUtils.error(Driver.class, "Failed to initialize WebDriver for browser: " + browserType, e);
            throw new RuntimeException("Failed to initialize WebDriver", e);
        }
    }

    /**
     * Quits the WebDriver and removes it from the current thread.
     * <p>
     * Closes and cleans up the WebDriver instance.
     * </p>
     */
    public static void quitDriver() {
        try {
            WebDriver driver = THREAD_LOCAL.get();
            if (driver != null) {
                driver.quit();
                THREAD_LOCAL.remove();
                LoggerUtils.info(Driver.class, "WebDriver quit and removed from thread.");
            }
        } catch (Exception e) {
            LoggerUtils.error(Driver.class, "Error quitting WebDriver", e);
            throw new RuntimeException("Error quitting WebDriver", e);
        }
    }

    /**
     * Configures the WebDriver with timeouts and initial URL.
     * <p>
     * Sets page load timeout, implicit wait timeout, and navigates to the configured URL.
     * </p>
     *
     * @param driver the WebDriver to configure
     */
    private void configureDriver(WebDriver driver) {
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigManager.get().pageLoadTime()));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigManager.get().implicitTime()));
        driver.get(ConfigManager.get().url());
    }
}
