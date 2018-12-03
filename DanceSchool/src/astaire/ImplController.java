package astaire;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ImplController implements Controller{

	@Override
	public String listAllDancersIn(String dance) {
		//get CSV file for danceShow Data
		
			ArrayList<String> danceShowData =  getCSV("src/csvFiles/danceShowData_dances.csv");
			
			for(String line:danceShowData) {
				String[] splitByComma=line.split(",");
				String[] splitBySpace=splitByComma[0].split("\\s+");
				
				
				
				//System.out.println(splitDanceShow[0]);
				String matchDance="";
				for(int i=0;i<splitBySpace.length-1;++i) {
				
					matchDance+=splitBySpace[i]+" ";
					if(splitBySpace.length-2!=i) {
						matchDance+=" ";
					}
				  
				}
			    //
				
				System.out.println(matchDance);
			}
			//ArrayList<String> filterDance=(ArrayList<String>) danceShowData.stream().filter(group->group.equals(dance)).collect(Collectors.toList()); 
		   // danceShowData.stream().filter(group->group.equals(dance)).collect(Collectors.toList()); 
			//System.out.println(filterDance);
		    return null;
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
	

	/**private ArrayList<String> csvToArray(String csv) {
		ArrayList<String> result = new ArrayList<String>();
		
		if (csv != null) {
			String[] splitData = csv.split(",");
			for (int i = 0; i < splitData.length; i++) {
				if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {
					result.add(splitData[i].trim());
				}
			}
		}
		
		return result;
	}**/

}
