package stepsImplementations;

import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import cucumber.api.java.en.And;

public class BDDSearchIphone {

	WebDriver driver;
	public double totalProductsFound;
	public int totalIphoneItems;
	public double highestIphonePriceUSD;

	@When("^user navigate to google website$")
	public void user_navigate_to_google_website() {
		System.out.println("user navigate to google website");

		System.setProperty("webdriver.chrome.driver",
				"C:\\Luana\\workspace\\LuanaCostaProject\\util\\chromedriver.exe");

		driver = new ChromeDriver();

		// Open Browser on "www.google.com"
		driver.get("http://google.com/");
	}

	@And("^search for amazon website$")
	public void search_for_amazon_website() {
		System.out.println("search for amazon website");

		// Search for "Amazon Brasil" and Search
		driver.findElement(By.name("q")).sendKeys("amazon brasil");
		driver.findElement(By.name("q")).sendKeys(Keys.ENTER);

		// Navigate to Amazon.com.br
		driver.findElement(By.xpath("//a[@data-pcu='http://www.amazon.com.br/,https://www.amazon.com.br/']")).click();
	}

	@And("^search for iphone using the search bar$")
	public void search_for_iphone_using_the_search_bar() throws InterruptedException {
		System.out.println("search for iphone using the search bar");

		Thread.sleep(5000);
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("iphone");
		Thread.sleep(10000);
		driver.findElement(By.xpath(
				"/html[1]/body[1]/div[1]/header[1]/div[1]/div[1]/div[2]/div[1]/form[1]/div[3]/div[1]/span[1]/input[1]"))
				.click();
	}

	@Then("^Count The Total List Of Found Products in Page$")
	public void Count_The_Total_List_Of_Found_Products_in_Page() {
		System.out.println("Count The Total List Of Found Products in Page");

		List<WebElement> ItemsOnPage = driver.findElements(By.xpath("//*[@class = 'a-section a-spacing-medium']"));

		System.out.println(ItemsOnPage.size());
	}

	@And("^Count The Total Of Iphone Items Found$")
	public void Count_The_Total_Of_Iphone_Items_Found() throws InterruptedException {

		List<WebElement> iphoneItems = driver.findElements(By.xpath(
				"//span[starts-with(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'apple iphone')]"));
		List<WebElement> appleIphoneItems = driver.findElements(By.xpath(
				"//span[starts-with(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'iphone')]"));

		totalIphoneItems = iphoneItems.size() + appleIphoneItems.size();

		System.out.println(totalIphoneItems);
	}

	@Then("^make sure at least 50 porcent of items found are Iphone$")
	public void make_sure_at_least_50_porcent_of_items_found_are_Iphone() {

		double result = ((totalIphoneItems * 100) / totalProductsFound);
		assertTrue(result >= 50);
		driver.close();
	}

	@Then("^find the more expensive Iphone in page$$")
	public void find_the_more_expensive_Iphone_in_page() throws InterruptedException {

		System.out.println("find the more expensive Iphone in page");

		double highestPriceIphone = findPrice(true, false);

		System.out.println("The most expensive:" + highestPriceIphone);
	}

	@And("^convert its value to USD$$")
	public void convert_its_value_to_USD$() throws IOException, InterruptedException {

		System.out.println("convert its value to USD$");

		double rate = getExchangeRate();
		double highestPriceIphone = findPrice(true, false);
		
		System.out.println("rate" + rate);

		highestIphonePriceUSD = highestPriceIphone * rate;
	}

	public double getExchangeRate() throws IOException {

		URL url = new URL("https://api.exchangeratesapi.io/latest?base=BRL&symbols=USD");

		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;

		double rate = 0;

		while ((inputLine = in.readLine()) != null) {
			JSONObject object = new JSONObject(inputLine);
			rate = object.getJSONObject("rates").getDouble("USD");
		}

		return rate;
	}

	@Then("^make sure the converted value is not greater than US 2000$$")
	public void make_sure_the_converted_value_is_not_greater_than_US_2000$() {
		System.out.println("make sure the converted value is not greater than US 2000$");
		System.out.println("Price Dolar" + highestIphonePriceUSD);
		assertTrue(highestIphonePriceUSD <= 2000);
		driver.close();
	}

	@And("^find products which are not Iphone$")
	public void find_products_which_are_not_Iphone() throws InterruptedException {

		int totalAcessories = 0;

		Thread.sleep(6000);
		List<WebElement> itens = driver
				.findElements(By.xpath("//*[contains(@class,'a-size-base-plus a-color-base a-text-normal')]"));

		for (WebElement webElement : itens) {
			if (!(webElement.getAttribute("textContent").toLowerCase().startsWith("apple iphone")
					|| webElement.getAttribute("textContent").toLowerCase().startsWith("iphone"))) {
				totalAcessories++;
			}
		}

		System.out.println("total items found " + totalAcessories);
	}

	@Then("^make sure all found products are cheaper than the cheapest Iphone$")
	public void make_sure_all_found_products_are_cheaper_than_the_cheapest_Iphone() throws InterruptedException {
		System.out.println("make_sure_all_found_products_are_cheaper_than_the_cheapest_Iphone");

		double cheapestPriceIphone = findPrice(true, true);
		double highestPriceNonIphone = findPrice(false, false);

		assertTrue(highestPriceNonIphone < cheapestPriceIphone);
	}

	public double findPrice(boolean iPhone, boolean cheapest) throws InterruptedException {

		List<WebElement> ItemsOnPage = driver.findElements(By.xpath("//*[@class = 'a-section a-spacing-medium']"));

		// Price reference
		double refPrice = cheapest ? Double.MAX_VALUE : Double.MIN_VALUE;

		for (WebElement item : ItemsOnPage) {

			String title = item.findElement(By.tagName("h2")).getText();

			boolean productIsIphone = title.toLowerCase().startsWith("iphone")
					|| title.toLowerCase().startsWith("apple iphone");

			if (iPhone == productIsIphone) {
				List<WebElement> elems = item.findElements(By.cssSelector("span.a-offscreen"));

				if (elems.size() == 0) {
					continue;
				}

				WebElement priceSpan = elems.get(0);
				
				String priceText = priceSpan.getAttribute("textContent").replace("R$", "").replace(".", "").replace(",",".");
				
				Double price = Double.parseDouble(priceText);

				if (cheapest && refPrice > price) {
					refPrice = price;
				} else if (!cheapest && price > refPrice) {
					refPrice = price;
				}
			}
		}

		return refPrice;
	}

}
