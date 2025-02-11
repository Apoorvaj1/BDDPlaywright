package steps;

import base.BaseTest;
import com.microsoft.playwright.Page;
import constants.data;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pageObjects.CarBase;
import pageObjects.Homepage;
import pageObjects.NewCarsPage;

import java.io.IOException;

public class stepDefinitions extends BaseTest {

    Homepage homepage;
    NewCarsPage newCarsPage;
    CarBase carBase;
    Page page;
    @Given("user navigate to carwale website")
    public void user_navigate_to_carwale_website() throws IOException {
        page =BaseTest.initialize();
        homepage = new Homepage(page);
        homepage.navigateURL(data.URL);
    }
    @When("user mousehover to New car option")
    public void user_movehover_to_new_car_option() throws IOException {
        homepage.mouseHoverNewCar("NEW_CARS");
    }
    @When("click on Find New Cars")
    public void click_on_find_new_cars() throws IOException {
        newCarsPage = homepage.clickFindNewCarOption("FIND_NEW_CARS");
    }
    @Then("user able to see lists of cars")
    public void user_able_to_see_lists_of_cars() throws IOException {
        newCarsPage.extractAllCarName("ALL_CARS");
    }
    @When("user clicks on {string}")
    public void user_clicks_on(String name) {
        carBase = newCarsPage.particularCar(name);
    }
    @Then("user able to see {string}")
    public void user_able_to_see(String name) throws IOException {
        String innerText = carBase.extractTitle();
        System.out.println("INNER TEXT is "+innerText);
        Assert.assertEquals(carBase.extractTitle(),name);
    }

}
