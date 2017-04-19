/*
 * Stage1.java
 * Author: Tenzin Dendup (u3149399)
 * Date Created: 31 March 2017
 * Date Last Changed: 14 April 2017
 * This is a Java console application to calculate number of days alive.
 * It is stage 1 of IIT Java Assignment, University of Canberra.
 * Stage 1 is implemented using simple functions called from main method.
 * Input: 2 sets of dates (Date of birth and another date)
 * Output: Number of days alive from date of birth to second date
 * 
 * 
 */

package javaProgrammingAssignment;

import java.util.GregorianCalendar; //To use Java in-built method to test for Leap year
import java.util.Scanner;

public class Stage1 {
	
	//Constants
	public static final int NUM_DAYS_NORMAL_YEAR = 365;
	public static final int NUM_DAYS_LEAP_YEAR = 366;
	public static final int[] NUMBER_OF_MONTH_DAYS = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	public static void main(String[] args) {
		int iNumFullYear = 0;
		int iNumLeapYear = 0;
		int iNumNormalYear = 0;
		int iNumDaysAlive = 0;
		String[] saTemp1, saTemp2;
		
		Scanner inConsole = new Scanner(System.in);
		
		//Read input in dd/mm/yyyy format
		System.out.println("Enter Date of Birth (dd/mm/yyyy):");
		saTemp1 = inConsole.nextLine().split("/");
		System.out.println("Enter Given Date (dd/mm/yyyy):");
		saTemp2 = inConsole.nextLine().split("/");
		inConsole.close();
		
		//Check for input errors using try catch
		try {
			int iBirthDay = Integer.parseInt(saTemp1[0]);
			int iBirthMonth = Integer.parseInt(saTemp1[1]);
			int iBirthYear = Integer.parseInt(saTemp1[2]);
			int iGivenDay = Integer.parseInt(saTemp2[0]);
			int iGivenMonth = Integer.parseInt(saTemp2[1]);
			int iGivenYear = Integer.parseInt(saTemp2[2]);
			
			if(iBirthDay > 31 || iGivenDay > 31 || iBirthMonth > 12 || iGivenMonth > 12) { //Invalid day and month
				System.out.println("Error in Input");
				System.exit(1);
			}
			else { //Input is fine
				
				//Logic to calculate the number of days alive
				if(iBirthYear == iGivenYear) { //birth year and given year are same
					iNumDaysAlive = calculateDaysBirthYear(iBirthDay, iBirthMonth, iBirthYear) - 
									calculateDaysBirthYear(iGivenDay, iGivenMonth, iGivenYear) + 1;
				}
				
				else {
					if(iGivenYear == iBirthYear + 1) //Birth Year and given Years are consecutive years
						iNumDaysAlive = calculateDaysBirthYear(iBirthDay, iBirthMonth, iBirthYear) + 
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
							iNumDaysAlive = calculateDaysBirthYear(iBirthDay, iBirthMonth, iBirthYear) + 
											calculateDaysGivenYear(iGivenDay, iGivenMonth, iGivenYear) + 
											calculateDaysFullYears(iNumNormalYear, iNumLeapYear);
							}	
						}
					}
				}
				
				//Display result
				System.out.println("Date of birth: " + iBirthDay + "/" + iBirthMonth + "/" + iBirthYear);
				System.out.println("Given date: " + iGivenDay + "/" + iGivenMonth + "/" + iGivenYear);
				System.out.println("Number of days alive = " + iNumDaysAlive);
			}
		}
		catch(NumberFormatException | ArrayIndexOutOfBoundsException e) { //Error in input
			System.out.println("Error in Input");
			System.exit(1);
		}
	}
	
	/*** Functions to do the calculation ***
	 *   Divided into three functions
	 *   Function1: calculates Days in Birth Year
	 *   Function2: calculates Days in Given Year
	 *   Function3: calculates Days in full years between birth year and given year
	 */
	
	/* Function1: to calculate number of days in the birth year */
	public static int calculateDaysBirthYear(int iDay, int iMonth, int iYear) {
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
	
	/* Function2: to calculate number of days in the given year */
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
	
	/* Function3: to calculate number of days in full years between birth year and given year */
	public static int calculateDaysFullYears(int iNumNormalYear, int iNumLeapYear) {
		return((iNumNormalYear * NUM_DAYS_NORMAL_YEAR) + (iNumLeapYear * NUM_DAYS_LEAP_YEAR));
	}

}
