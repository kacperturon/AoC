package aoc.days;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import aoc.Day;

public class Day15 extends Day<Integer, Integer> {
	public static void main(String[] args) {
		(new Day15()).run(15);
	}
	
	public class Unit implements Comparable<Unit> {
		int x;
		int y;
		int hp;
		char type;
		int atk = 3;
		
		public Unit(int x, int y, char type) {
			this.x=x;
			this.y=y;
			this.hp=200;
			this.type=type;
		}
		public Unit(int x, int y, char type, int atk) {
			this.x=x;
			this.y=y;
			this.hp=200;
			this.type=type;
			this.atk = atk;
		}

		@Override
		public int compareTo(Unit o) {
			if(y==o.y && x==o.x) return 0;
			else if((y==o.y && x<o.x) || y<o.y) return -1;
			return 1;
		}
	}

	private int countUnitsByType(List<Unit> units, char type) {
		int i=0;
		for(Unit u: units)
			if(u.type==type && u.hp>0)
				i++;
		return i;
	}
	
	public List<Unit> getUnits(List<List<Integer>> coords, List<Unit> units){
		List<Unit> out = new ArrayList<>();
		
		for(List<Integer> coord : coords) {
			for(Unit u: units) {
				if(u.x == coord.get(0) && u.y == coord.get(1) && u.hp >0)
					out.add(u);
			}
		}
		return out;
	}
	
	public Unit getLowestHPUnit(List<Unit> units) {
		int lowestHP = Integer.MAX_VALUE;
		Unit lowestHPUnit = null;
		for(Unit u : units) {
			if(u.hp<lowestHP) {
				lowestHP = u.hp;
				lowestHPUnit = u;
			}
		}
		return lowestHPUnit;
	}
	
	private List<Unit> getEnemies(Unit u, char[][] grid, List<Unit> units) {
		List<List<Integer>> neighbours = getNeighbours(u.x, u.y, grid);
		List<List<Integer>> enemies = new ArrayList<>();
		for(List<Integer> n : neighbours) {
			if(u.type=='G' && grid[n.get(1)][n.get(0)] == 'E' || 
			u.type=='E' && grid[n.get(1)][n.get(0)] == 'G') 
				enemies.add(n);
		}
		return getUnits(enemies, units);
	}
	
	private List<List<Integer>> getNeighbours(int x, int y, char[][] grid){
		List<List<Integer>> neighbours = new ArrayList<>();
		if(y-1>0 && grid[y-1][x]!='#') neighbours.add(Arrays.asList(new Integer[] {x,y-1}));
		if(x-1>0 && grid[y][x-1]!='#') neighbours.add(Arrays.asList(new Integer[] {x-1,y})); 
		if(x+1<grid[0].length && grid[y][x+1]!='#') neighbours.add(Arrays.asList(new Integer[] {x+1,y}));
		if(y+1<grid.length && grid[y+1][x]!='#') neighbours.add(Arrays.asList(new Integer[] {x,y+1}));
		return neighbours;
	}

	private List<List<Integer>> backtrace(Map<List<Integer>, List<Integer>> parent, List<Integer> start, List<Integer> end){
		List<List<Integer>> path = new ArrayList<>();
		path.add(end);
		while(!path.get(path.size()-1).equals(start)) {
			List<Integer> t = path.get(path.size()-1);
			path.add(parent.get(path.get(path.size()-1)));
		}
		Collections.reverse(path);
		return path;
	}
	
	private List<Integer> DFS(Unit u, char[][] grid) {
		char goal;
		if(u.type=='G') goal = 'E';
		else goal = 'G';
		
		Deque<List<Integer>> queue = new ArrayDeque<>(); 
		queue.add(Arrays.asList(new Integer[] {u.x, u.y}));
		
		List<List<Integer>> visited = new ArrayList<>();
		visited.add(Arrays.asList(new Integer[] {u.x, u.y}));
		
		Map<List<Integer>, List<Integer>> parent = new HashMap<>();
		
		while(!queue.isEmpty()) {
			List<Integer> node = queue.pop();
			if(grid[node.get(1)][node.get(0)]==goal) {
				List<List<Integer>> result = backtrace(parent, 
						Arrays.asList(new Integer[] {u.x, u.y}),
						Arrays.asList(new Integer[] {node.get(0), node.get(1)}));
				if(result.size()>1) return result.get(1);
				return null;
			}
				
			for(List<Integer> coords : getNeighbours(node.get(0), node.get(1), grid)) {
				if(grid[coords.get(1)][coords.get(0)]!=u.type) {
					if(!visited.contains(coords)) {
						parent.put(Arrays.asList(new Integer[] {coords.get(0), coords.get(1)}),
								Arrays.asList(new Integer[] {node.get(0), node.get(1)}));
						visited.add(coords);
						queue.add(coords);
					}
				}
			}
		}
		return null;
	}

