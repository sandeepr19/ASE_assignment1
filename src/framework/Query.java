package framework;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Query {
	String originalUrl = "https://www.cia.gov/library/publications/the-world-factbook/print/textversion.html";
	String nationURL = "https://www.cia.gov/library/publications/the-world-factbook/geos/countrytemplate_";
	List<String> listOfCountries;
	URL url;
	String suffix = ".html";
	HttpURLConnection conn;
	Document doc;
	Elements links;
	String continent;
	boolean continentPresent;
	String[] listOfContinents = { "Asia", "South America", "North America",
			"Australia", "Africa", "Europe" };
	Map<String, String> countryMap = new HashMap<String, String>();

	public void displayResults() throws IOException {

	}

	public List<String> getListOfNations() throws IOException {
		doc = Jsoup.connect(originalUrl).get();
		Element links = doc.select("#countryCode").first();
		Elements siblings = links.children();
		listOfCountries = new ArrayList<String>();
		for (Element element : siblings) {
			String link = element.attr("value");
			if(!link.equals("")){
			String countryCode = link;
			String countryName = element.ownText();
			listOfCountries.add(countryCode);
			countryMap.put(countryCode, countryName);
			}
		}
		return listOfCountries;
	}

	/*
	 * public static void main(String[] args) throws IOException { Query query =
	 * new Query(); List<String> countries = query.getListOfNations(); for
	 * (String country : countries) { System.out.println(country); }
	 * 
	 * }
	 */

	public List<LocationInformation> getLocationInformation()
			throws IOException {
		List<LocationInformation> listOfLocations = new ArrayList<LocationInformation>();
		LocationInformation locationObject;
		listOfCountries = getListOfNations();
		// listOfCountries = new ArrayList<String>();
		// listOfCountries.add("af");
		for (String country : listOfCountries) {
			//System.out.println(country);
			doc = Jsoup.connect(nationURL + country + suffix).get();
			Elements links = doc.select("a[href]");
			for (Element link : links) {
				String header = link.ownText();
				if (header.contains("Capital")) {
					try{
					Element element;
					Element nextElement = null;
						element = ((link.parent()).parent()).parent();
						nextElement = (element.nextElementSibling().child(0))
								.child(0);
					String capitalInformation = (nextElement.child(0))
							.ownText();
					String locationValue = ((nextElement.nextElementSibling())
							.child(0)).ownText();
					String[] locationInformation = (locationValue.trim())
							.split(",");
					if (locationInformation.length != 2)
						break;
					String longitudeInformation = locationInformation[0].trim();
					if ((longitudeInformation.split(" ")).length != 3)
						break;
					Integer longitude = 0;
					if (longitudeInformation.charAt(longitudeInformation
							.length() - 1) == 'N')
						longitude = Integer
								.parseInt(((longitudeInformation.substring(0,
										longitudeInformation.length() - 2))
										.split(" "))[0]);
					else if (longitudeInformation.charAt(longitudeInformation
							.length() - 1) == 'S')
						longitude = -1
								* Integer
										.parseInt(((longitudeInformation
												.substring(0,
														longitudeInformation
																.length() - 2))
												.split(" "))[0]);

					String latitudeInformation = locationInformation[1].trim();
					if ((latitudeInformation.split(" ")).length != 3)
						break;
					Integer latitude = 0;
					if (latitudeInformation
							.charAt(latitudeInformation.length() - 1) == 'W')
						latitude = Integer
								.parseInt(((latitudeInformation.substring(0,
										latitudeInformation.length() - 2))
										.split(" "))[0]);
					else if (latitudeInformation.charAt(latitudeInformation
							.length() - 1) == 'E')
						latitude = -1
								* Integer
										.parseInt(((latitudeInformation
												.substring(0,
														latitudeInformation
																.length() - 2))
												.split(" "))[0]);

					locationObject = new LocationInformation();
					locationObject.setCapitalName(capitalInformation);
					locationObject.setLatitude(latitude);
					locationObject.setLongitude(longitude);
					//System.out.println(capitalInformation);
					listOfLocations.add(locationObject);
					}catch(Exception e){
						break;
					}
				}
			}
		}
		return listOfLocations;
	}
}
