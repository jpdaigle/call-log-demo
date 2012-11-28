package calllog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import calllog.CallEvent.EntryType;

public class LogParser {

	private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	private final File fileIn;

	public LogParser(String filePath) throws IOException {
		fileIn = new File(filePath);
		if (!fileIn.exists())
			throw new IOException(String.format("File '%s' does not exist.", filePath));
	}

	/**
	 * Parse a String of type "13:08" into a timestamp; NOT safe for concurrent
	 * use.
	 * 
	 * @param str
	 * @return Timestamp (from an arbitrary base offset: do not assume anything
	 *         about the offset).
	 * @throws ParseException
	 */
	public static long parseTimeString(String str) throws ParseException {
		Date d = timeFormat.parse(str);
		return d.getTime();
	}

	public CallLog parse() {
		CallLog log = new CallLog();
		int concurrent = 0;

		try {
			BufferedReader br = new BufferedReader(new FileReader(fileIn));
			String line = null;
			while ((line = br.readLine()) != null) {
				// Tokenize the line by splitting on TAB
				String[] lineparts = line.split("\t");
				if (lineparts.length != 3)
					continue;

				// Parse all 3 fields
				long eventDate = parseTimeString(lineparts[0]);
				EntryType entryType = EntryType.valueOf(lineparts[1]);
				int entryId = Integer.parseInt(lineparts[2]);

				// Keep a running total of the concurrent call count
				switch (entryType) {
				case START:
					concurrent++;
					break;
				case END:
					concurrent--;
				}

				log.add(new CallEvent(eventDate, entryType, entryId, concurrent));
			}
		} catch (Exception ioe) {
			System.err.println("Exception while reading input file: " + ioe);
			return new CallLog(); // blank log
		}
		return log;
	}
}
