package framework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class LongitudeLatitudeQuery extends Query {

	List<LocationInformation> listOfLocationInformation;
	Hashtable<String, List<LocationInformation>> hashTable;
	List<LocationInformation> listOfPossibleLocations = new ArrayList<LocationInformation>();

	public void displayResults() throws IOException {
		listOfLocationInformation = getLocationInformation();
		hashTable = new Hashtable<String, List<LocationInformation>>();
		for (int i = 0; i < listOfLocationInformation.size(); i++) {
			String capital = listOfLocationInformation.get(i).getCapitalName();
			listOfPossibleLocations = new ArrayList<LocationInformation>();
			listOfPossibleLocations.add(listOfLocationInformation.get(i));
			for (int j = 0; j < listOfLocationInformation.size(); j++) {
				if (j != i) {
					if (Math.abs(listOfLocationInformation.get(j).getLatitude()
							- listOfLocationInformation.get(i).getLatitude()) < 10
							&& Math.abs(listOfLocationInformation.get(j)
									.getLongitude()
									- listOfLocationInformation.get(i)
											.getLongitude()) < 10) {
						listOfPossibleLocations.add(listOfLocationInformation
								.get(j));
					}
				}
			}
			hashTable.put(capital, listOfPossibleLocations);
		}

		Set<String> capitalSet = hashTable.keySet();
		Integer maxSize = 0;
		String startingCapital = "";
		for (String capital : capitalSet) {
			int length = hashTable.get(capital).size();
			if (length > maxSize) {
				maxSize = length;
				startingCapital = capital;
			}
		}

		for (LocationInformation locationInformation : hashTable
				.get(startingCapital)) {
			System.out.println(locationInformation.getCapitalName());
		}

	}
}
