import java.util.*;
import java.io.*;

public class Test {

	public static void main(String[] args) {
		
		try{
			// Clearing the clublist before each run
			Club.deleteAll();
			// Opening the members and instructions files
			File fw1 = new File(args[0]);
			File fw2 = new File(args[1]);
			Scanner memberslist = new Scanner(fw1);
			Scanner instructions = new Scanner(fw2);
			
			// Variables for reading and writing methods
			String line_read;
			String mobile="";
			String name="";
			String birthday_str = "";
			String address = "";
			String pass = "";
			double fee = 0;
			String word;
			String member_details ="";
			
			
			// Read Members file and pass to convertMemberString()
			while (memberslist.hasNextLine()){
				line_read = memberslist.nextLine().trim();
				
				if (!line_read.equals("")){
					member_details+=line_read+" ";
				}
				else {
					if(!member_details.isEmpty()){
						convertMemberString(member_details);
					}
					member_details="";
				}								
			}
				
			while (instructions.hasNext()){
				word  = instructions.next().trim();
				if (word.equals("add")){
					addMember(instructions.nextLine());	
				}
				else if (word.equals("delete")){
					deleteMember(instructions.nextLine());
				}				
			}
			memberslist.close();
			instructions.close();
			Club.createResultsFile(args[2]);
			
		}
		catch (Exception e){
			System.out.println("error: "+e.getMessage());
		}		
	}
	
	// check if mobile number is all digits
	public static boolean validMobile(String mobile){
		boolean output = true;
		for (int i =0;i<mobile.length();i++){
			
			// change to have isDigit()
			if (mobile.charAt(i)=='0' || mobile.charAt(i)=='1' || mobile.charAt(i)=='2' || mobile.charAt(i)=='3' ||
					mobile.charAt(i)=='4' ||mobile.charAt(i)=='5' ||mobile.charAt(i)=='6' ||mobile.charAt(i)=='7' ||
					mobile.charAt(i)=='8' || mobile.charAt(i)=='9'){
				
			}
			else{
				output = false;
				break;
			}
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
		
		if (day>0 && day<=31 && month>0 && month<=12 && year >0){			
			output = day+"/"+month+"/"+year;
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
	
	public static void convertMemberString(String line){
		System.out.println(line);
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
				System.out.println(instruction);
				addMember(instruction);	
			
			
			
			
			
			/*
			if (list[i].equals("name")){
				run_thru = true;
				i++;
				while (run_thru && i<list.length){
					//System.out.println(list[i]);
					for (int j=0;j<keywords.length;j++){
						if (list[i].equals(keywords[j])) run_thru = false;
					}
					if (run_thru){
						name+=list[i]+" ";
						i++;
					}
				}
				
			}
			else if (list[i].equals("mobile")){
				run_thru = true;
				i++;
				while (run_thru && i<list.length){
					System.out.println(list[i]);
					for (int j=0;j<keywords.length;j++){
						if (list[i].equals(keywords[j])) run_thru = false;
					}
					if (run_thru){
						mobile+=list[i]+" ";
						i++;
					}
					//System.out.println(mobile);
				}
				
			}
			else if (list[i].equals("birthday")){
				run_thru = true;
				i++;
				while (run_thru && i<list.length){
					for (int j=0;j<keywords.length;j++){
						if (list[i].equals(keywords[j])) run_thru = false;
					}
					if (run_thru){
						birthday+=list[i]+" ";
						i++;
					}
				}
				
			}
			else if (list[i].equals("address")){
				run_thru = true;
				i++;
				while (run_thru && i<list.length){
					for (int j=0;j<keywords.length;j++){
						if (list[i].equals(keywords[j])) run_thru = false;
					}
					if (run_thru){
						address+=list[i]+" ";
						i++;
					}
				}				
			}
			else if (list[i].equals("fee")){
				run_thru = true;
				i++;
				while (run_thru && i<list.length){
					//System.out.println(list[i]);
					for (int j=0;j<keywords.length;j++){
						if (list[i].equals(keywords[j])) run_thru = false;
					}
					if (run_thru){
						fee+=list[i];
						i++;
					}
				}
				//System.out.println(fee);
			}
			else if (list[i].equals("pass")){
				run_thru = true;
				i++;
				while (run_thru && i<list.length){			
					for (int j=0;j<keywords.length;j++){
						if (list[i].equals(keywords[j])) run_thru = false;
					}
					if (run_thru){
						pass+=list[i]+" ";
						i++;
					}
					//System.out.println(i);
					//System.out.println(list.length);
				}				
			}
			else if (list[i].equals("email")){
				run_thru = true;
				i++;
				while (run_thru && i<list.length){
					for (int j=0;j<keywords.length;j++){
						if (list[i].equals(keywords[j])) run_thru = false;
					}
					if (run_thru){
						email+=list[i]+" ";
						i++;
					}
				}
			}
		}*/
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
		double fee = 0;
		for (int i =0;i<list.length;i++){
			list[i] = list[i].trim();
			String[] words = list[i].split(" ");
			if (words[0].trim().equals("mobile")){
				for (int j = 1; j<words.length;j++){
					mobile+=words[j].trim()+" ";
				}
				mobile = mobile.trim();
				//mobile = list[i].substring(7,list[i].length());					
			} 
			else if (words[0].trim().equals("name")){
				for (int j = 1; j<words.length;j++){
					name+=words[j].trim()+" ";
				}
				name = name.trim();
				//name = list[i].substring(5,list[i].length());
			}
			else if (words[0].trim().equals("birthday")){
				for (int j = 1; j<words.length;j++){
					birthday_str+=words[j].trim()+" ";
				}
				birthday_str = birthday_str.trim();
				//birthday_str = list[i].substring(9,list[i].length());
			}
			else if (words[0].trim().equals("address")){
				for (int j = 1; j<words.length;j++){
					address+=words[j].trim()+" ";
				}
				address = address.trim();
				//address = list[i].substring(9,list[i].length());
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
				//pass = list[i].substring(5,list[i].length());
			}
			
		}
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
			to_add.addAddress(address);
			if (!pass.isEmpty()) to_add.addPass(pass);
			
			Club.addToClub(to_add); 
		}	
	}
	public static void deleteMember(String instruction){
		instruction = instruction.trim();
		String[] list = instruction.split(";");
		String name = "";
		String mobile = "";
		name = list[0].trim();
		mobile = list[1].trim();
		//System.out.println(name + " "+mobile);
		Club.deleteFromClub(name, mobile);
		
	}
}
