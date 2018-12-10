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
			
			String result = null;
			String trimmed = null;
			for(String line:danceShowData) {
				String[] splitByTab = line.split("\t");
				
				if (splitByTab[0].equals(dance)) {
					result = splitByTab[1];
					trimmed = result.trim();
					
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
