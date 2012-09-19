package framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FlagQuery extends Query {

	List<String> listOfCountries;
	String color;

	public void displayResults() throws IOException {
		System.out.println("Please enter the color of the flag");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String userInput = br.readLine();
		String inputArray[] = userInput.split(" ");
		if (inputArray.length == 1)
			color = inputArray[0].trim();
		else {
			System.out.println("Invalid input");
			return;
		}
		displayCountries(color);
	}

	public void displayCountries(String color) throws IOException {
		if (color != null) {
			listOfCountries = getListOfNations();
			for (String country : listOfCountries) {
				doc = Jsoup.connect(nationURL + country + suffix).get();
				Elements links = doc.select("a[href]");
				for (Element link : links) {
					String header = link.ownText();
					if (header.contains("Flag description")) {
						Element element = ((link.parent()).parent()).parent();
						String flagDescription = (((element.nextElementSibling())
								.child(0)).child(0)).ownText();
						if (flagDescription.contains(color))
							System.out.println(countryMap.get(country));
						break;
					}
				}
			}
		}
	}
}
