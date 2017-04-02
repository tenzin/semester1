package javaProgrammingAssignment;

import java.util.*;

public class Test {
	
	public static final int NUM_DAYS_NORMAL_YEAR = 365;
	public static final int NUM_DAYS_LEAP_YEAR = 366;
	public static final int[] NUMBER_OF_MONTH_DAYS = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	//Function to calculate number of days in the birth year
	public static int calculateDaysBirthYear(int iDate, int iMonth, int iYear) {
		int iNumDays = 0;
		//First calculate number of days in the birth month
		iNumDays = NUMBER_OF_MONTH_DAYS[iMonth - 1] - iDate + 1; // +1 is to make the birth date inclusive
		System.out.println("Number of days in birth month = " + iNumDays);
		
		//Now calculate remaining number of days in birth year
		for(int iI = iMonth + 1; iI <= 12; iI++)
			iNumDays += NUMBER_OF_MONTH_DAYS[iI - 1];
		
		//Check for LEAP year && February month. If TRUE add 1 to iNumDays;
		if(new GregorianCalendar().isLeapYear(iYear) && iMonth == 2)
			iNumDays++;
		System.out.println("Number of days in birth year = " + iNumDays);
		return(iNumDays);
	}
	
	//Function to calculate number of days in the given year
	public static int calculateDaysGivenYear(int iDate, int iMonth, int iYear) {
		int iNumDays = 0;
		//Calculate number of days from January to iMonth - 1
		for(int iI = 0; iI < iMonth - 1; iI++)
			iNumDays += NUMBER_OF_MONTH_DAYS[iI];
		
		//Add the days in given date
		iNumDays += iDate;
		
		//Check for LEAP year && month beyond February. If TRUE add 1 to iNumDays
		if(new GregorianCalendar().isLeapYear(iYear) && iMonth > 2)
			iNumDays++;
		System.out.println("Total number of days in given year = " + iNumDays);
		return(iNumDays);
	}
	
	//Function to calculate number of days in full years between birth year and given year
	public static int calculateDaysFullYears(int iNumNormalYear, int iNumLeapYear) {
		return((iNumNormalYear * NUM_DAYS_NORMAL_YEAR) + (iNumLeapYear * NUM_DAYS_LEAP_YEAR));
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] iaDateOfBirth = {30, 6, 1898};
		int[] iaGivenDate = {11,11,2010};
		int iNumFullYear = 0;
		int iNumLeapYear = 0;
		int iNumNormalYear = 0;
		int iNumDaysAlive = 0;
		// First calculate the number of full years. If we omit the birth year and the given year, the years in between are full years :)
		iNumFullYear = iaGivenDate[2] - iaDateOfBirth[2] - 1;
		
		//Now calculate the number of leap years in those full years
		for(int iI=iaDateOfBirth[2] + 1; iI < iaGivenDate[2]; iI++) {
			if(new GregorianCalendar().isLeapYear(iI)) {
				System.out.println("Leap Year: " + iI);
				iNumLeapYear++;
			}
		}
		
		iNumNormalYear = iNumFullYear - iNumLeapYear;
		
		System.out.println("Full year, Normal Years, Leap Years = " + iNumFullYear + " " + iNumNormalYear + " " + iNumLeapYear);
		
		iNumDaysAlive = calculateDaysBirthYear(iaDateOfBirth[0], iaDateOfBirth[1], iaDateOfBirth[2]) + calculateDaysGivenYear(iaGivenDate[0], iaGivenDate[1], iaGivenDate[2]) + calculateDaysFullYears(iNumNormalYear, iNumLeapYear); 
		System.out.println("Number of days alive = " + iNumDaysAlive);

	}

}
