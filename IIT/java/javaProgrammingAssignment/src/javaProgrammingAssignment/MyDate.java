/*
 * MyDate.java
 * Author: Tenzin Dendup (u3149399)
 * Date Created: 14 April 2017
 * Date Last Changed: 17 April 2017
 * This is java source code of MyDate object
 * It is used by Stage3.java and Stage4.java files for IIT Java Assignment, University of Canberra.
 * 
 */

package javaProgrammingAssignment;

public class MyDate {
	private int iDay;
	private int iMonth;
	private int iYear;
	
	public MyDate(int iDay, int iMonth, int iYear) {
		this.iDay = iDay;
		this.iMonth = iMonth;
		this.iYear = iYear;
	}
	
	public int getDay() {
		return(iDay);
	}
	
	public int getMonth() {
		return iMonth;
	}
	
	public int getYear() {
		return iYear;
	}
	
	//Method to return set of date as integer Array
	public int[] getDate() {
		int[] iaDate = {iDay, iMonth, iYear};
		return(iaDate);
	}
	
	//Method to return string with date formatted as dd/mm/yy
	public String getDateFormatted() {
		return(iDay + "/" + iMonth + "/" + iYear);
	}
	
	//Method to set Date
	public void setDate(int iDay, int iMonth, int iYear) {
		this.iDay = iDay;
		this.iMonth = iMonth;
		this.iYear = iYear;
	}
	
	//Method that checks if two MyDate objects are equal or not
	public boolean equals(MyDate date) {
		if(this.iDay == date.iDay && this.iMonth == date.iMonth && this.iYear == date.iYear)
			return true;
		else
			return false;
	}
}