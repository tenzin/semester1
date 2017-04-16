package javaProgrammingAssignment;

import java.util.GregorianCalendar;

public class Person {
	static final int NUM_DAYS_NORMAL_YEAR = 365;
	static final int NUM_DAYS_LEAP_YEAR = 366;
	static final int[] NUMBER_OF_MONTH_DAYS = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	private String sName;
	private MyDate birthDate;
	private MyDate givenDate;
	private int iDaysAlive;
	
	public Person(String sName, MyDate birthDate, MyDate givenDate) {
		this.sName = sName;
		this.birthDate = birthDate;
		this.givenDate = givenDate;
		this.iDaysAlive=calculateDaysAlive();
	}

	private int calculateDaysAlive() {
		
		int iNum = 0;
		
		int iNumFullYear = 0;
		int iNumLeapYear = 0;
		int iNumNormalYear = 0;
		
		/*Step 1: Calculate number of days in Birth Year*/
		//Calculate number of days in the birth month
		iNum = NUMBER_OF_MONTH_DAYS[birthDate.getMonth() - 1] - birthDate.getDay() + 1; // +1 is to make the birth date inclusive	
		
		//Now calculate remaining number of days in birth year
		for(int iI = birthDate.getMonth() + 1; iI <= 12; iI++)
			iNum += NUMBER_OF_MONTH_DAYS[iI - 1];
		
		//Check for LEAP year && February month. If TRUE add 1 to iNumDays;
		if(new GregorianCalendar().isLeapYear(birthDate.getYear()) && birthDate.getMonth() == 2)
			iNum++;
		
		/*Step 2: Calculate Number of Days in Given Year*/
		//Calculate number of days from January to iMonth - 1
		for(int iI = 0; iI < givenDate.getMonth() - 1; iI++)
			iNum += NUMBER_OF_MONTH_DAYS[iI];
		
		//Add the days in given month which is givenDate.iDay
		iNum += givenDate.getDay();
		//Check for LEAP year && month beyond February. If TRUE add 1 to iNumDays
		if(new GregorianCalendar().isLeapYear(givenDate.getYear()) && givenDate.getMonth() > 2)
			iNum++;
		
		/*Step 3: Calculate number of days in full years between birth year and given year*/
		// Calculate the number of full years. If we omit the birth year and the given year, the years in between are full years
		iNumFullYear = givenDate.getYear() - birthDate.getYear() - 1;
		
		// Now calculate the number of leap years and normal years in those full years
		for(int iI=birthDate.getYear() + 1; iI < givenDate.getYear(); iI++) {
			if(new GregorianCalendar().isLeapYear(iI))
				iNumLeapYear++;
		}
		iNumNormalYear = iNumFullYear - iNumLeapYear;
		
		//Calculate the days in normal years and leap years
		iNum += (iNumNormalYear * NUM_DAYS_NORMAL_YEAR) + (iNumLeapYear * NUM_DAYS_LEAP_YEAR);
		return(iNum);
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
		iDaysAlive = calculateDaysAlive();
	}
}
