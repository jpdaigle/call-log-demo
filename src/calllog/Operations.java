package calllog;

import java.text.ParseException;

public class Operations {

	/**
	 * Gets the number of concurrent calls (cached in the log entries,
	 * precomputed at parse time) at a given time.
	 * <p>
	 * Preconditions: log is sorted and legal (spans a single day, is not empty,
	 * etc.)
	 * <p>
	 * Assuming the log is sorted, we are looking for the entry with the largest
	 * timestamp which is <strong>lesser or equal</strong> to the timestamp
	 * supplied as argument, and can do this with a binary search.
	 * 
	 * 
	 * @param log
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static int getConcurrentCountAtTime(CallLog log, String time) throws ParseException {
		final long ts = LogParser.parseTimeString(time);

		int ptrMin = 0;
		int ptrMax = log.size() - 1;

		while (ptrMin <= ptrMax) {
			int pivot = ptrMin + (ptrMax - ptrMin) / 2;
			final long callTime = log.get(pivot).timestamp;
			final long callNext = (pivot == log.size() - 1) ? Long.MAX_VALUE : log.get(pivot + 1).timestamp;
			if (ts >= callTime && ts < callNext)
				return log.get(pivot).cumulOpenCalls;
			else if (ts < callTime)
				ptrMax = pivot - 1;
			else if (ts > callTime)
				ptrMin = pivot + 1;
		}

		// We only hit this if the requested time was before the first entry
		// (hence, 0 open calls)
		return 0;
	}
}
