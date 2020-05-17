package aoc.days;
import java.util.HashMap;
import java.util.Map;

import aoc.Day;

public class Day2 extends Day<Integer, String>{
	public static void main(String[] args) {
		(new Day2()).run(2);
	}

	@Override
	protected Integer A(String input) {
		int count2s = 0;
		int count3s = 0;
		
		Map<Character, Integer> repetitions = new HashMap<>();
		String[] lines = input.split("\n");

		for(String val: lines) {
			for(int i=0; i<val.length(); i++) {
				char c = val.charAt(i);
				repetitions.put(c, repetitions.get(c) != null ? repetitions.get(c)+1 : 1);
			}
			boolean twosFound = false, threesFound = false;
			for(char c: repetitions.keySet()) {
				if(!twosFound && repetitions.get(c)==2) {
					count2s++;
					twosFound = true;
				}
				if(!threesFound && repetitions.get(c)==3) {
					count3s++;
					threesFound = true;
				}
				if(threesFound && twosFound)
					break;
			}
			repetitions.clear();
		}
		return count2s*count3s;
	}
	
	@Override
	protected String B(String input) {
		String[] lines = input.split("\n");
		for(String word : lines) {
			for(String word2: lines) {
				if(word.equals(word2) || word.length() != word2.length()) continue;
				String output="";
				int count = 0;
				for(int i=0; i<word.length(); i++) {
						if(word.charAt(i)==word2.charAt(i)) output += word.charAt(i);
						else count++;
				}
				if(count == 1)
					return output;
			}
		}
		return null;
	}
	
	@Override
	protected void test() {
		// TODO Auto-generated method stub
		
	}


}
