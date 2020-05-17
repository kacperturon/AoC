package aoc.days;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aoc.Day;

public class Day18 extends Day<Integer, Integer> {
	public static void main(String[] args) {
		(new Day18()).run(18);
	}
	
	private void printGrid(char[][] grid) {
		String out = "";
		for(int y=0; y<grid.length; y++) {
			for(int x=0; x<grid[0].length; x++) {
				out+=grid[y][x];
			}
			out+="\n";
		}
		System.out.println(out);
	}
	
	@Override
	protected Integer A(String input) throws Exception {
		// PARSE
		String[] lines = input.split("\n");
		char[][] grid = new char[lines.length][lines[0].length()];
		for(int y=0; y<lines.length; y++) {
			grid[y] = lines[y].toCharArray();
		}
		// LOGIC
		char[][] newGrid = new char[lines.length][lines[0].length()];
		for(int i=0; i<10;i++) {
//			printGrid(grid);
			for(int y=0; y<grid.length; y++) {
				for(int x=0; x<grid[0].length; x++) {
					char c = grid[y][x];
					Map<Character, Integer> adjC = getAdjacentCount(x, y, grid);
					if(c=='.') {
						if(adjC.getOrDefault('|',0)>=3) newGrid[y][x]='|';
						else newGrid[y][x]='.';
					}else if (c=='|') {
						if(adjC.getOrDefault('#',0)>=3) newGrid[y][x]='#';
						else newGrid[y][x]='|';
					}else if (c=='#') {
						if(adjC.getOrDefault('#',0)>0 && adjC.getOrDefault('|',0)>0) newGrid[y][x]='#';
						else newGrid[y][x]='.';
					}
				}
			}
			grid = newGrid;
			newGrid = new char[lines.length][lines[0].length()];
		}
//		printGrid(grid);

		return count('|', grid) * count('#', grid);
	}
	
	private int count(char c, char[][] grid) {
		int count = 0;
		for(int y=0;y<grid.length; y++) {
			for(int x=0; x<grid[0].length; x++) {
				if(grid[y][x]==c)count++;
			}
		}
		return count;
	}
	
	private Map<Character, Integer> getAdjacentCount(int x, int y, char[][] grid) {
		Map<Character, Integer> adjCount = new HashMap<>();
		
		if(x-1>=0 && y-1>=0) 
			adjCount.put(grid[y-1][x-1], adjCount.getOrDefault(grid[y-1][x-1], 0)+1);
		if(y-1>=0) 
			adjCount.put(grid[y-1][x], adjCount.getOrDefault(grid[y-1][x], 0)+1);
		if(y-1>=0 && x+1<grid[0].length) 
			adjCount.put(grid[y-1][x+1], adjCount.getOrDefault(grid[y-1][x+1], 0)+1);

		if(x-1>=0)
			adjCount.put(grid[y][x-1], adjCount.getOrDefault(grid[y][x-1], 0)+1);
		if(x+1<grid[0].length)
			adjCount.put(grid[y][x+1], adjCount.getOrDefault(grid[y][x+1], 0)+1);
		
		if(x-1>=0 && y+1<grid.length)
			adjCount.put(grid[y+1][x-1], adjCount.getOrDefault(grid[y+1][x-1], 0)+1);

		if(y+1<grid.length)
			adjCount.put(grid[y+1][x], adjCount.getOrDefault(grid[y+1][x], 0)+1);

		if(x+1<grid[0].length && y+1<grid.length)
			adjCount.put(grid[y+1][x+1], adjCount.getOrDefault(grid[y+1][x+1], 0)+1);
		return adjCount;
	}

	@Override
	protected Integer B(String input) throws Exception {
		// PARSE
		String[] lines = input.split("\n");
		char[][] grid = new char[lines.length][lines[0].length()];
		for(int y=0; y<lines.length; y++) {
			grid[y] = lines[y].toCharArray();
		}
		// LOGIC
		char[][] newGrid = new char[lines.length][lines[0].length()];
		for(int i=0; i<600;i++) {
			for(int y=0; y<grid.length; y++) {
				for(int x=0; x<grid[0].length; x++) {
					char c = grid[y][x];
					Map<Character, Integer> adjC = getAdjacentCount(x, y, grid);
					if(c=='.') {
						if(adjC.getOrDefault('|',0)>=3) newGrid[y][x]='|';
						else newGrid[y][x]='.';
					}else if (c=='|') {
						if(adjC.getOrDefault('#',0)>=3) newGrid[y][x]='#';
						else newGrid[y][x]='|';
					}else if (c=='#') {
						if(adjC.getOrDefault('#',0)>0 && adjC.getOrDefault('|',0)>0) newGrid[y][x]='#';
						else newGrid[y][x]='.';
					}
				}
			}
			grid = newGrid;
			newGrid = new char[lines.length][lines[0].length()];
		}
		int i=600;
		while(true) {
			if(1000000000%28 == i%28) break;
			for(int y=0; y<grid.length; y++) {
				for(int x=0; x<grid[0].length; x++) {
					char c = grid[y][x];
					Map<Character, Integer> adjC = getAdjacentCount(x, y, grid);
					if(c=='.') {
						if(adjC.getOrDefault('|',0)>=3) newGrid[y][x]='|';
						else newGrid[y][x]='.';
					}else if (c=='|') {
						if(adjC.getOrDefault('#',0)>=3) newGrid[y][x]='#';
						else newGrid[y][x]='|';
					}else if (c=='#') {
						if(adjC.getOrDefault('#',0)>0 && adjC.getOrDefault('|',0)>0) newGrid[y][x]='#';
						else newGrid[y][x]='.';
					}
				}
			}
			i++;
			grid = newGrid;
			newGrid = new char[lines.length][lines[0].length()];
		}
			
		return count('|', grid) * count('#', grid);
	}

	@Override
	protected void test() throws Exception {
		assert(A(".#.#...|#.\n" + 
				".....#|##|\n" + 
				".|..|...#.\n" + 
				"..|#.....#\n" + 
				"#.#|||#|#|\n" + 
				"...#.||...\n" + 
				".|....|...\n" + 
				"||...#|.#|\n" + 
				"|.||||..|.\n" + 
				"...#.|..|.")==1147);
		
	}

}
