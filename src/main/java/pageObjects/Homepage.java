package pageObjects;

import base.BaseClass;
import com.microsoft.playwright.Page;

import java.io.IOException;

public class Homepage extends BaseClass {

    public Homepage(Page page){
        super(page);
    }

    public void mouseHoverNewCar(String locator) throws IOException {
        elementMouseHover(utils.configReader.readKey(locator));
    }

    public NewCarsPage clickFindNewCarOption(String locator) throws IOException {
        elementClick(utils.configReader.readKey(locator));
        return new NewCarsPage(page);
    }
}
