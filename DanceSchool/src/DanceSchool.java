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

	public static void main(String args[]) throws FileNotFoundException { //create a new controller and run it

		Controller controller = new ImplController();
		new TUI(controller);

	}


}
