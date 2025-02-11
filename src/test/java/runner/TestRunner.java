package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterMethod;

import static base.BaseTest.*;

@CucumberOptions(
        features = {"./src/test/resources/features/cars.feature"},
        glue = {"steps"},
        monochrome = true,
        dryRun = false,
        plugin = {"pretty","html:target/html-report.html","html:target/cucumber-html-report.html"}
)

public class TestRunner extends AbstractTestNGCucumberTests {

    @AfterMethod
    public static void tearDown(){
        getPage().waitForTimeout(3000);
        getPage().close();
        getContext().close();
        getBrowser().close();
        getPlaywright().close();
    }
}
