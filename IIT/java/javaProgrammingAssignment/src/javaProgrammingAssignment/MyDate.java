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
	
	public int[] getDate() {
		int[] iaDate = {iDay, iMonth, iYear};
		return(iaDate);
	}
	
	public String getDateFormatted() {
		return(iDay + "/" + iMonth + "/" + iYear);
	}
	
	public void setDate(int iDay, int iMonth, int iYear) {
		this.iDay = iDay;
		this.iMonth = iMonth;
		this.iYear = iYear;
	}
}