package astaire;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.Arrays;

public class ImplController implements Controller {

	int count = 0;
	int count2 = 0;

	@Override
	public String listAllDancersIn(String dance) {
		// get CSV file for dances Data
		ArrayList<String> dancesData = getCSV("src/csvFiles/danceShowData_dances.csv");

		String result = "";

		// for each line in dances csv file
		for (String line : dancesData) {

			// split into two sections - [0] is name of dance & [1] is dancers
			String[] splitByTab = line.split("\t");

			splitByTab[0] = splitByTab[0].trim();

			// if name of dance matches given dance name
			if (splitByTab[0].equals(dance)) {

				// split names of dancers into individual strings
				String[] separatedNames = splitByComma(splitByTab[1]);

				// iterate through names
				for (int i = 0; i < separatedNames.length; i++) {
					// append result with output of getDanceGroupMembers (and trim input)
					result += ", " + getDanceGroupMembers(separatedNames[i].trim());
				}
			}
		}

		// remove leading comma and space
		result = result.substring(2);

		return result;
	}

	public String[] splitByComma(String names) {
		return names.split(", ");
	}

	public String getDanceGroupMembers(String name) {
		// get dance group data
		ArrayList<String> danceGroupsData = getCSV("src/csvFiles/danceShowData_danceGroups.csv");

		// result by default is just name of given
		String result = name;

		// iterate iterate
		for (String line : danceGroupsData) {
			String[] splitByTab = line.split("\t");

			// if, at any point, name of dance group is equal to given name
			if (splitByTab[0].equals(name)) {
				// return names of dancers in group
				result = getDanceGroupMembers(splitByTab[1]);
			}
		}

		return result;
	}

	@Override
	public String listAllDancesAndPerformers(String filename) {

		// get CSV file for dances Data
		ArrayList<String> dancesData = getCSV("src/csvFiles/" + filename);

		int lineNumber = 0;
		String result = "";

		// for each line in dances csv file
		for (String line : dancesData) {

			// split into two sections - [0] is name of dance & [1] is dancers
			String[] splitByTab = line.split("\t");

			lineNumber++;
			result += lineNumber + ": ";
			result += (splitByTab[0].trim()) + "\n";
			result += (listAllDancersIn(splitByTab[0].trim())) + "\n";
		}

		return result;
	}

	@Override
	public String checkFeasibilityOfRunningOrder(String filename, int gaps) {

		// get CSV file for dances Data
		ArrayList<String> runningOrder = getCSV("src/csvFiles/" + filename);

		System.out.println(listAllDancesAndPerformers(filename));

		return checkRunningOrder(runningOrder, gaps);
	}

	private String checkRunningOrder(ArrayList<String> runningOrder, int gaps) {

		// For each line (dance) in running order
		for (int i = 0; i < runningOrder.size() - gaps; i++) {

			// get individual names
			String[] splitByTab = runningOrder.get(i).split("\t");
			String[] dancers = listAllDancersIn(splitByTab[0].trim()).split(", ");

			String[] nextDancers;

			// from current dance to current dance PLUS given gaps
			for (int a = i + 1; a < i + gaps; a++) {

				// get individual names
				splitByTab = runningOrder.get(a).split("\t");
				nextDancers = listAllDancersIn(splitByTab[0].trim()).split(", ");

				// for each dancer in current dance
				for (String dancer : dancers) {

					// for each dancer in next dances - up until current dance + gaps

					for (String nextDancer : nextDancers) {

						// if any dancers repeated
						if (dancer.equals(nextDancer)) {
							return "This running order is not feasible. " + dancer + " needs to change in " + (a - i)
									+ " dances, \n between dance number " + (i + 1) + " and dance number " + (a + 1);
						}
					}
				}
			}
		}

		return "This running order is feasible.";
	}

	@Override
	public String generateRunningOrder(int gaps) {
		// get CSV file for dances Data
		ArrayList<String> dancesData = getCSV("src/csvFiles/danceShowData_dances.csv");

		return checkRunningOrder(gaps, dancesData);
	}

	private String checkRunningOrder(int gaps, ArrayList<String> runningOrder) {

		Random ran  = new Random();

		String result = "";

		// For each line (dance) in running order
		for (int i = count2; i < runningOrder.size() - gaps; i++) {

			// get individual names
			String[] splitByTab = runningOrder.get(i).split("\t");
			String[] dancers = listAllDancersIn(splitByTab[0].trim()).split(", ");

			String[] nextDancers;

			// from current dance to current dance PLUS given gaps
			for (int a = i + 1; a < i + gaps; a++) {

				// get individual names
				splitByTab = runningOrder.get(a).split("\t");
				nextDancers = listAllDancersIn(splitByTab[0].trim()).split(", ");

				// for each dancer in current dance
				for (String dancer : dancers) {

					// for each dancer in next dances - up until current dance + gaps

					for (String nextDancer : nextDancers) {
						// if any dancers repeated
						if (dancer.equals(nextDancer)) {
//							needsSwap = true;
//							toSwap = a;
//							String temp = runningOrder.get(a+1);
//							runningOrder.add(a+1, runningOrder.get(a));
//							runningOrder.add(a, temp);

							System.out.println("Swapped: " + runningOrder.get(a).split("\t")[0] + " with: " + runningOrder.get(a+1).split("\t")[0]);
							Collections.swap(runningOrder, a, ran.nextInt((runningOrder.size()-a))+a);
							
//							System.out.println("\n\n Current running Order: \n");
//							for (String line : runningOrder) {
//								System.out.println(line);
//							}
							
							if (count > 1000) {
								return "\nA feasible running order could not be found";
							}
							count++;
							
							checkRunningOrder(gaps, runningOrder);
						}
					}
				}
			}
		}

		result += "The following running order will work: \n";
		for (String line : runningOrder) {
			result += line + "\n";
		}

		return result;
	}

	public ArrayList<String> getCSV(String file) {

		// Print program menu and loop till the user exit

		BufferedReader buffer = null;
		ArrayList<String> data = new ArrayList<>();
		try {
			String line;
			buffer = new BufferedReader(new FileReader(file));
			buffer.readLine();
			while ((line = buffer.readLine()) != null) {

				// System.out.println( csvToArray(line));
				data.add(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (buffer != null)
					buffer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;

	}
}
