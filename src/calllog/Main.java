package calllog;

public class Main {

	public static void main(String[] args) throws Exception {
		final String filename = "testlog.txt";
		LogParser parser = new LogParser(filename);
		CallLog log = parser.parse();
		System.out.printf("Log %s loaded with %s entries\n", filename, log.size());

		int minutes = 18 * 60; // look at 18 hours
		for (int i = 0; i < minutes; i += 30) {
			String time = String.format("%02d:%02d", (int) (i / 60), i % 60);
			System.out.printf("%s  ==> %s concurrent calls\n", time, Operations.getConcurrentCountAtTime(log, time));
		}

	}

}
