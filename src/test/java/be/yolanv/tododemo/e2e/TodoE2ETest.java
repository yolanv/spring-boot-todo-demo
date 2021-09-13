package be.yolanv.tododemo.e2e;

import be.yolanv.tododemo.e2e.base.BaseSeleniumE2ETest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.assertj.core.api.Assertions.assertThat;

public class TodoE2ETest extends BaseSeleniumE2ETest {

    private static final String COLOR_HEX_ORANGE = "#f58220";
    private static final String APP_TITLE = "JWorks Todo Demo App";
    private static final String ADD_NEW_TODO_BUTTON = "Add New Todo";
    private static final String XPATH_TABLE = "//table[@class='table']";
    private static final String MODAL_TITLE = "TODO";

    @Test
    void test_E2E_AssertVisibleHtmlElements() {
        // Find the content of the title by going through various HTML attributes on the page
        // You can go step-wise through your HTML elements to find the right one by chaining findElement()
        var title = driver
                .findElement(By.id("header"))
                .findElement(By.className("navbar"))
                .findElement(By.tagName("a")).getText();

        assertThat(driver.getTitle()).isEqualTo(APP_TITLE);
        assertThat(title).isEqualTo(APP_TITLE);

        // Find the CSS value of a HTML element
        var navColor = driver
                .findElement(By.id("header"))
                .findElement(By.className("navbar")).getCssValue("background-color");
        var navColorHex = Color.fromString(navColor).asHex();

        assertThat(navColorHex).isEqualTo(COLOR_HEX_ORANGE);

        // We can ask the WebDriverWait to wait until the logo is done loading so we can proceed with the test
        // No chaining needed here, because 'logo' is only used by the img tag
        waitDriver.until(ExpectedConditions.elementToBeClickable(By.className("logo")));
        // Find the image through the class name 'logo'
        var image = driver.findElement(By.className("logo"));

        assertThat(image.getTagName()).isEqualTo("img");
        assertThat(image.isDisplayed()).isTrue();

        // Easily find the table and the button on the page through XPath
        var thead = driver.findElement(By.xpath(XPATH_TABLE + "/thead"));
        var theadRows = thead.findElements(By.tagName("th"));

        assertThat(theadRows.size()).isEqualTo(2);
        assertThat(theadRows.get(0).getText()).isEqualTo("Description");
        assertThat(theadRows.get(1).getText()).isEqualTo("Delete");

        var tbody = getTableBody();
        assertThat(tbody.findElements(By.tagName("tr")).size()).isZero();

        var addButton = getAddTodoButton();
        assertThat(addButton.getText()).isEqualTo(ADD_NEW_TODO_BUTTON);

        captureScreenshot();
    }

    @Test
    void test_E2E_addNewTodoToTable_And_DeleteTodoFromTable() throws InterruptedException {
        var addButton = getAddTodoButton();
        // interact with the button
        addButton.click();

        // Wait two seconds to show visible changes
        Thread.sleep(2000);

        // Find an element on the page by using its id in the HTML element
        var formTitle = driver.findElement(By.id("modalTitle"));
        assertThat(formTitle.getText()).isEqualTo(MODAL_TITLE);

        var descriptionBox = driver.findElement(By.id("todoDescription"));
        assertThat(descriptionBox.getTagName()).isEqualTo("input");
        // Type "this is an E2E test" in the input field
        descriptionBox.sendKeys("This is an E2E test");

        // Wait two seconds to show visible changes
        Thread.sleep(2000);

        var confirmButton = driver.findElement(By.xpath("//div[@class='modal-body']/form/button[@type='submit']"));

        // Interact with the "Add todo" button
        confirmButton.click();

        captureScreenshot();

        var tbody = getTableBody();
        var rows = tbody.findElements(By.tagName("tr"));
        assertThat(rows.size()).isEqualTo(1);

        // Use CSS selectors to find the columns of the TODO entry on the page
        assertThat(tbody.findElement(By.cssSelector("tr:nth-child(1)"))
                .findElements(By.tagName("td")).size()).isEqualTo(2);

        assertThat(rows.get(0).findElement(By.xpath("//td[1]")).getText()).isEqualTo("This is an E2E test");

        // Wait two seconds to show visible changes
        Thread.sleep(2000);

        // Find the delete button based on the input type and the text in the button
        var deleteButton = tbody.findElement(By.xpath("//button[@type='submit' and text() = 'Delete']"));
        deleteButton.click();

        // Verify that the item is removed from the table by checking if there are no <tr> elements
        assertThat(getTableBody().findElements(By.tagName("tr")).size()).isEqualTo(0);
    }

    private WebElement getAddTodoButton() {
        return driver.findElement(By.xpath("//div[@class='d-flex justify-content-center']/a[contains(@class, 'btn')]"));
    }

    private WebElement getTableBody() {
        return driver.findElement(By.xpath(XPATH_TABLE + "/tbody"));
    }
}
