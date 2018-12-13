package astaire;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import java.util.Arrays;

public class ImplController implements Controller {

	int count = 0;

	/**
	 *
	 */
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

	public String[] splitByComma(String names) { //split data by comma
		return names.split(",");
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
		//get the data from in a TreeSet to make the groups sorted
		TreeSet<String> dancesData = getCSVTree("src/csvFiles/danceShowData_dances.csv");

		int lineNumber = 0;
		String result = "";

		//for each line in dances csv file
		for (String line : dancesData) {

			//split into two sections - [0] is name of dance & [1] is dancers
			String[] splitByTab = line.split("\t");




			lineNumber++; //print line number
			result += lineNumber + ": ";
			result += (splitByTab[0].trim()) + "\n"; //print group name




			String finalResult =(listAllDancersIn(splitByTab[0].trim())) ; //find out all the dancers in the dance
			String[] elements = finalResult.split(","); // split them by commas and place them into array

			for(int i = 0; i < elements.length; i++) {
				elements[i] = elements[i].trim(); //trim every element in the array
			}


			List<String> fixedLenghtList = Arrays.asList(elements); //put the array elements into a list
			ArrayList<String> listOfString = new ArrayList<String>(fixedLenghtList); //put the elements of the list into an arrayList
			Collections.sort(listOfString); //sort the array list alphabetically
			result += listOfString.toString().replace("[","").replace("]",""); //remove brackets from the answers and add to result
			result += "\n";

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

		return makeRunningOrder(gaps, dancesData);
	}

	private String makeRunningOrder(int gaps, ArrayList<String> runningOrder) {

		Random ran = new Random();

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

						if (dancer.equals(nextDancer)) {

							// Try 3000 times, if nothing found in that time then assume not possible
							if (count > 3000) {
								result = "\nA feasible running order could not be found";
							} else {
								// Place conflicting dance in other random place
								Collections.swap(runningOrder, a, ran.nextInt((runningOrder.size())));

								count++;

								// Run again
								makeRunningOrder(gaps, runningOrder);
							}
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

	public ArrayList<String> getCSV(String file) { //returns the CSV file in an ArrayList

		// Print program menu and loop till the user exit

		BufferedReader buffer = null;
		ArrayList<String> data = new ArrayList<>();
		try {
			String line;
			buffer = new BufferedReader(new FileReader(file));
			buffer.readLine();
			while ((line = buffer.readLine()) != null) {

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

	public TreeSet<String> getCSVTree(String file) { //returns the CSV file in an TreeSet

		// Print program menu and loop till the user exit

		BufferedReader buffer = null;
		TreeSet<String> data = new TreeSet<>();
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
