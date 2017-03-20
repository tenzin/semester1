
public class TestExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int iTotal = 0;
		for (int iI = 1; iI <= 3; iI++)
			for (int iJ = 1; iJ < 3; iJ++) {
				iTotal += iI * iJ;
			}
		System.out.println("iTotal = " + iTotal);
	}

}
