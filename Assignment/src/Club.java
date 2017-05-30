import java.util.*;
import java.io.*;
public class Club {
	private static ArrayList<Member> ListOfMembers = new ArrayList<Member>();

	public static void addToClub(Member a){
		ListOfMembers.add(a);
	}
	public static void deleteFromClub(String a, String mobile){
		for (int i =0; i<ListOfMembers.size();i++){
			if (ListOfMembers.get(i).getName().trim().equals(a) && ListOfMembers.get(i).getMobile().trim().equals(mobile)){
				ListOfMembers.remove(i);
				break;
			}
		}
	}
	public static void createResultsFile(String a){
		try{
			File fw = new File(a);
			PrintWriter out = new PrintWriter(fw);			
			for (int i = 0; i<ListOfMembers.size();i++){
				out.println("name "+ListOfMembers.get(i).getName());				
				if (!ListOfMembers.get(i).getBirthday().isEmpty()) out.println("birthday "+ListOfMembers.get(i).getBirthday());
				out.println("mobile "+ListOfMembers.get(i).getMobile());				
				if (!ListOfMembers.get(i).getPassType().isEmpty()) out.println("pass "+ListOfMembers.get(i).getPassType());				
				if (ListOfMembers.get(i).getFee()>0){					
					out.printf("fee $%5.2f",ListOfMembers.get(i).getFee());					
					out.println();
				}
				if (!ListOfMembers.get(i).getAddress().isEmpty()) out.println("address "+ListOfMembers.get(i).getAddress());
				if (!ListOfMembers.get(i).getEmail().isEmpty()) out.println("email "+ListOfMembers.get(i).getEmail());
				out.println();
			}
			out.close();
		} catch (Exception e){
			System.out.println("error :"+e.getMessage());
		}		
	}
	public static void deleteAll(){
		ListOfMembers.clear();
	}
	public static boolean checkMemberExists(String name, String mob){
		boolean output = false;
		for (int i =0;i<ListOfMembers.size();i++){
			if (ListOfMembers.get(i).getName().equals(name) && ListOfMembers.get(i).getMobile().equals(mob)){
				output = true;
				break;
			}
		}
		return output;
	}

	public static Member getMember(String name, String mobile){
		for (int i =0;i<ListOfMembers.size();i++){
			if (ListOfMembers.get(i).getName().equals(name) && ListOfMembers.get(i).getMobile().equals(mobile)){
				return ListOfMembers.get(i);
			}
		}
		return null;
	}

	public static void updateMember(String name, String mobile,String instruction, String value){
		for (int i =0;i<ListOfMembers.size();i++){
			if (ListOfMembers.get(i).getName().equals(name) && ListOfMembers.get(i).getMobile().equals(mobile)){
				if (instruction.equals("birthday")){
					ListOfMembers.get(i).addBirthday(value);
				}
				else if (instruction.equals("pass")){
					ListOfMembers.get(i).addPass(value);
				}
				else if (instruction.equals("fee")){
					ListOfMembers.get(i).addFee(Double.parseDouble(value));
				}
				else if (instruction.equals("address")){
					ListOfMembers.get(i).addAddress(value);
				}
				else if (instruction.equals("email")){
					ListOfMembers.get(i).addEmail(value);
				}
			}
		}
		return;
	}
	public static void sortAscending(){
		for (int i =1;i<ListOfMembers.size();i++){
			Member temp  = ListOfMembers.get(i);
			int loc = i-1;
			while (loc>=0 && higherIndex(ListOfMembers.get(loc),temp)==1){
				ListOfMembers.set(loc+1,ListOfMembers.get(loc));
				loc--;
			}
			ListOfMembers.set(loc+1,temp);
		}
	}
	public static void sortDescending(){
		for (int i =1;i<ListOfMembers.size();i++){
			Member temp  = ListOfMembers.get(i);
			int loc = i-1;
			while (loc>=0 && higherIndex(ListOfMembers.get(loc),temp)==2){
				ListOfMembers.set(loc+1,ListOfMembers.get(loc));
				loc--;
			}
			ListOfMembers.set(loc+1,temp);
		}
	}

	private static int higherIndex(Member a, Member b){
		//higher of the two inputs. 
		String name1 = a.getName();
		String name2 = b.getName();
		int output=0;
		// check if one name is before the other
		if (name1.length()==name2.length()){
			for(int i =0;i<name1.length();i++){
				if (name1.charAt(i)>name2.charAt(i)){
					output = 1;					
					return output;
				}
				else if (name1.charAt(i)<name2.charAt(i)){
					output = 2;					
					return output;
				}
			}
		}
		else {
			for(int i =0;i<Math.min(name1.length(),name2.length());i++){
				if (name1.charAt(i)>name2.charAt(i)){
					output = 1;					
					return output;
				}
				else if (name1.charAt(i)<name2.charAt(i)){
					output = 2;					
					return output;
				}
			}
			if (output == 0 && name1.length()<name2.length()){
				output = 1;
				return output;
			}
			else if (output == 0 && name1.length()>name2.length()){
				output = 2;
				return output;
			}
		}

		if (output == 0){
			String number1 = a.getMobile();
			String number2 = b.getMobile();
			for(int i =0;i<name1.length();i++){
				if (number1.charAt(i)>number2.charAt(i)){
					output = 1;
					return output;
				}
				else if (number1.charAt(i)<number2.charAt(i)){
					output = 2;	
					return output;
				}
			}	
		}
		return output;
	}
	public static ArrayList<String> queryPassAge(){
		ArrayList<String> output = new Arraylist<String>();
		output.add("----query age fee----");
		output.add("Total Club Member size: "+ListOfMembers.size());
		output.add("Age based fee income distribution");
		for (int i=0;i<ListOfMembers.size();i++){
			
		}
		
		
	}
	public static int ageInYears(String bd){
		int age;
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		int t_month = cal.get(Calendar.MONTH)+1;
		int t_day = cal.get(Calendar.DAY_OF_MONTH);
		int t_year = cal.get(Calendar.YEAR);
		int bd_month = Integer.parseInt(bd.substring(3,5));
		int bd_day = Integer.parseInt(bd.substring(0,2));
		int bd_year = Integer.parseInt(bd.substring(6,10));
		System.out.println(bd_year);
		if (t_year!=bd_year) {
			if ((t_month>bd_month) || (t_day==bd_day && t_day>bd_day)) {
				age = t_year-bd_year;
				return age;
			}
			else {
				age = t_year-bd_year-1;
				return age;
			}
		}
		else {
			age = 0;
			return age;
		}		
	}

}
