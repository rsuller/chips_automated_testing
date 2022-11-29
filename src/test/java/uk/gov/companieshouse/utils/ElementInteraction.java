package uk.gov.companieshouse.utils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class ElementInteraction {

    public void selectByValue(WebElement element, String value) {
        Select select = new Select(element);
        select.selectByValue(value);
    }
}
