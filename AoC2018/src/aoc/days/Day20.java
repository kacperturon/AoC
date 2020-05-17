package aoc.days;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aoc.Day;

public class Day20 extends Day<Integer, Integer>{
	public static void main(String[] args) {
		(new Day20()).run(20);
	}
	
	private void displayMap(char[][] grid) {
		String out = "";
		for(int y=0; y<grid.length; y++) {
			for(int x=0; x<grid[y].length; x++) {
				if(grid[y][x]==Character.MIN_VALUE)
					out+='#';
				else out+=grid[y][x];
			}
			out+="\n";
		}
		System.out.println(out);
	}
	
	@Override
	protected Integer A(String input) throws Exception {
		char[][] grid = new char[300][300];
		grid[150][150]='X';
//		displayMap(grid);
		exploreMap(grid, input, 1, 150, 150);
		return DFS(grid, 150,150, false);
	}
		
	private int backtrace(Map<List<Integer>, List<Integer>> parent, List<Integer> start, List<Integer> end){
		List<List<Integer>> path = new ArrayList<>();
		int count = 0;
		path.add(end);
		while(!path.get(path.size()-1).equals(start)) {
			List<Integer> t = path.get(path.size()-1);
			path.add(parent.get(path.get(path.size()-1)));
			count++;
		}
		Collections.reverse(path);
		return count;
	}
	
	private int DFS(char[][] grid, int x, int y, boolean countMoreThan1kDoors) {
		
		int roomCount = 0;
		if(!countMoreThan1kDoors) roomCount = Integer.MIN_VALUE;
		
		Deque<List<Integer>> queue = new ArrayDeque<>(); 
		queue.add(Arrays.asList(new Integer[] {x, y}));
		List<List<Integer>> visited = new ArrayList<>();
		visited.add(Arrays.asList(new Integer[] {x, y}));
		Map<List<Integer>, List<Integer>> parent = new HashMap<>();

		while(!queue.isEmpty()) {
			List<Integer> node = queue.pop();
			for(List<Integer> coords : getNeighbours(node.get(0), node.get(1), grid)) {
				if(!visited.contains(coords))
					visited.add(coords);
				
				List<Integer> nextRoom = null;
				if(grid[coords.get(1)][coords.get(0)] == '-' && node.get(1)>coords.get(1)) {
					if(coords.get(1)-1>0) nextRoom = Arrays.asList(new Integer[] {coords.get(0),coords.get(1)-1});
				}else if(grid[coords.get(1)][coords.get(0)]=='-'&& node.get(1)<coords.get(1)) {
					if(coords.get(1)+1<grid.length) nextRoom = Arrays.asList(new Integer[] {coords.get(0),coords.get(1)+1});
				}else if(grid[coords.get(1)][coords.get(0)]=='|'&& node.get(0)<coords.get(0)) {
					if(coords.get(0)-1>0) nextRoom = Arrays.asList(new Integer[] {coords.get(0)+1,coords.get(1)});
				}else if(grid[coords.get(1)][coords.get(0)]=='|'&& node.get(0)>coords.get(0))
					if(coords.get(0)+1<grid[0].length) nextRoom = Arrays.asList(new Integer[] {coords.get(0)-1,coords.get(1)});
				if(nextRoom!=null && !visited.contains(nextRoom)) {
					parent.put(Arrays.asList(new Integer[] {nextRoom.get(0), nextRoom.get(1)}),
							Arrays.asList(new Integer[] {node.get(0), node.get(1)}));
					visited.add(nextRoom);
					queue.add(nextRoom);
					int val = backtrace(parent, 
							Arrays.asList(new Integer[] {x, y}),
							Arrays.asList(new Integer[] {nextRoom.get(0), nextRoom.get(1)}));
					if(!countMoreThan1kDoors) {
						if(val>roomCount)
							roomCount = val;
					}else
						if(val>999)
							roomCount++;
				}
			}
		}
		
		
		return roomCount;
	}
	
	
	private List<List<Integer>> getNeighbours(int x, int y, char[][] grid){
		List<List<Integer>> neighbours = new ArrayList<>();
		if(y-1>0 && grid[y-1][x]=='-') neighbours.add(Arrays.asList(new Integer[] {x,y-1}));
		if(y+1<grid.length && grid[y+1][x]=='-') neighbours.add(Arrays.asList(new Integer[] {x,y+1}));
		if(x-1>0 && grid[y][x-1]=='|') neighbours.add(Arrays.asList(new Integer[] {x-1,y}));
		if(x+1<grid[0].length && grid[y][x+1]=='|') neighbours.add(Arrays.asList(new Integer[] {x+1,y}));
		return neighbours;
	}
	
	private int exploreMap(char[][] grid, String input, int pointer, int x, int y) {
		char c = 0;
		int localX = x, localY = y;
		while(c!='$') {
			c=input.charAt(pointer);
			if(c==')') return pointer;
			if(c=='|') {
				localX = x;
				localY = y;
			}
			if(c=='(') pointer=exploreMap(grid, input, pointer+1, localX, localY);
			if(c=='N') {
				localY--;
				grid[localY][localX]='-';
				localY--;
				grid[localY][localX]='.';
			}
			else if(c=='S') {
				localY++;
				grid[localY][localX]='-';
				localY++;
				grid[localY][localX]='.';

			}else if(c=='E') {
				localX++;
				grid[localY][localX]='|';
				localX++;
				grid[localY][localX]='.';
			}else if(c=='W') {
				localX--;
				grid[localY][localX]='|';
				localX--;
				grid[localY][localX]='.';
			}
			pointer++;
			
		}
		return pointer;
		
	}

	@Override
	protected Integer B(String input) throws Exception {
		char[][] grid = new char[300][300];
		grid[150][150]='X';
		exploreMap(grid, input, 1, 150, 150);
//		displayMap(grid);
		return DFS(grid, 150,150, true);
	}

	@Override
	protected void test() throws Exception {
		assert(A("^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))$")==23);
		assert(A("^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))$")==31);
		
	}

}
