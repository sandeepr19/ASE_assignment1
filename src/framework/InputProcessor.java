package framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputProcessor {

	public void processInput(int option) throws IOException {
		Query query;
		switch (option) {
		case 1:
			query = new NaturalDisasterQuery();
			query.displayResults();
			break;
		case 2:
			query = new PoliticalPartyQuery();
			query.displayResults();
			break;
		case 3:
			query = new FlagQuery();
			query.displayResults();
			break;
		case 4:
			query = new LandlockedNationQuery();
			query.displayResults();
			break;
		case 5:
			query = new LongitudeLatitudeQuery();
			query.displayResults();
			break;
		default:
			System.out.println("invalid input");
			break;
		}

	}

	public static void main(String[] args) throws IOException {
		InputProcessor input = new InputProcessor();
		System.out
				.println("Following are the options.\n 1 for natural disasters.\n 2 for political parties.\n 3 "
						+ "for flags.\n 4 for landlocked nations.\n 5 for capitals within 10 degrees of latitude and longitude"
						+ "\n Please select an option");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String userInput = br.readLine().trim();
		try {
			int option = Integer.parseInt(userInput);
			input.processInput(option);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Invalid option");
		}
	}
}
