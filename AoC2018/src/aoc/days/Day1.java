package aoc.days;

import java.util.ArrayList;
import java.util.List;

import aoc.Day;

public class Day1 extends Day<Integer, Integer>{
	public static void main(String[] args) {
		(new Day1()).run(1);
	}

	@Override
	protected Integer A(String input) {
		int count = 0;
		String[] lines = input.split("\n");
		for(String val: lines) {
			int i = Integer.parseInt(val);
			count += i;
		}
		return count;
	}

	@Override
	protected Integer B(String input) {
		int count = 0;
		String[] lines = input.split("\n");
		List<Integer> frequencies = new ArrayList<>();
		while(true) {
			for(String val: lines) {
				int i = Integer.parseInt(val);
				count += i;
				if(frequencies.contains(count))
					return count;
				frequencies.add(count);
			}
		}
	}

	@Override
	protected void test() {
		assert(A("+1\n-3\n+15\n-4")==9);
		assert(B("+1\n-3\n+2")==1);
		
	}

}
