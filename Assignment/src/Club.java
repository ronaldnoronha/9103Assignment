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
				//System.out.println(ListOfMembers.get(i).getPassType().isEmpty());
				out.println("name "+ListOfMembers.get(i).getName());
				if (!ListOfMembers.get(i).getBirthday().isEmpty()) out.println("birthday "+ListOfMembers.get(i).getBirthday());
				out.println("mobile "+ListOfMembers.get(i).getMobile());
				//if (!ListOfMembers.get(i).getPassType().isEmpty()) out.println("pass "+ListOfMembers.get(i).getPassType());
				if (ListOfMembers.get(i).getFee()>0){					
					out.printf("fee $%5.2f",ListOfMembers.get(i).getFee());					
					out.println();
				}
				if (!ListOfMembers.get(i).getAddress().isEmpty()) out.println("address "+ListOfMembers.get(i).getAddress());
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
	
}