	private void displayMap(char[][] grid, List<Unit> units) {
		String out="";
		for(int y=0; y<grid.length; y++) {
			for(int x=0; x<grid[0].length; x++) {
				out+=grid[y][x];
			}
			List<Unit> hp = new ArrayList<>();
			for(Unit u: units) {
				if(u.y ==y && u.hp>0)
				out+="(" +u.hp+ ")";
			}
			out+="\n";
		}
		System.out.println(out.trim());
	}
	
	public Integer solve(String input, boolean noElvesDie, int power) {
		// PARSING
		String[] lines = input.split("\n");
		char[][] grid = new char[lines.length][lines[0].length()];
		List<Unit> units = new ArrayList<>();
		int elvesCount = 0;
		for(int y=0; y<lines.length; y++) {
			for(int x=0; x<lines[y].length(); x++) {
				char c = lines[y].charAt(x);
				if(c=='G') units.add(new Unit(x,y,'G'));
				else if(c=='E') {
					units.add(new Unit(x,y,'E', power));
					elvesCount++;
				}
				grid[y][x]=c;
			}
		}
		// LOGIC STARTS HERE
		int counter = 0;
		while(true) {
			Collections.sort(units);
//			System.out.println("ROUND: "+counter);
//			displayMap(grid, units);
			
			for(int i=0; i<units.size();i++) {
				Unit u = units.get(i);
				if(u.hp<=0) continue; // skip dead unit

				// check if unit is blocked (cant move or attack)
				List<Unit> enemies = getEnemies(u,grid,units);
				if(enemies.isEmpty()) { // no enemies in range
					List<Integer> nextCell = DFS(u, grid);
					if(nextCell == null) continue;
					grid[u.y][u.x]='.';
					grid[nextCell.get(1)][nextCell.get(0)]=u.type;
					u.x = nextCell.get(0); u.y = nextCell.get(1);
				}
				// if can attack again
				enemies = getEnemies(u, grid, units);
				if(!enemies.isEmpty()) {
					Unit enemy = getLowestHPUnit(enemies);
					enemy.hp -= u.atk;
					if(enemy.hp<=0) grid[enemy.y][enemy.x] = '.';
				}
				if(noElvesDie && elvesCount != countUnitsByType(units, 'E'))
					return -1;
				if(u.type == 'E' && countUnitsByType(units,'G')<=0
						|| u.type == 'G' && countUnitsByType(units,'E')<=0) {
					if(i==units.size()-1) // unit died at the end of the round
						counter++;
//					displayMap(grid, units);
					int remainingHP = 0;
					for(Unit t: units) {
						if(t.hp>0)
						remainingHP+=t.hp;
					}
					return counter*remainingHP;
				}
			}
			counter++;
		}

	}
	
	@Override
	protected Integer A(String input) throws Exception {
		return solve(input, false, 3);
	}
	
	@Override
	protected Integer B(String input) throws Exception {
		int power = 3;
		while(true) {
			int score = solve(input, true, power);
			if(score!=-1) {
				return score;
			}
			power++;
		}
	}

	@Override
	protected void test() throws Exception {
	assert(A("#######\n" + 
			"#.G...#\n" + 
			"#...EG#\n" + 
			"#.#.#G#\n" + 
			"#..G#E#\n" + 
			"#.....#\n" + 
			"#######")==27730);
	assert(A("#######\n" + 
			"#G..#E#\n" + 
			"#E#E.E#\n" + 
			"#G.##.#\n" + 
			"#...#E#\n" + 
			"#...E.#\n" + 
			"#######")==36334);
	assert(A("#######\n" + 
			"#E..EG#\n" + 
			"#.#G.E#\n" + 
			"#E.##E#\n" + 
			"#G..#.#\n" + 
			"#..E#.#\n" + 
			"#######")==39514);
	assert(A("#######\n" + 
			"#E.G#.#\n" + 
			"#.#G..#\n" + 
			"#G.#.G#\n" + 
			"#G..#.#\n" + 
			"#...E.#\n" + 
			"#######")==27755);
	assert(A("#######\n" + 
			"#.E...#\n" + 
			"#.#..G#\n" + 
			"#.###.#\n" + 
			"#E#G#G#\n" + 
			"#...#G#\n" + 
			"#######")==28944);
	assert(A("#########\n" + 
			"#G......#\n" + 
			"#.E.#...#\n" + 
			"#..##..G#\n" + 
			"#...##..#\n" + 
			"#...#...#\n" + 
			"#.G...G.#\n" + 
			"#.....G.#\n" + 
			"#########\n")==18740);
	}

}
