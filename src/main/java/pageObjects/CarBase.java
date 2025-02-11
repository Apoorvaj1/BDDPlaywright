package pageObjects;

import base.BaseClass;
import com.microsoft.playwright.Page;

import java.io.IOException;

public class CarBase extends BaseClass {

    public CarBase(Page page){
        super(page);
    }

    public String extractTitle() throws IOException {
        return fetchInnerText(utils.configReader.readKey("SPECIFIC_CAR"));
    }
}
