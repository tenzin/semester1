/*
 * Person.java
 * Author: Tenzin Dendup (u3149399)
 * Date Created: 14 April 2017
 * Date Last Changed: 19 April 2017
 * This is java source code of Person object.
 * When a person Object is created, its iDaysAlive is also calculated.
 * It is used by Stage3.java and Stage4.java files for IIT Java Assignment, University of Canberra.
 * 
 */

package javaProgrammingAssignment;

import java.util.GregorianCalendar;

public class Person {
	
	//Constants
	static final int NUM_DAYS_NORMAL_YEAR = 365;
	static final int NUM_DAYS_LEAP_YEAR = 366;
	static final int[] NUMBER_OF_MONTH_DAYS = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	private String sName;
	private MyDate birthDate;
	private MyDate givenDate;
	private int iDaysAlive;
	
	//Constructor creates Person Object
	public Person(String sName, MyDate birthDate, MyDate givenDate) {
		this.sName = sName;
		this.birthDate = birthDate;
		this.givenDate = givenDate;
		//Number of days alive is calculated when the Person object is created by calling a private method
		this.iDaysAlive=calculateDaysAlive();
	}

	//Private method to calculate number of days alive when Person object is created
	private int calculateDaysAlive() {
		
		int iNum = 0;
		
		int iNumFullYear = 0;
		int iNumLeapYear = 0;
		int iNumNormalYear = 0;
		
		int iGivenDay = givenDate.getDay();
		int iGivenMonth = givenDate.getMonth();
		int iGivenYear = givenDate.getYear();
		int iBirthDay = birthDate.getDay();
		int iBirthMonth = birthDate.getMonth();
		int iBirthYear = birthDate.getYear();
		
		if(iBirthYear == iGivenYear) { //birth year and given year are same
			iNum = calculateDaysBirthYear(iBirthDay, iBirthMonth, iBirthYear) - 
					calculateDaysBirthYear(iGivenDay, iGivenMonth, iGivenYear) + 1;
		}
		
		else {
			if(iGivenYear == iBirthYear + 1) //Birth Year and given Years are consecutive years
				iNum = calculateDaysBirthYear(iBirthDay, iBirthMonth, iBirthYear) + 
								calculateDaysGivenYear(iGivenDay, iGivenMonth, iGivenYear);
			else {
				if(iGivenYear > iBirthYear + 1) { //There is at least one year between birth year and given year
					//Calculate number of years in between
					iNumFullYear = iGivenYear - iBirthYear - 1;
					//Calculate number of leap years
					for(int iI=iBirthYear + 1; iI < iGivenYear; iI++) { 
						if(new GregorianCalendar().isLeapYear(iI))
							iNumLeapYear++;
					//Calculate number of Normal years
					iNumNormalYear = iNumFullYear - iNumLeapYear;
					//Get result
					iNum = calculateDaysBirthYear(iBirthDay, iBirthMonth, iBirthYear) + 
							calculateDaysGivenYear(iGivenDay, iGivenMonth, iGivenYear) + 
							calculateDaysFullYears(iNumNormalYear, iNumLeapYear);
					}	
				}
			}
		}
		
		return(iNum);
		
	}
	
	private static int calculateDaysBirthYear(int iDay, int iMonth, int iYear) {
		int iNumDays = 0;
		
		//First calculate number of days in the birth month
		iNumDays = NUMBER_OF_MONTH_DAYS[iMonth - 1] - iDay + 1; // +1 is to make the birth date inclusive
		
		//Now calculate remaining number of days in birth year
		for(int iI = iMonth + 1; iI <= 12; iI++)
			iNumDays += NUMBER_OF_MONTH_DAYS[iI - 1];
		
		//Check for LEAP year && month feb or before feb. If TRUE add 1 to iNumDays;
		if(new GregorianCalendar().isLeapYear(iYear) && iMonth <= 2)
			iNumDays++;
		return(iNumDays);
	}
	
	private static int calculateDaysGivenYear(int iDay, int iMonth, int iYear) {
		int iNumDays = 0;
		
		//Calculate number of days from January to iMonth - 1
		for(int iI = 0; iI < iMonth - 1; iI++)
			iNumDays += NUMBER_OF_MONTH_DAYS[iI];
		
		//Add the days in given month which is iDate
		iNumDays += iDay;
		
		//Check for LEAP year && month beyond February. If TRUE add 1 to iNumDays
		if(new GregorianCalendar().isLeapYear(iYear) && iMonth > 2)
			iNumDays++;
		return(iNumDays);
	}
	
	private static int calculateDaysFullYears(int iNumNormalYear, int iNumLeapYear) {
		return((iNumNormalYear * NUM_DAYS_NORMAL_YEAR) + (iNumLeapYear * NUM_DAYS_LEAP_YEAR));
	}
	
	public String getName() {
		return sName;
	}
	
	public int[] getBirthDate() {
		return(birthDate.getDate());
	}
	
	public int[] getGivenDate() {
		return(givenDate.getDate());
	}
	
	public MyDate getGivenDateObject() {
		return givenDate;
	}
	
	public String getBirthDateFormatted() {
		return(birthDate.getDateFormatted());
	}
	
	public String getGivenDateFormatted() {
		return(givenDate.getDateFormatted());
	}
	
	public int getDaysAlive() {
		return iDaysAlive;
	}
	
	public void setGivenDate(int iDay, int iMonth, int iYear) {
		givenDate.setDate(iDay, iMonth, iYear);
		//Since this method sets givenDate to a new date, iDaysAlive is also updated accordingly
		iDaysAlive = calculateDaysAlive();
	}
}
