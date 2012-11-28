package calllog;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a call log as an ordered list of call entries.
 * 
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
