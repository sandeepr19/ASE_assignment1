package framework;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LandlockedNationQuery extends Query {

	List<String> listOfCountries;

	public void displayResults() throws IOException {
		displayCountries();
	}

	public void displayCountries() throws IOException {
		listOfCountries = getListOfNations();
		for (String country : listOfCountries) {
			doc = Jsoup.connect(nationURL + country + suffix).get();
			Elements links = doc.select("a[href]");
			for (Element link : links) {
				String header = link.ownText();
				if (header.contains("Location")) {
					Element element = ((link.parent()).parent()).parent();
					String description = (((element.nextElementSibling())
							.child(0)).child(0)).ownText();
					if ((description.toLowerCase()).contains("enclave"))
						System.out.println(countryMap.get(country));
					break;
				}
			}
		}
	}

}
