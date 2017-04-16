package javaProgrammingAssignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class Test {
	
	public static class Dates {
		String sBirthDate;
		String sGivenDate;
		
		public Dates(String sBirthDate, String sGivenDate) {
			this.sBirthDate = sBirthDate;
			this.sGivenDate = sGivenDate;
		}
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Hashtable<String, Dates> hPeople = new Hashtable<String, Dates>();
		String[] saItem;
		BufferedReader in = new BufferedReader(new FileReader("dates.txt"));
		String sLine;
		while((sLine = in.readLine()) != null) {
			saItem = sLine.split(";");
			hPeople.put(saItem[0], new Dates(saItem[1], saItem[2]));
		}
		
		for(String key: hPeople.keySet())
			System.out.println("Name: " + key + "DOB: " + hPeople.get(key).sBirthDate + "GIVENDATE: " + hPeople.get(key).sGivenDate );
		in.close();
	}

}
