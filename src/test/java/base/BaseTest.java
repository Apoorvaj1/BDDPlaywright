package base;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.ScreenshotType;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BaseTest {

    public static Page page;
    public static Browser browser;
    public static BrowserContext context;
    public static Playwright playwright;

    private static final ThreadLocal<Playwright> pw = new ThreadLocal<>();

    public static void setPlaywright(Playwright playwright){
        pw.set(playwright);
    }

    public static Playwright getPlaywright(){
        return pw.get();
    }
    private static final ThreadLocal<Browser> br = new ThreadLocal<>();

    public static void setBrowser(Browser browser){
        br.set(browser);
    }

    public static Browser getBrowser(){
        return br.get();
    }
    private static final ThreadLocal<Page> pg = new ThreadLocal<>();

    public static void setPage(Page page){
        pg.set(page);
    }
    public static Page getPage(){
        return pg.get();
    }
    private static final ThreadLocal<BrowserContext> bc = new ThreadLocal<>();

    public static void setContext(BrowserContext context){
        bc.set(context);
    }
    public static BrowserContext getContext(){
        return bc.get();
    }
    private static final ThreadLocal<WebDriver> tl = new ThreadLocal<>();
    public static void setDriver(WebDriver driver){
        tl.set(driver);
    }
    public static WebDriver getDriver(){
        return tl.get();
    }

    public static Page initialize() throws IOException {
        playwright = Playwright.create();
        setPlaywright(playwright);

        List<String> options = new ArrayList<>();
        options.add("--start-maximized");
        options.add("--incognito");

        String browserName = System.getProperty("whichBrowser"); // Read property
        if (browserName == null || browserName.trim().isEmpty()) {
            System.out.println("Browser property not set! Using default: chrome");
            browserName = "chrome";  // Explicitly set the default
        } else {
            System.out.println("BROWSER NAME IS: " + browserName);
        }

        switch (browserName.toLowerCase()) {
            case "chrome":
                browser = getPlaywright().chromium().launch(new BrowserType.LaunchOptions()
                        .setChannel("chrome").setArgs(options).setHeadless(false).setHandleSIGINT(false)   // Prevents Playwright from closing the browser on SIGINT
                        .setHandleSIGTERM(false)  // Prevents browser auto-close on test end
                        .setHandleSIGHUP(false)); // Keeps browser alive);
                break;
            case "headless":
                browser = getPlaywright().chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(true).setHandleSIGINT(false)   // Prevents Playwright from closing the browser on SIGINT
                        .setHandleSIGTERM(false)  // Prevents browser auto-close on test end
                        .setHandleSIGHUP(false)); // Keeps browser alive);
                break;
            case "msedge":
                browser = getPlaywright().chromium().launch(new BrowserType.LaunchOptions()
                        .setChannel("msedge").setArgs(options).setHeadless(false).setHandleSIGINT(false)   // Prevents Playwright from closing the browser on SIGINT
                        .setHandleSIGTERM(false)  // Prevents browser auto-close on test end
                        .setHandleSIGHUP(false)); // Keeps browser alive);
                break;
            case "firefox":
                browser = getPlaywright().firefox().launch(new BrowserType.LaunchOptions()
                        .setChannel("firefox").setArgs(options).setHeadless(false).setHandleSIGINT(false)   // Prevents Playwright from closing the browser on SIGINT
                        .setHandleSIGTERM(false)  // Prevents browser auto-close on test end
                        .setHandleSIGHUP(false)); // Keeps browser alive);
                break;
            case "webkit":
                browser = getPlaywright().webkit().launch(new BrowserType.LaunchOptions()
                        .setHeadless(false).setHandleSIGINT(false)   // Prevents Playwright from closing the browser on SIGINT
                        .setHandleSIGTERM(false)  // Prevents browser auto-close on test end
                        .setHandleSIGHUP(false)); // Keeps browser alive);
                break;
            default:
                System.out.println("Invalid browser! Using Chrome as default.");
                browser = getPlaywright().chromium().launch(new BrowserType.LaunchOptions()
                        .setChannel("chrome").setArgs(options).setHeadless(false).setHandleSIGINT(false)   // Prevents Playwright from closing the browser on SIGINT
                        .setHandleSIGTERM(false)  // Prevents browser auto-close on test end
                        .setHandleSIGHUP(false)); // Keeps browser alive);
        }

        setBrowser(browser); // Store in ThreadLocal
        if (getBrowser() == null) {
            throw new IllegalStateException("Browser initialization failed! getBrowser() is null.");
        }

        System.out.println("Browser initialized successfully: " + getBrowser()); // Debug log

        context = getBrowser().newContext(new Browser.NewContextOptions().setViewportSize(null));
        setContext(context);

        page = getContext().newPage();
        setPage(page);

        if (getPage() == null) {
            throw new IllegalStateException("Page initialization failed! getPage() is null.");
        }

        return getPage();
    }


    public static void addScreenshotInProject(String name){
        getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./src/test/resources/screenshots/"+name+System.currentTimeMillis()+".png")));
    }

    public static byte[] addScreenshotInReportPlaywright() {
        if (getPage() != null) {
            try {
                return getPage().screenshot(new Page.ScreenshotOptions()
                        .setType(ScreenshotType.PNG)); // Ensure it's a PNG
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new byte[0]; // Return empty array if screenshot fails
    }
    public static void addScreenshotInProjectSelenium(String folderName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot)getDriver();
        File src = ts.getScreenshotAs(OutputType.FILE);
        File desc = new File("./src/test/resources/"+folderName);
        FileUtils.copyFile(src,desc);
    }

    @Attachment(value = "Screenshot on Failure", type = "image/png")
    public static byte[] takeScreenshotInAllurePlaywright(){
        if(getPage()!=null){
            try {
                return getPage().screenshot(new Page.ScreenshotOptions().setType(ScreenshotType.PNG));
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return new byte[0];
    }

    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextOnAllurePlaywright(String message){
        return message;
    }

//    @Attachment(value = "Screenshot on Failure", type = "image/png")
//    public static byte[] takeScreenshotInAllure(){
//        TakesScreenshot ts = (TakesScreenshot)utils.browserFactorySelenium.getDriver();
//        return ts.getScreenshotAs(OutputType.BYTES);
//    }

    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLogInAllure(String message) {
        return message;
    }


}
