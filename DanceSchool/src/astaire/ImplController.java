package astaire;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.Arrays;

public class ImplController implements Controller {

	@Override
	public String listAllDancersIn(String dance) {
		// get CSV file for dances Data
	

		Set<String> dancesData = new TreeSet<>(getCSV("src/csvFiles/danceShowData_dances.csv"));
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
		return names.split(",");
	}

	public String getDanceGroupMembers(String name) {
		// get dance group data

		Set<String> danceGroupsData = new TreeSet<>(getCSV("src/csvFiles/danceShowData_danceGroups.csv"));
		
		// result by default is just name of given
		String result = name;

		// iterate iterate
		for (String line : danceGroupsData) {
			String[] splitByTab = line.split("\t");

			// if, at any point, name of dance group is equal to given name
			if (splitByTab[0].equals(name) ) {
				// return names of dancers in group 
				result = getDanceGroupMembers(splitByTab[1]);
			}
		}

		return result;
	}
	
	@Override
	public String listAllDancesAndPerformers() {
		
		// get CSV file for dances Data
		TreeSet<String> dancesData = getCSV("src/csvFiles/danceShowData_dances.csv");

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateRunningOrder(int gaps) {
		// TODO Auto-generated method stub
		return null;
	}

	public TreeSet<String> getCSV(String file) {

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
