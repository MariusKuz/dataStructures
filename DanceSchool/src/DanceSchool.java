import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import astaire.Controller;
import astaire.ImplController;
import astaire.TUI;

public class DanceSchool {

	public DanceSchool() {

	}

	public static void main(String args[]) throws FileNotFoundException {

		Controller controller = new ImplController();
		new TUI(controller);

	}

	public static ArrayList<String> csvToArray(String csv) {
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
	}
}
