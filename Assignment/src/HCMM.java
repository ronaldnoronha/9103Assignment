import java.util.*;
import java.io.*;

public class HCMM {

	public static void main(String[] args) {

		try{
			// Clearing the clublist before each run
			Club.deleteAll();
			// Opening the members and instructions files
			File fw1 = new File(args[0]+".txt");
			File fw2 = new File(args[1]+".txt");
			Scanner memberslist = new Scanner(fw1);
			Scanner instructions = new Scanner(fw2);

			// Variables for reading and writing methods
			String line_read;
			String word;
			String member_details ="";


			// Read Members file and pass to convertMemberString()
			while (memberslist.hasNextLine()){
				line_read = memberslist.nextLine().trim();
				if (!line_read.isEmpty()){
					member_details+=line_read+" ";
				}
				else {
					if(!member_details.isEmpty()){
						convertMemberString(member_details);
						member_details="";						
					}					
				}								
			}
			if(!member_details.isEmpty()){
				convertMemberString(member_details);
				member_details="";
			}					

			while (instructions.hasNext()){
				word  = instructions.next().trim();
				if (word.equals("add")){
					addMember(instructions.nextLine());	
				}
				else if (word.equals("delete")){
					deleteMember(instructions.nextLine());
				}
				else if (word.equals("sort")){					
					String order = instructions.next().trim();				
					if (order.equals("ascending")){
						Club.sortAscending();
					}
					else if (order.equals("descending")){					
						Club.sortDescending();
					}
				}			
			}
			memberslist.close();
			instructions.close();
			Club.createResultsFile(args[2]+".txt");

		}
		catch (Exception e){
			System.out.println("error: "+e.getMessage());
		}		
	}

	// check if mobile number is all digits
	public static boolean validMobile(String mobile){
		boolean output = true;
		if (mobile.length()==8){
			for (int i =0;i<mobile.length();i++){
				// change to have isDigit()
				if (!Character.isDigit(mobile.charAt(i))){
					output = false;
					return output;
				}
			}
		}
		else{
			output = false;
		}
		return output;
	}

	// Make sure birthday is as dd-mm-yy or dd-mm-yyyy or d-m-yy...
	public static String validBirthday(String bd){
		String output = "";
		int day = 0;
		int month = 0;
		int year = 0;
		int first_index = 0;
		int second_index = 0;
		String day_str = "";
		String month_str = "";
		boolean valid_birthday = true;
		//day
		for (int i=0;i<bd.length();i++){
			if (bd.charAt(i)=='/' || bd.charAt(i)=='-'){
				day = Integer.parseInt(bd.substring(0, i));
				first_index = i;
				break;
			}
		}
		for (int i=first_index+1;i<bd.length();i++){
			if (bd.charAt(i)=='/' || bd.charAt(i)=='-'){
				month = Integer.parseInt(bd.substring(first_index+1, i));
				second_index = i;

				break;
			}
		}
		year = Integer.parseInt(bd.substring(second_index+1, bd.length()));


		// checking validity by months
		if (((month==4 || month ==6 ||month==9||month == 11) && !(day<=30)) || (month == 2 && year%4==0 && !(day<=29)) || (month == 2 && year%4!=0 && !(day<=28))) valid_birthday = false;

		// add 0 in from of single digit numbers
		if (day<=9){
			day_str = "0"+day;
		}
		else{
			day_str = ""+day;
		}
		if (month<=9){
			month_str = "0"+month;
		}
		else{
			month_str = ""+month;
		}

		if (day>0 && day<=31 && month>0 && month<=12 && year >0 && valid_birthday){			
			output = day_str+"/"+month_str+"/"+year;
		}

		return output;
	}
	public static boolean checkName(String nm){
		boolean output = true;
		for (int i = 0;i<nm.length();i++){		
			if (!Character.isLetter(nm.charAt(i)) && !(nm.charAt(i)==' ')){
				output = false;
				break;
			}
		}
		return output;
	}
	public static boolean validAddress(String address){
		boolean output = true;
		// Only anomaly is postcode being numbers and Street number present
		String[] words = address.split(" ");

		if (!Character.isDigit(words[0].charAt(0))){
			output = false;
			return output;
		}
		for (int i = 0; i<words[words.length-1].length();i++){
			if (!Character.isDigit(words[words.length-1].charAt(i))){
				output = false;
				return output;
			}
		}
		return output;
	}

