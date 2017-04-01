package javaProgrammingAssignment;

public class Test {

	public static boolean isLeap(int iYear) {
		if(iYear % 4 == 0)
			if(iYear % 100 == 0)
				if(iYear % 400 == 0)
					return true;
				else
					return false;
			else
				return true;
		else
			return false;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] iaDateOfBirth = {10, 6, 1699};
		int[] iaDate = {5,4,2021};
		int iFullYears;
		int iNumberLeap = 0;
		int iNumberNormal;
		
		// First calculate the number of full years. If we omit the birth year and the given year, the years in between are full years :)
		iFullYears = iaDate[2] - iaDateOfBirth[2] - 1;
		
		//Now calculate the number of leap years in those full years
		for(int iI=iaDateOfBirth[2] + 1; iI < iaDate[2]; iI++) {
			if(isLeap(iI)) {
				System.out.println("Leap Year: " + iI);
				iNumberLeap++;
			}
		}
		iNumberNormal = iFullYears - iNumberLeap;
		
		System.out.println("Full year, Normal Years, Leap Years = " + iFullYears + " " + iNumberNormal + " " + iNumberLeap);
		

	}

}
