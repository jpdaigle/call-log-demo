package calllog;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a call log as an ordered list of call entries.
 * 
 * Yes, obviously, currently this doesn't present any functionality that a
 * straight List instance wouldn't, but we can extend this class to add
 * validation of records as they're inserted, efficient lookup of records, etc.
 */
public class CallLog {

	// The internal representation of the log is as an arraylist, which gives us
	// O(1) access to any given element.
	public final List<CallEvent> log;

	public CallLog() {
		log = new ArrayList<CallEvent>();
	}

	public void add(CallEvent entry) {
		log.add(entry);
	}

	public CallEvent get(int index) {
		return log.get(index);
	}

	public int size() {
		return log.size();
	}
}
