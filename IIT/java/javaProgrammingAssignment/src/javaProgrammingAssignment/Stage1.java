/*
 * Stage1.java
 * Author: Tenzin Dendup (u3149399)
 * Date Created: 31 March 2017
 * Date Last Changed: 6 April 2017
 * This is a Java console application to calculate number of days alive.
 * It is stage 1 of PPIT Java Assignment, University of Canberra.
 * Input: 2 sets of dates (Date of birth and another date)
 * Output: Number of days alive from date of birth to second date
 * 
 */

package javaProgrammingAssignment;

import java.util.GregorianCalendar; //To use Java in-built method to test for Leap year
import java.util.Scanner;

public class Stage1 {
	
	public static final int NUM_DAYS_NORMAL_YEAR = 365;
	public static final int NUM_DAYS_LEAP_YEAR = 366;
	public static final int[] NUMBER_OF_MONTH_DAYS = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	/* Function to calculate number of days in the birth year */
	public static int calculateDaysBirthYear(int iDay, int iMonth, int iYear) {
		int iNumDays = 0;
		
		//First calculate number of days in the birth month
		iNumDays = NUMBER_OF_MONTH_DAYS[iMonth - 1] - iDay + 1; // +1 is to make the birth date inclusive
		
		//Now calculate remaining number of days in birth year
		for(int iI = iMonth + 1; iI <= 12; iI++)
			iNumDays += NUMBER_OF_MONTH_DAYS[iI - 1];
		
		//Check for LEAP year && February month. If TRUE add 1 to iNumDays;
		if(new GregorianCalendar().isLeapYear(iYear) && iMonth == 2)
			iNumDays++;

		return(iNumDays);
	}
	
	/* Function to calculate number of days in the given year */
	public static int calculateDaysGivenYear(int iDay, int iMonth, int iYear) {
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
	
	/* Function to calculate number of days in full years between birth year and given year */
	public static int calculateDaysFullYears(int iNumNormalYear, int iNumLeapYear) {
		return((iNumNormalYear * NUM_DAYS_NORMAL_YEAR) + (iNumLeapYear * NUM_DAYS_LEAP_YEAR));
	}
	
	public static void main(String[] args) {
		int iNumFullYear = 0;
		int iNumLeapYear = 0;
		int iNumNormalYear = 0;
		int iNumDaysAlive = 0;
		
		Scanner inConsole = new Scanner(System.in);
		
		/* Read day, month and year of date of birth */
		System.out.println("Enter the day of the Date of Birth: ");
		int iBirthDay = inConsole.nextInt();
		System.out.println("Enter the month of the Date of Birth: ");
		int iBirthMonth = inConsole.nextInt();
		System.out.println("Enter the year of the of the Date of Birth: ");
		int iBirthYear = inConsole.nextInt();
		
		/* Read day, month and year of given date */
		System.out.println("Enter the day of the Given date: ");
		int iGivenDay = inConsole.nextInt();
		System.out.println("Enter the month of the Given Date: ");
		int iGivenMonth = inConsole.nextInt();
		System.out.println("Enter the year of the of the Given Date: ");
		int iGivenYear = inConsole.nextInt();
		
		// Calculate the number of full years. If we omit the birth year and the given year, the years in between are full years
		iNumFullYear = iGivenYear - iBirthYear - 1;
		
		// Now calculate the number of leap years in those full years
		for(int iI=iBirthYear + 1; iI < iGivenYear; iI++) {
			if(new GregorianCalendar().isLeapYear(iI))
				iNumLeapYear++;
		}
		
		iNumNormalYear = iNumFullYear - iNumLeapYear;
		
		//Number of Days alive is the sum of days in birth year, given year and full years in between
		iNumDaysAlive = calculateDaysBirthYear(iBirthDay, iBirthMonth, iBirthYear) + calculateDaysGivenYear(iGivenDay, iGivenMonth, iGivenYear) + calculateDaysFullYears(iNumNormalYear, iNumLeapYear); 
		
		System.out.println("Date of birth: " + iBirthDay + "/" + iBirthMonth + "/" + iBirthYear);
		System.out.println("Given date: " + iGivenDay + "/" + iGivenMonth + "/" + iGivenYear);
		System.out.println("Number of days alive = " + iNumDaysAlive);
		
		inConsole.close();
	}

}
