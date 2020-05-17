package aoc.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aoc.Day;

public class Day22 extends Day<Integer, Integer>{
	public static void main(String[] args) {
		(new Day22()).run(22);
	}
	
	@Override
	protected Integer A(String input) throws Exception {
		String[] lines = input.split("\n");
		int depth = Integer.parseInt(lines[0].replace("depth: ", ""));
		String[] t = lines[1].replace("target: ", "").split(",");
		int[] target = new int[] {Integer.parseInt(t[0]), Integer.parseInt(t[1])};
		char[][] grid = new char[depth][target[0]+1];
		grid[0][0]='M';
		grid[target[1]][target[0]]='T';
		Map<List<Integer>, Integer> geologicIndex = new HashMap<>();
		Map<List<Integer>, Integer> erosionLevel = new HashMap<>();

		populateGIndexErosionLevel(geologicIndex, erosionLevel, target, grid, depth);
//		displayMap(grid);
		return countRiskLevel(erosionLevel, grid, target);
	}
	private void populateGIndexErosionLevel(Map<List<Integer>, Integer> gIndex, Map<List<Integer>, Integer> erosionLevel, int[] target, char[][] grid, int depth) {
		for(int y=0; y<grid.length; y++) {
			for(int x=0; x<grid[y].length; x++) {
				List<Integer> l = new ArrayList<>();
				l.add(x); l.add(y);
				if((x==0 && y == 0 )|| (x==target[0] && y==target[1])) {
					gIndex.put(l, 0);
				}else if(y==0) {
					gIndex.put(l, x*16807);
				}else if(x==0) {
					gIndex.put(l, y*48271);
				}else {
					List<Integer> l1 = new ArrayList<>();
					List<Integer> l2 = new ArrayList<>();
					l1.add(l.get(0)-1);
					l1.add(l.get(1));
					l2.add(l.get(0));
					l2.add(l.get(1)-1);
					gIndex.put(l, erosionLevel.get(l1)*erosionLevel.get(l2));
				}
				int eLevel = getErosionLevel(gIndex.get(l), depth);
				erosionLevel.put(l, eLevel);
				grid[y][x] = getRegionType(eLevel);
			}
		}
	}
	
	private int getErosionLevel(int gIndex, int depth) {
		return (gIndex+depth)%20183;
	}
	
	private char getRegionType(int erosionLevel) {
		if(erosionLevel%3 == 0) return '.';
		if(erosionLevel%3 == 1) return '=';
		if(erosionLevel%3 == 2) return '|';
		return Character.MIN_VALUE;
	}
	
	private int countRiskLevel(Map<List<Integer>, Integer> erosionLevel, char[][] grid, int[] target) {
		int count = 0;
		for(int y=0; y<grid.length; y++)
			for(int x=0; x<grid[y].length; x++) {
				List<Integer> l = new ArrayList<>();
				l.add(x);
				l.add(y);
				count+=erosionLevel.get(l)%3;
				if(x==target[0] && y==target[1])
					return count;
			}
		return count;
	}
	
	private void displayMap(char[][] grid) {
		String out = "";
		for(int y =0; y<grid.length; y++) {
			for(int x=0; x<grid[0].length; x++) {
				out+=grid[y][x];
			}
			out+="\n";
		}
		System.out.println(out);
	}
	
	@Override
	protected Integer B(String input) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void test() throws Exception {
		// TODO Auto-generated method stub
		assert(A("depth: 510\n"+
				"target: 10,10")==114);
		
	}

}
