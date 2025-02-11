package base;

import com.microsoft.playwright.Page;


public class BaseClass {

    protected Page page;

    public BaseClass(Page page){
        this.page = page;
    }

    public void elementMouseHover(String locator){
        page.hover(locator);
    }

    public void elementClick(String locator){
        page.click(locator);
    }

    public void navigateURL(String url){
        page.navigate(url);
    }

    public String fetchInnerText(String locator){
       String text = page.locator(locator).innerText();
       System.out.println(text);
       return text;
    }


}
