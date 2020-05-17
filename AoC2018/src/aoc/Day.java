package aoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public abstract class Day<A,B> {
	
	public static String readInput(int day) throws IOException {
		URL url = new URL("https://adventofcode.com/2018/day/"+day+"/input");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Cookie", "session=53616c7465645f5fb828e9ce621913e44486cef9fa80a29c5b7d8f0f628ea9a5b278cc98073963e861d740e7cc968678");
		return new BufferedReader(new InputStreamReader(con.getInputStream())).lines()
				   .parallel().collect(Collectors.joining("\n"));
	}
	
	protected abstract A A(String input) throws Exception;
	
	protected abstract B B(String input) throws Exception;
	
	protected abstract void test() throws Exception;

	protected void run(int day) {
		try {
			test();
			String input = readInput(day);
			long startTimeA = System.currentTimeMillis();		
			A valA = A(input);
			long elapsedA = System.currentTimeMillis() - startTimeA;
			System.out.println("A returned "+valA+", took: "+elapsedA +"ms");

			long startTimeB = System.currentTimeMillis();		
			B valB = B(input);
			long elapsedB = System.currentTimeMillis() - startTimeB;	
			System.out.println("B returned "+valB+", took: "+elapsedB +"ms");

		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
