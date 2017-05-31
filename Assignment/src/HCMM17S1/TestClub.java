package HCMM17S1;
import java.util.*;
import java.text.*;
import java.io.*;
public class TestClub {

	public static void main(String[] args) {
		//Club.deleteAll();
		//Club.createResultsFile("results1.txt");
		char a = 'a';
		char b = 'b';
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int year = cal.get(Calendar.YEAR);
		//System.out.println(year);
		
		
		try{
			
			// Designed test to check output files
			File fw1 = new File(args[0]+".txt");
			//System.out.println(("abcd.123@gmail.com").indexOf('@'));
			File fw2  = new File(args[1]+".txt");
			//System.out.println(true);
			Scanner output = new Scanner(fw1);
			Scanner sample = new Scanner(fw2);
			String line = "";
			String line2 = "";
			int i = 0;
			while (output.hasNextLine()){
				i++;
				System.out.println(i);
				line = output.nextLine();
				line2 = sample.nextLine();
				
				//System.out.println(line);
				if (!line.equals(line2)){
					System.out.println("mismatch on line "+ i);	
					System.out.println(line);
					System.out.println(line2);
				}
			}
			if (sample.hasNextLine()) System.out.println("missing information");

		} catch (Exception e){
			System.out.println("error: "+e.getMessage());
		}		

		Club.deleteAll();



	}

}
