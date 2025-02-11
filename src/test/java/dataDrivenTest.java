import com.microsoft.playwright.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class dataDrivenTest {
    @Test(dataProvider = "demoData", dataProviderClass = utils.excelReader.class)
    public void test(Hashtable<String,String> data){
        Playwright playwright = Playwright.create();
        List<String> list = new ArrayList<>();
        list.add("--incognito");
        list.add("--start-maximized");
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setArgs(list).setHeadless(false));
        BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        Page page = context.newPage();
        page.navigate("https://www.carwale.com/");
        page.hover("//div[text()=\"NEW CARS\"]");
        page.waitForSelector("//div[text()=\"Find New Cars\"]",new Page.WaitForSelectorOptions().setTimeout(5000));
        page.click("//div[text()=\"Find New Cars\"]");
        page.locator("//div[text()=\"View More Brands\"]").click();
        page.waitForSelector("div[class^=\"o-zmksK\"]",new Page.WaitForSelectorOptions().setTimeout(5000));
        Locator cars = page.locator("div[class^=\"o-zmksK\"]");
        for(int i=0;i<cars.count();i++){
            if(cars.nth(i).innerText().equalsIgnoreCase(data.get("Brand"))){
                System.out.println(data.get("Place"));
                cars.nth(i).click();
                break;
            }
        }
        page.waitForSelector("//h3[@class=\"o-jjpuv o-cVMLxW o-mHabQ o-fzpibK\"]",new Page.WaitForSelectorOptions().setTimeout(4000));
        Locator carBrandName = page.locator("//h3[@class=\"o-jjpuv o-cVMLxW o-mHabQ o-fzpibK\"]");
        Locator upcomingCars = page.locator("//div[text()=\"Upcoming\"]");
        page.waitForSelector("span[class*=\"o-cJrNdO o-by\"]",new Page.WaitForSelectorOptions().setTimeout(3000));
        Locator carPrice = page.locator("span[class*=\"o-cJrNdO o-by\"]");

        System.out.println("------------------------CARS ARE-------------------------");
        for(int i=0;i<carBrandName.count();i++){
            if(upcomingCars.nth(i).isVisible()){
                System.out.println("Upcoming car name is "+upcomingCars.nth(i).innerText().trim());
                continue;
            }
            System.out.println(carBrandName.nth(i).innerText()+" : "+carPrice.nth(i).innerText());
        }
        page.waitForTimeout(3000);
        page.close();
        context.close();
        browser.close();
        playwright.close();

    }

//    @DataProvider(name="demoData")
//    public Object[][] getData(){
//        return new Object[][]{
//                {"Lamborghini"},
//                {"McLaren"},
//                {"BMW"}
//        };
//    }
}
