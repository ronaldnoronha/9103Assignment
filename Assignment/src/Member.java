//import java.util.*;

public class Member {
	private String name;
	private String birthday;
	private String mobile;
	private String passtype;
	private double fee;
	private String address;
	
	public Member(String nm, String phone){
		name = nm;
		mobile = phone;
		birthday = "";
	}
	public Member(String nm, String bd, String phone, String pass, double cost){
		name = nm;
		birthday = bd;
		mobile = phone;
		passtype = pass;
		fee = cost;
	}
	public void addBirthday(String birth){
		birthday = birth;
	}
	public void addAddress(String addr){
		address = addr;
	}
	public void addFee(double cost){
		fee = cost;
	}
	public void addPass(String pass){
		passtype = pass;
	}
	
	
	public String getName(){
		return name;
	}
	public String getMobile(){
		return mobile;
	}
	public String getBirthday(){
		return birthday;
	}
	public String getPassType(){
		return passtype;
	}
	public double getFee(){
		return fee;
	}
	public String getAddress(){
		return address;
	}
	

}
