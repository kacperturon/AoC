package aoc.days;
import java.util.HashMap;
import java.util.Map;

import aoc.Day;

public class Day6 extends Day<Integer, Integer>{
	public static void main(String[] args) {
		(new Day6()).run(6);
	}
	
	char[][] grid;
	Map<Character, Map<Character, Integer>> sources;
	Map<Character, Integer> area;
	
	public Day6() {
		grid = new char[400][400];
		sources = new HashMap<>();
		for(int y=0; grid.length>y;y++) {
			for(int x=0; grid[0].length>x;x++) {
				grid[y][x]='.';
			}
		}
	}
	
	
	public void mapCoordinates(String input) {
		char currChar = 'A';
		String[] lines = input.split("\n");
		for(String coordinates : lines) {
			int x = Integer.parseInt(coordinates.split(",")[0]);
			int y = Integer.parseInt(coordinates.split(",")[1].trim());
			grid[y][x] = currChar;
			Map<Character, Integer> coords = new HashMap<>();
			coords.put('x', x);
			coords.put('y', y);
			sources.put(currChar, coords);
			currChar++;
		}
	}

	
	public char nodeClosestSource(int x, int y) {
		Map<Character, Integer> distances = new HashMap<>();
		for(Map.Entry<Character, Map<Character, Integer>> entry: sources.entrySet()) 
			distances.put(entry.getKey(), Math.abs(entry.getValue().get('x')-x)+Math.abs(entry.getValue().get('y')-y));
		
		int lowestVal=Integer.MAX_VALUE;
		for(Map.Entry<Character, Integer> entry : distances.entrySet()) 
			if(entry.getValue()<lowestVal) lowestVal = entry.getValue();
		
		char out = '.';
		int count =0;
		for(Map.Entry<Character, Integer> entry : distances.entrySet()) 
			if(entry.getValue()==lowestVal) {
				out = entry.getKey();
				count++;
			}
		
		if(count>1) out = '.';
		return out;
	}
	
	public void mapManhattan() {
		area = new HashMap<>();
		for(int y=0; grid.length>y;y++) 
			for(int x=0; grid[0].length>x;x++) 
				if(sources.get(grid[y][x]) == null) {
					char source = nodeClosestSource(x,y);
					if(source == '.') continue;
					area.put(source, area.get(source) == null ? 1: area.get(source)+1);
					grid[y][x] = (char)(source+32);
				}
	}
	
	public int largestFiniteArea() {
		int largestArea = Integer.MIN_VALUE;
		for(Map.Entry<Character, Integer> entry: area.entrySet()) {
			if(largestArea<entry.getValue() && isAreaFinite(entry.getKey())) {
				largestArea = entry.getValue();
			}
		}
		return largestArea+1;
	}
	
	private boolean isAreaFinite(char source) {
		//if any child node is touching the end of array the area is infinite
		if(touchingBorder(sources.get(source).get('x'), sources.get(source).get('y'))) return false;
		for(int y=0; grid.length>y;y++) {
			for(int x=0; grid[0].length>x;x++) {
				if(grid[y][x]==(char)(source+32))
					if(touchingBorder(x,y)) return false;
				
			}
		}
		return true;
	}
	
	private boolean touchingBorder(int x, int y) {
		if(x-1<0 || y-1<0) return true;
		if(x+1>grid[0].length-1 || y+1>grid.length-1) return true;
		return false;
	}
	
	public void displayGrid() {
		for(int y=0; grid.length>y;y++) {
			for(int x=0; grid[0].length>x;x++) {
				System.out.print(grid[y][x]);
			}
			System.out.println();
		}
	}
	
	public void displaySources() {
		for(Map.Entry<Character, Map<Character, Integer>> entry: sources.entrySet()) {
			System.out.println(entry.getKey()+" - x: "+entry.getValue().get('x')+" y: "+entry.getValue().get('y'));
		}
	}


	@Override
	protected Integer A(String input) throws Exception {
		mapCoordinates(input);
		mapManhattan();
		return largestFiniteArea();
	}


	@Override
	protected Integer B(String input) throws Exception {
		mapCoordinates(input);
		int count =0;
		for(int y=0; grid.length>y;y++) {
			for(int x=0; grid[0].length>x;x++) {
				int sum = 0;
				for(Map.Entry<Character, Map<Character, Integer>> entry: sources.entrySet()) {
					int dist = Math.abs(entry.getValue().get('x')-x)+Math.abs(entry.getValue().get('y')-y);
					sum += dist;
				}
				if(0<sum && sum<10000) count++;
			}
		}

		return count;
	}


	@Override
	protected void test() {
		// TODO Auto-generated method stub
		
	}
}
