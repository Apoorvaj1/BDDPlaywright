package pageObjects;

import base.BaseClass;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.io.IOException;

public class NewCarsPage extends BaseClass {

    public NewCarsPage(Page page){
        super(page);
    }
    Locator handles;

    public void extractAllCarName(String name) throws IOException {
        String actualLocator = utils.configReader.readKey(name);
        System.out.println("Extracting text from locator: " + actualLocator);

        page.waitForSelector(actualLocator, new Page.WaitForSelectorOptions().setTimeout(5000)); // Ensure elements are present

        handles = page.locator(actualLocator);
        int count = handles.count();

        System.out.println("Total cars found: " + count);

        if (count == 0) {
            System.out.println("No cars found! Locator might be incorrect.");
            return;
        }

        for (int i = 0; i < count; i++) {
            System.out.println("Car Name: " + handles.nth(i).innerText());
        }
    }

    public CarBase particularCar(String name){
        for(int i=0;i<handles.count();i++){
            if(handles.nth(i).innerText().equalsIgnoreCase(name)){
                handles.nth(i).click();
                break;
            }
        }
        return new CarBase(page);
    }



}
