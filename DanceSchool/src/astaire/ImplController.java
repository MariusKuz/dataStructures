package astaire;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ImplController implements Controller{

	@Override
	public String listAllDancersIn(String dance) {
		//get CSV file for danceShow Data
		
			ArrayList<String> danceShowData = getCSV("src/csvFiles/danceShowData_dances.csv");
			ArrayList<String> danceGroupData = getCSV("src/csvFiles/danceShowData_danceGroups.csv");
			String result = "";
			String trimmed = "";


			
			
			for(String line:danceShowData) {
				String[] splitDanceByTab = line.split("\t"); //split dances by tabs ( [0] dance name [1] performers
				



				if (splitDanceByTab[0].equals(dance)) {
					
					
					
					String[] splitDanceByComma =  splitDanceByTab[1].split(","); //split dancers in the dance [0] first performer, [1] second etc

					
					for (String performer:splitDanceByComma) {
						result += performer;
						trimmed = result.trim();
					}
					
					
					
					
					/**
					for(String groupLine : danceGroupData) { //for every line of DANCE GROUPS
					String[] splitGroupByTab = groupLine.split("\t"); //split it by tabs


					
					
					
					
					if (splitDanceByComma[1].equals(splitGroupByTab[0])) { //IF [1] -> PERFORMER is equal TO [0] -> DANCE GGROUPS
						result += splitGroupByTab[1]; //ADD PERFORMERS IN THAT DANCE TO RESULT FROM GROUP CSV
						trimmed = result.trim();
					}
					
					else         //IDEK PLS SOMEONE SHOOT ME
						result += splitDanceByComma[1];
						trimmed = result.trim();
					
					

				}
					
					
					**/
					
				}
			}
		    return trimmed;
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
		
		//Print program menu and loop till the user exit
		
       BufferedReader buffer = null;
       ArrayList<String> data= new ArrayList<>();	
		try {
			String line;
			buffer = new BufferedReader(new FileReader(file));
			buffer.readLine();
			while ((line = buffer.readLine()) != null) {
				
				//System.out.println( csvToArray(line));
				data.add(line);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (buffer != null) buffer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
		
	}
	
  
  


}
