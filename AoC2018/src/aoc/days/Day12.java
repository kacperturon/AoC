package aoc.days;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aoc.Day;

public class Day12 extends Day<Integer, Long> {
	public static void main(String[] args) {
		(new Day12()).run(12);
	}
	
	@Override
	protected Integer A(String input) throws Exception {
		String[] lines = input.split("\n");
		Map<String, Boolean> rules = new HashMap<>();
		String initialState = lines[0].split(" ")[2];
		for(int i=2;i<lines.length;i++) {
			String[] rule = lines[i].split("=>");
			rules.put(rule[0].trim(), rule[1].trim().equals("#"));
		}
		int padding = 25;
		for(int i=0;i<padding;i++) {
			initialState+=".";
			initialState="."+initialState;
		}
		
		String newState = "";
		for(int j=0;j<20;j++) {
			newState = "";
			for(int i=0;i<initialState.length();i++) {
				String combination ="";
				combination+= i-2<0 ? "." : initialState.charAt(i-2);
				combination+= i-1<0 ? "." : initialState.charAt(i-1);
				combination+= initialState.charAt(i);
				combination+= i+1>initialState.length()-1 ? "." : initialState.charAt(i+1);
				combination+= i+2>initialState.length()-1 ? "." : initialState.charAt(i+2);
				newState += rules.getOrDefault(combination, false) ? "#" : ".";
				
			}
			initialState = newState;
		}
		
		int sumOfPots=0;
		for(int i=0; i<initialState.length(); i++) {
			if(initialState.charAt(i)=='#')
				sumOfPots += i-padding;
		}
		return sumOfPots;
		
	}

	@Override
	protected Long B(String input) throws Exception {
		String[] lines = input.split("\n");
		Map<String, Boolean> rules = new HashMap<>();
		String initialState = lines[0].split(" ")[2];
		for(int i=2;i<lines.length;i++) {
			String[] rule = lines[i].split("=>");
			rules.put(rule[0].trim(), rule[1].trim().equals("#"));
		}
		int paddingLeft = 10;
		int paddingRight = 300;
		for(int i=0;i<paddingLeft;i++) {
			initialState="."+initialState;
		}
		for(int i=0;i<paddingRight;i++) {
			initialState+=".";
		}
		String newState = "";
		for(int j=0;j<150;j++) {
			newState = "";
			for(int i=0;i<initialState.length();i++) {
				String combination ="";
				combination+= i-2<0 ? "." : initialState.charAt(i-2);
				combination+= i-1<0 ? "." : initialState.charAt(i-1);
				combination+= initialState.charAt(i);
				combination+= i+1>initialState.length()-1 ? "." : initialState.charAt(i+1);
				combination+= i+2>initialState.length()-1 ? "." : initialState.charAt(i+2);
				newState += rules.getOrDefault(combination, false) ? "#" : ".";
				
			}
			initialState = newState;
		}
		
		long sumOfPots=0;
		for(int i=0; i<initialState.length(); i++) {
			if(initialState.charAt(i)=='#')
				sumOfPots += i-paddingLeft + 50000000000L-150;
		}
		return sumOfPots;
	}

	@Override
	protected void test() throws Exception {
		assert(A("initial state: #..#.#..##......###...###\n" + 
				"\n" + 
				"...## => #\n" + 
				"..#.. => #\n" + 
				".#... => #\n" + 
				".#.#. => #\n" + 
				".#.## => #\n" + 
				".##.. => #\n" + 
				".#### => #\n" + 
				"#.#.# => #\n" + 
				"#.### => #\n" + 
				"##.#. => #\n" + 
				"##.## => #\n" + 
				"###.. => #\n" + 
				"###.# => #\n" + 
				"####. => #")==325);
		
		
	}

}
