package aoc.days;

import java.util.ArrayList;
import java.util.List;

import aoc.Day;

public class Day17 extends Day<Integer, Integer>{
	public static void main(String[] args) {
		(new Day17()).run(17);
	}
	
	@Override
	protected Integer A(String input) throws Exception {
		// PARSE
		String[] lines = input.split("\n");
		List<List<List<Integer>>> valuesParsed = new ArrayList<>();
		int lowestX=Integer.MAX_VALUE; int lowestY=Integer.MAX_VALUE; // Lowest in value not position
		int highestX=500; int highestY=Integer.MIN_VALUE;
		
		for(String line: lines) {
			String[] vals = line.split(", ");
			String xval = vals[0].contains("x") ? vals[0]: vals[1];
			String yval = vals[0].contains("y") ? vals[0]: vals[1];
			
			List<List<Integer>> valuesXY = new ArrayList<>();

			List<Integer> valuesX = new ArrayList<>();
			if(xval.contains("..")){
				for(String val : xval.replace("x=", "").split("\\.\\.")) {
					int x = Integer.parseInt(val);
					if(x<lowestX) lowestX = x;
					if(x>highestX) highestX = x;
					valuesX.add(x);
				}
			}else {
				int x = Integer.parseInt(xval.replace("x=", ""));
				if(x<lowestX) lowestX = x;
				if(x>highestX) highestX = x;
				valuesX.add(x);
			}
			List<Integer> valuesY = new ArrayList<>();
			if(yval.contains("..")){
				for(String val : yval.replace("y=", "").split("\\.\\.")){
					int y = Integer.parseInt(val);
					if(y<lowestY) lowestY = y;
					if(y>highestY) highestY = y;
					valuesY.add(y);
				}
			}else {
				int y = Integer.parseInt(yval.replace("y=", ""));
				if(y<lowestY) lowestY = y;
				if(y>highestY) highestY = y;
				valuesY.add(y);
			}
			valuesXY.add(valuesX);
			valuesXY.add(valuesY);
			valuesParsed.add(valuesXY);
		}
		
		int padding = -300;
		char[][] grid = new char[highestY+1][401];
		for(int y=0; y<grid.length; y++)
			for(int x=0; x<grid[y].length; x++)
				grid[y][x]='.';
		
		for(List<List<Integer>> valuesXY : valuesParsed) {
			if(valuesXY.get(0).size()==1) {
				int x = valuesXY.get(0).get(0);
				for(int y=valuesXY.get(1).get(0); y<=valuesXY.get(1).get(1); y++) {
					grid[y][x+padding]='#';
				}
				
			}else {
				int y = valuesXY.get(1).get(0);
				for(int x=valuesXY.get(0).get(0); x<=valuesXY.get(0).get(1); x++) {
					grid[y][x+padding]='#';
				}
			}
		}
		grid[0][500+padding]='+';
		int[] waterSourceXY = new int[] {500+padding, 0};
		// LOGIC
//		displayMap(grid);
		pourWater(grid, waterSourceXY, highestY);
		int flowing=0;
		int resting=0;
		for(int y=0; y<grid.length; y++) {
			for(int x=0; x<grid[y].length; x++) {
				if(y>=lowestY)
					if(grid[y][x]=='~')
						resting++;
					else if(grid[y][x]=='|')
						flowing++;
			}
		}
//		displayMap(grid);

		return flowing+resting;
	}
	

	private void pourWater(char[][] grid, int[] waterXY, int maxY) {
		int x = waterXY[0]; int y = waterXY[1]; // Current cell
		if(y >= maxY) return;
		
		if(grid[y+1][x]=='.') {
			grid[y+1][x]='|';
			pourWater(grid, new int[] {x, y+1}, maxY);
		}
		if((grid[y+1][x]=='#'||grid[y+1][x]=='~')&&grid[y][x+1]=='.') {
			grid[y][x+1]='|';
			pourWater(grid, new int[] {x+1, y}, maxY);
		}
		if((grid[y+1][x]=='#'||grid[y+1][x]=='~')&&grid[y][x-1]=='.') {
			grid[y][x-1]='|';
			pourWater(grid, new int[] {x-1, y}, maxY);
		}
		if(isEnclosed(grid,x,y)) spread(grid, x, y);

	}
	
	private void spread(char[][] grid, int x, int y) {
		fillSide(grid, x, y, 1);
		fillSide(grid, x, y, -1);
	}
	
