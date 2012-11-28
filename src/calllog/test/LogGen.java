package calllog.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Random;

public class LogGen {

	public static void main(String[] args) {
		HashSet<Integer> openCalls = new HashSet<Integer>();
		Random r = new Random(12345); // always make the same file
		GregorianCalendar gc = new GregorianCalendar(2012, 11, 27);
		int callId = 0;

		for (int i = 0; i < 1000; i++) {
			// actions at any point in time are: NOTHING, START, END
			gc.add(GregorianCalendar.MINUTE, 1);
			final int action = r.nextInt(3);
			int curCallId = -1;
			switch (action) {
			case 0:
				// DO NOTHING
				break;
			case 1:
				// START A NEW CALL
				curCallId = ++callId;
				openCalls.add(curCallId);
				System.out.printf("%s\tSTART\t%s\n", strDate(gc), curCallId);
				break;
			case 2:
				// END A CALL (PICK ONE FROM OPEN CALLS)
				if (openCalls.size() > 0) {
					// Grab a random callId from the set (whatever first thing
					// the iterator returns)
					curCallId = openCalls.iterator().next();
					openCalls.remove(Integer.valueOf(curCallId));
					System.out.printf("%s\tEND\t%s\n", strDate(gc), curCallId);
				}
				break;
			}
		}

	}

	public static String strDate(Calendar c) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(c.getTime());
	}

}
