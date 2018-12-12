package astaire;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ImplController implements Controller {

	@Override
	public String listAllDancersIn(String dance) {

		// get CSV file for dances Data
		ArrayList<String> dancesData = getCSV("src/csvFiles/danceShowData_dances.csv");

		String result = "";

		//for each line in dances csv file
		for (String line : dancesData) {
			
			//split into two sections - [0] is name of dance & [1] is dancers
			String[] splitByTab = line.split("\t");

			splitByTab[0] = splitByTab[0].trim();
			
			//if name of dance matches given dance name
			if (splitByTab[0].equals(dance)) {

				//split names of dancers into individual strings
				String[] separatedNames = splitByComma(splitByTab[1]);

				//iterate through names
				for (int i = 0; i < separatedNames.length; i++) {
					//append result with output of getDanceGroupMembers (and trim input)
						result += ", " + getDanceGroupMembers(separatedNames[i].trim());
				}
			}
		}

		//remove leading comma and space
		result = result.substring(2);
		
		return result;
	}

	public String[] splitByComma(String names) {
		return names.split(", ");
	}

	public String getDanceGroupMembers(String name) {
		//get dance group data
		ArrayList<String> danceGroupsData = getCSV("src/csvFiles/danceShowData_danceGroups.csv");
		
		//result by default is just name of given
		String result = name;

		//iterate iterate
		for (String line : danceGroupsData) {
			String[] splitByTab = line.split("\t");
			
			//if, at any point, name of dance group is equal to given name
			if (splitByTab[0].equals(name)) {
				//return names of dancers in group
				result = getDanceGroupMembers(splitByTab[1]);
			}
		}

		return result;
	}

	@Override
	public String listAllDancesAndPerformers() {
		// TODO Auto-generated method stub
		return null;
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

	public List<String> readOrderedFile(String filePath) throws IOException {
		String line = "";
		String csvSplitBy = "\t";
		String[] danceInfo = null;
		ArrayList<String> lines = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			line = br.readLine();

			while ((line = br.readLine()) != null) {
				danceInfo = line.split(csvSplitBy);
				for (String i : danceInfo) {
					lines.add(i.trim());
				}
			}
		} catch (FileNotFoundException e) {
		}
		return lines;
	}

	/**
	 * private ArrayList<String> csvToArray(String csv) { ArrayList<String> result =
	 * new ArrayList<String>();
	 * 
	 * if (csv != null) { String[] splitData = csv.split(","); for (int i = 0; i <
	 * splitData.length; i++) { if (!(splitData[i] == null) ||
	 * !(splitData[i].length() == 0)) { result.add(splitData[i].trim()); } } }
	 * 
	 * return result; }
	 **/

}
