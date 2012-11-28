package calllog;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		LogParser parser = new LogParser("testlog.txt");
		CallLog log = parser.parse();
		System.out.printf("Log loaded with %s entries\n", log.size());

		int minutes = 24 * 60;

		for (int i = 0; i < minutes; i++) {
			String time = String.format("%02d:%02d", (int) (i / 60), i % 60);
			System.out.printf("%s  ==> %s\n", time, Operations.getConcurrentCountAtTime(log, time));
		}

	}

}
