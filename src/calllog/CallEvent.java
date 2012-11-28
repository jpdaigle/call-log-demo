package calllog;

/**
 * Represents a single Call Event, such as a call start or end. Caches
 * cumulative calls in progress at this time for fast lookup of concurrent call
 * count.
 * <p>
 * Members are immutable and thus safely published publicly.
 */
public class CallEvent {

	public enum EntryType {
		START, END
	}

	/**
	 * Time at which the event occurred.
	 */
	public final long timestamp;
	/**
	 * Unique ID of the call (uniqueness guaranteed in a single day).
	 */
	public final int callId;
	/**
	 * Type of entry.
	 */
	public final EntryType type;
	/**
	 * Cached value of concurrent call count at this point in the day, for
	 * efficient lookup of number of concurrent calls at a point in time.
	 */
	public final int cumulOpenCalls;

	public CallEvent(long timestamp, EntryType type, int id, int cumulOpenCalls) {
		this.timestamp = timestamp;
		this.type = type;
		this.callId = id;
		this.cumulOpenCalls = cumulOpenCalls;
		
		if (cumulOpenCalls < 0)
			throw new IllegalArgumentException("Sanity check failure: cumulOpenCalls should be >= 0");
	}

	@Override
	public String toString() {
		return String.format("%s: Call %s %s (cumulOpenCalls:%s)", timestamp, callId, type, cumulOpenCalls);
	}
}