	public static void convertMemberString(String line){		
		String[] list = line.trim().split(" ");
		for (int i=0;i<list.length;i++){
			list[i] = list[i].trim();
			//System.out.println(list[i]);
		}

		String name = "";
		String mobile = "";
		String birthday = "";
		String address = "";
		String fee = "";
		String pass ="";
		String email = "";
		boolean run_thru = true;
		boolean keyword_found = false;
		String last_keyword = "";
		int i =0;
		String[] keywords = {"name","mobile","birthday","address","fee","pass","email"};

		while (i<list.length){			
			for (int j=0;j<keywords.length;j++){
				if (list[i].equals(keywords[j])){
					last_keyword = list[i];
					keyword_found = true;
				}
			}
			if (!keyword_found){
				if (last_keyword.equals("name")){
					name+=list[i]+" ";
				}
				else if (last_keyword.equals("mobile")){
					mobile+=list[i]+" ";
				}
				else if (last_keyword.equals("birthday")){
					birthday+=list[i]+" ";
				}
				else if (last_keyword.equals("address")){
					address+=list[i]+" ";
				}
				else if (last_keyword.equals("fee")){
					fee+=list[i]+" ";
				}
				else if (last_keyword.equals("pass")){
					pass+=list[i]+" ";
				}
				else if (last_keyword.equals("email")){
					email+=list[i]+" ";
				}

			}
			else {
				keyword_found =false;
			}
			i++;
		}
		// Create instruction for addMember to read
		String instruction = "";
		instruction = "name "+name+";"+"mobile "+mobile;
		if (!birthday.isEmpty()){
			instruction+=";birthday "+birthday;
		}
		if (!address.isEmpty()){
			instruction+=";address "+address;
		}
		if (!fee.isEmpty()){
			instruction+=";fee "+fee;
		}
		if (!pass.isEmpty()){
			instruction+=";pass "+pass;
		}
		if (!email.isEmpty()){
			instruction+=";email "+email;
		}
		addMember(instruction);	
	}
	public static void addMember(String instruction){
		instruction = instruction.trim();
		// break into smaller pieces based on ';'
		String[] list = instruction.split(";"); 
		String name = "";
		String mobile = "";
		String birthday_str = "";
		String pass = "";
		String address = "";
		String email = "";
		double fee = 0;


		for (int i =0;i<list.length;i++){
			list[i] = list[i].trim();
			String[] words = list[i].split(" ");
			if (words[0].trim().equals("mobile")){
				for (int j = 1; j<words.length;j++){
					mobile+=words[j].trim()+" ";
				}
				mobile = mobile.trim();									
			} 
			else if (words[0].trim().equals("name")){
				for (int j = 1; j<words.length;j++){
					name+=words[j].trim()+" ";
				}
				name = name.trim();			
			}
			else if (words[0].trim().equals("birthday")){
				for (int j = 1; j<words.length;j++){
					birthday_str+=words[j].trim()+" ";
				}
				birthday_str = birthday_str.trim();
			}
			else if (words[0].trim().equals("address")){
				for (int j = 1; j<words.length;j++){
					address+=words[j].trim()+" ";
				}
				address = address.trim();			
			}
			else if (words[0].trim().equals("fee")){
				for (int j=0;j<list[i].length();j++){
					if(list[i].charAt(j) =='$'){
						fee = Double.parseDouble(list[i].substring(j+1,list[i].length()));
					}
				}
			}
			else if (words[0].trim().equals("pass")){
				for (int j = 1; j<words.length;j++){
					pass+=words[j].trim()+" ";
				}
				pass = pass.trim();
			}
			else if (words[0].trim().equals("email")){
				for (int j = 1; j<words.length;j++){
					email+=words[j].trim()+" ";
				}
				email = email.trim();
			}
		}

		// adding to the club
		if (!name.isEmpty() && !mobile.isEmpty() && checkName(name) && validMobile(mobile) && !Club.checkMemberExists(name, mobile)){
			Member to_add = new Member(name,mobile);
			if (!birthday_str.isEmpty()){
				if (!validBirthday(birthday_str).isEmpty()){					
					to_add.addBirthday(validBirthday(birthday_str));
				}							
			}
			if (fee>0){
				to_add.addFee(fee);
			}
			if (!address.isEmpty() && validAddress(address)) to_add.addAddress(address);
			if (!pass.isEmpty() && validPass(pass)) to_add.addPass(pass);
			if (!email.isEmpty()) to_add.addEmail(email);
			Club.addToClub(to_add); 
		}
		// If already a member then update details
		else if (Club.checkMemberExists(name, mobile)){
			if (!birthday_str.isEmpty()){
				if (!validBirthday(birthday_str).isEmpty()){					
					Club.getMember(name, mobile).addBirthday(validBirthday(birthday_str));
				}							
			}
			if (fee>0){
				Club.getMember(name, mobile).addFee(fee);
			}
			if (!address.isEmpty() && validAddress(address)) Club.getMember(name, mobile).addAddress(address);
			if (!pass.isEmpty() && validPass(pass)) Club.getMember(name, mobile).addPass(pass);
			if (!email.isEmpty()) Club.getMember(name, mobile).addEmail(email);
		}
	}

	public static boolean validPass(String pass){
		if (pass.equals("Bronze") || pass.equals("Silver") || pass.equals("Gold")){
			return true;
		}
		else {
			return false;
		}
	}
	public static void deleteMember(String instruction){
		instruction = instruction.trim();
		String[] list = instruction.split(";");
		String name = "";
		String mobile = "";
		name = list[0].trim();
		mobile = list[1].trim();
		Club.deleteFromClub(name, mobile);

	}
}
