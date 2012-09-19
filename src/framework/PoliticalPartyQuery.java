package framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PoliticalPartyQuery extends Query {

	Integer numberOfPoliticalParties;
	List<String> listOfCountries;

	public void displayResults() throws IOException {
		System.out
				.println("Please enter the Continent name along with the number of political parties seperated by a ,\n");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String userInput = br.readLine();
		String inputArray[] = userInput.split(",");
		if (inputArray.length == 2) {
			continent = inputArray[0].trim();
			try {
				numberOfPoliticalParties = Integer.parseInt(inputArray[1].trim());
			} catch (Exception exception) {
				System.out.println("invalid input");
				return;
			}

		} else {
			System.out.println("Invalid input");
			return;
		}
		displayCountries(continent, numberOfPoliticalParties);
	}

	public void displayCountries(String continent,
			Integer numberOfPoliticalParties) throws IOException {
		if (continent != null && numberOfPoliticalParties != null) {
			listOfCountries = getListOfNations();
			for (String country : listOfCountries) {
				doc = Jsoup.connect(nationURL + country + suffix).get();
				Elements links = doc.select("a[href]");
				for (Element link : links) {
					String header = link.ownText();
					if (header.contains("Map references")) {
						Element element = ((link.parent()).parent()).parent();
						String continentValue = ((((element.nextElementSibling())
								.child(0)).child(0)).child(0)).ownText();
						if ((continentValue.toLowerCase()).contains(continent.toLowerCase())) {
							continentPresent = true;
							continue;
						} else
							break;
					}
					if (continentPresent
							&& header
									.contains("Political parties and leaders")) {
						Element element = ((link.parent()).parent()).parent();
						String disasterValue = (((element.nextElementSibling())
								.child(0)).child(0)).ownText();
						String[] listOfParties = disasterValue.split(";");
						if (listOfParties.length -1 > numberOfPoliticalParties)
							System.out.println(countryMap.get(country));
						break;
					}
				}
				continentPresent = false;
			}
		}
	}

}