	private void fillSide(char[][] grid, int x, int y, int offset) {
		int currX = x;
		while(true) {
			if(grid[y][currX] == '#') return;
			grid[y][currX] = '~';
			currX +=offset;
		}
	}
	
	private boolean isEnclosed(char[][] grid, int x, int y) {
		return hasWall(grid, x, y, 1) && hasWall(grid, x, y, -1);
	}
	
	private boolean hasWall(char[][] grid, int x, int y, int offset) {
		int currX = x;
		while(true) {
			if(grid[y][currX] == '.') return false;
			if(grid[y][currX] == '#') return true;
			currX += offset;
			if(currX<0||currX>=grid[0].length) return false;
		}
	}
	
	private void displayMap(char[][] grid) {
		String out ="";
		for(int y=0; y<grid.length; y++) {
			for(int x=0; x<grid[y].length; x++) {
				out+=grid[y][x];
			}
			out+="\n";
		}
		System.out.println(out);
		
	}

	@Override
	protected Integer B(String input) throws Exception {
		// PARSE
		String[] lines = input.split("\n");
		List<List<List<Integer>>> valuesParsed = new ArrayList<>();
		int lowestX=Integer.MAX_VALUE; int lowestY=Integer.MAX_VALUE; // Lowest in value not position
		int highestX=500; int highestY=Integer.MIN_VALUE;
		
		for(String line: lines) {
			String[] vals = line.split(", ");
			String xval = vals[0].contains("x") ? vals[0]: vals[1];
			String yval = vals[0].contains("y") ? vals[0]: vals[1];
			
			List<List<Integer>> valuesXY = new ArrayList<>();

			List<Integer> valuesX = new ArrayList<>();
			if(xval.contains("..")){
				for(String val : xval.replace("x=", "").split("\\.\\.")) {
					int x = Integer.parseInt(val);
					if(x<lowestX) lowestX = x;
					if(x>highestX) highestX = x;
					valuesX.add(x);
				}
			}else {
				int x = Integer.parseInt(xval.replace("x=", ""));
				if(x<lowestX) lowestX = x;
				if(x>highestX) highestX = x;
				valuesX.add(x);
			}
			List<Integer> valuesY = new ArrayList<>();
			if(yval.contains("..")){
				for(String val : yval.replace("y=", "").split("\\.\\.")){
					int y = Integer.parseInt(val);
					if(y<lowestY) lowestY = y;
					if(y>highestY) highestY = y;
					valuesY.add(y);
				}
			}else {
				int y = Integer.parseInt(yval.replace("y=", ""));
				if(y<lowestY) lowestY = y;
				if(y>highestY) highestY = y;
				valuesY.add(y);
			}
			valuesXY.add(valuesX);
			valuesXY.add(valuesY);
			valuesParsed.add(valuesXY);
		}
		
		int padding = -300;
		char[][] grid = new char[highestY+1][401];
		for(int y=0; y<grid.length; y++)
			for(int x=0; x<grid[y].length; x++)
				grid[y][x]='.';
		
		for(List<List<Integer>> valuesXY : valuesParsed) {
			if(valuesXY.get(0).size()==1) {
				int x = valuesXY.get(0).get(0);
				for(int y=valuesXY.get(1).get(0); y<=valuesXY.get(1).get(1); y++) {
					grid[y][x+padding]='#';
				}
				
			}else {
				int y = valuesXY.get(1).get(0);
				for(int x=valuesXY.get(0).get(0); x<=valuesXY.get(0).get(1); x++) {
					grid[y][x+padding]='#';
				}
			}
		}
		grid[0][500+padding]='+';
		int[] waterSourceXY = new int[] {500+padding, 0};
		// LOGIC
//		displayMap(grid);
		pourWater(grid, waterSourceXY, highestY);
		int flowing=0;
		int resting=0;
		for(int y=0; y<grid.length; y++) {
			for(int x=0; x<grid[y].length; x++) {
				if(y>=lowestY)
					if(grid[y][x]=='~')
						resting++;
					else if(grid[y][x]=='|')
						flowing++;
			}
		}
//		displayMap(grid);

		return resting;
	}

	@Override
	protected void test() throws Exception {
		assert(A("x=495, y=2..7\n" + 
				"y=7, x=495..501\n" + 
				"x=501, y=3..7\n" + 
				"x=498, y=2..4\n" + 
				"x=506, y=1..2\n" + 
				"x=498, y=10..13\n" + 
				"x=504, y=10..13\n" + 
				"y=13, x=498..504")==57);

	}

}
