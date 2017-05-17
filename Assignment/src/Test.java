import java.util.*;
import java.io.*;

public class Test {

	public static void main(String[] args) {
		
		try{
			Club.deleteAll();
			File fw1 = new File(args[0]);
			File fw2 = new File(args[1]);
			Scanner memberslist = new Scanner(fw1);
			Scanner instructions = new Scanner(fw2);
			String line_read;
			String mobile="";
			String name="";
			String birthday_str = "";
			String address = "";
			String pass = "";
			double fee = 0;
			String word;
			String member_details ="";
			
			while (memberslist.hasNextLine()){
				line_read = memberslist.nextLine().trim();
				System.out.println(line_read);
				if (line_read.isEmpty()){
					if (validMobile(mobile) && checkName(name) && !Club.checkMemberExists(name, mobile) && !name.isEmpty() && !mobile.isEmpty()){						
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
						name="";
						mobile= "";
						birthday_str = "";
						address = "";
						fee = 0;
						pass = "";
					}
					else{
						name="";
						mobile= "";
						birthday_str = "";
						address = "";
						fee = 0;
						pass = "";
					}
				}
				else if (line_read.substring(0,6).equals("mobile")){
					mobile = line_read.substring(7,line_read.length());					
				} 
				else if (line_read.substring(0,4).equals("name")){
					name = line_read.substring(5,line_read.length());
				}
				else if (line_read.substring(0,5).equals("birth")){
					birthday_str = line_read.substring(9,line_read.length());
				}
				else if (line_read.substring(0,5).equals("addre")){
					address = line_read.substring(9,line_read.length());
				}
				else if (line_read.substring(0,3).equals("fee")){
					for (int i=0;i<line_read.length();i++){
						if(line_read.charAt(i) =='$'){
							fee = Double.parseDouble(line_read.substring(i+1,line_read.length()));
						}
					}
				}
				else if (line_read.substring(0,4).equals("pass")){
					pass = line_read.substring(5,line_read.length());
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
			Club.createResultsFile("results2.txt");
			
		}
		catch (Exception e){
			System.out.println("error: "+e.getMessage());
		}		
	}
	public static boolean validMobile(String mobile){
		boolean output = true;
		for (int i =0;i<mobile.length();i++){
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
			if (list[i].substring(0,6).equals("mobile")){
				mobile = list[i].substring(7,list[i].length());					
			} 
			else if (list[i].substring(0,4).equals("name")){
				name = list[i].substring(5,list[i].length());
			}
			else if (list[i].substring(0,5).equals("birth")){
				birthday_str = list[i].substring(9,list[i].length());
			}
			else if (list[i].substring(0,5).equals("addre")){
				address = list[i].substring(9,list[i].length());
			}
			else if (list[i].substring(0,3).equals("fee")){
				for (int j=0;j<list[i].length();j++){
					if(list[i].charAt(j) =='$'){
						fee = Double.parseDouble(list[i].substring(j+1,list[i].length()));
					}
				}
			}
			else if (list[i].substring(0,4).equals("pass")){
				pass = list[i].substring(5,list[i].length());
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
		System.out.println(name + " "+mobile);
		Club.deleteFromClub(name, mobile);
		
	}
}
