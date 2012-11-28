package calllog.test;

import java.text.ParseException;

import junit.framework.Assert;

import org.junit.Test;

import calllog.CallEvent;
import calllog.CallLog;
import calllog.LogParser;
import calllog.Operations;

public class ConcurrentCallsTest {

	// Check the hard way every possible time-of-day query.

	// The problem space is small so this is feasible. The actual boundary
	// conditions however are:
	// - When the pivot is right on the target value.
	// - When the target value is not in the list and we must infer the greatest
	// recorded timestamp before the target.
	// - When target value is greater than the last value in the list.
	// - When target value is less than the first value in the list.

	private static int getConcurrentCallsLinear(CallLog log, String time) throws ParseException {
		final long ts = LogParser.parseTimeString(time);
		int calls = 0;
		int i = 0;

		while (i < log.size() && ts >= log.get(i).timestamp) {
			CallEvent ev = log.get(i);
			switch (ev.type) {
			case START:
				calls++;
				break;
			case END:
				calls--;
				break;
			}
			i++;
		}

		return calls;
	}

	@Test
	public void testAll() throws Exception {
		final String filename = "testlog.txt";
		CallLog log = new LogParser(filename).parse();

		int assertions = 0;
		for (int i = 0; i < 24 * 60; i++) {
			String timeOfDay = timeOfDay(i);
			int calls_hardWay = getConcurrentCallsLinear(log, timeOfDay);
			int calls_test = Operations.getConcurrentCountAtTime(log, timeOfDay);
			Assert.assertEquals(calls_hardWay, calls_test);
			assertions++;
		}
		System.out.printf("Passed %s timeOfDay assertions.\n", assertions);
	}

	private static String timeOfDay(int minutes) {
		return String.format("%02d:%02d", (int) (minutes / 60), minutes % 60);
	}

}
