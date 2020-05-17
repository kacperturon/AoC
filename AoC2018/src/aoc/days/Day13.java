package aoc.days;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import aoc.Day;

public class Day13 extends Day<String, String> {
	public static void main(String[] args) {
		(new Day13()).run(13);
	}
	
	Character[][] grid;
	List<Cart> carts;
	
	public class Cart implements Comparable<Cart>{
		int x;
		int y;
		char c;
		int intersectMove;
		public Cart(int x, int y, char c) {
			this.x = x;
			this.y = y;
			this.c = c;
			this.intersectMove = Integer.MIN_VALUE;
		}
		
		public void tick() {
			if(c=='>') {
				char next = grid[y][x+1];
				if(next == '+') crossIntersection();
				else {
					if(next == '\\') c='v';
					if(next == '/') c='^';
				}
				x++;
			}else if(c=='<') {
				char next = grid[y][x-1];
				if(next == '+') crossIntersection();
				else {
					if(next == '\\') c='^';
					if(next == '/') c='v';
				}
				x--;
			}else if(c=='^') {
				char next = grid[y-1][x];
				if(next == '+') crossIntersection();
				else {
					if(next == '\\') c='<';
					if(next == '/') c='>';
				}
				y--;
			}else if(c=='v') {
				char next = grid[y+1][x];
				if(next == '+') crossIntersection();
				else {
					if(next == '\\') c='>';
					if(next == '/') c='<';
				}
				y++;
			}
		}
		
		private void crossIntersection() {
			if(intersectMove == Integer.MIN_VALUE) intersectMove = 0;
			else if(intersectMove == 2) intersectMove = 0;
			else intersectMove++;
			
			if(intersectMove==0) {
				if(c=='^') c='<';
				else if(c=='v') c='>';
				else if(c=='>') c='^';
				else if(c=='<') c='v';
			}else if(intersectMove == 2) {
				if(c=='^') c='>';
				else if(c=='v') c='<';
				else if(c=='>') c='v';
				else if(c=='<') c='^';
			}

		}
		
		@Override
		public int compareTo(Cart c2) {
			if(y==c2.y && x==c2.x) return 0;
			else if((y==c2.y && x<c2.x) || y<c2.y) return -1;
			return 1;
		}

	}
	
	private boolean hasCollided(Cart cart) {
		for(Cart c: carts) {
			if(!c.equals(cart)) {
				if(c.x == cart.x && c.y == cart.y) return true;
			}
		}
		return false;
	}
	
	private void removeCollided() {
		Set<Cart> collidingCarts = new HashSet<>();
		
		for(Cart c: carts) {
			for(Cart b: carts) {
				if(!c.equals(b)) {
					if(c.x == b.x && c.y == b.y) {
						collidingCarts.add(b);
						collidingCarts.add(c);
					}
				}
			}
		}
		List<Cart> newCarts = new ArrayList<>();
		for(Cart c: carts)
			if(!collidingCarts.contains(c))
				newCarts.add(c);
		this.carts = newCarts;
	}
	
	public void displayGrid() {
		String out = "";
		for(int y=0; y<grid.length; y++) {
			for(int x=0; x<grid[0].length; x++) {
				if(grid[y][x]!='X'){
					boolean displayCart = false;
					for(Cart c: carts) {
						if(c.y == y && c.x == x) {
							out+=c.c;
							displayCart = true;
						}
					}
					if(!displayCart) out+=grid[y][x];
				}else  out+=grid[y][x];
			}
			out+="\n";
		}
		System.out.print(out);

	}
	
	@Override
	protected String A(String input) throws Exception {
		String[] lines = input.split("\n");
		int gridWidth = lines[0].length();
		int gridHeight = lines.length;
		grid = new Character[gridHeight][gridWidth];
		carts = new ArrayList<>();
		
		int x=0; int y=0;
		for(String line: lines) {
			for(int i=0; i<line.length(); i++) {
				char c = line.charAt(i);
				if(c=='>'||c=='<'||c=='v'||c=='^') {
					carts.add(new Cart(x,y,c));
					if(c=='>'||c=='<')
						c='-';
					else 
						c='|';
				}
				grid[y][x]=c;
				if(x==gridWidth-1) x=0; else x++;
			}
			y++;
		}
		
		Collections.sort(carts);
//		displayGrid();
		while(true) {
			Collections.sort(carts);
			for(Cart cart : carts) {
				cart.tick();
				if(hasCollided(cart)) {
					grid[cart.y][cart.x] = 'X';
					return cart.x+","+cart.y;
					
				}
			}
		}
	}

	@Override
	protected String B(String input) throws Exception {
		String[] lines = input.split("\n");
		int gridWidth = lines[0].length();
		int gridHeight = lines.length;
		grid = new Character[gridHeight][gridWidth];
		carts = new ArrayList<>();
		int x=0; int y=0;
		for(String line: lines) {
			for(int i=0; i<line.length(); i++) {
				char c = line.charAt(i);
				if(c=='>'||c=='<'||c=='v'||c=='^') {
					carts.add(new Cart(x,y,c));
					if(c=='>'||c=='<')
						c='-';
					else 
						c='|';
				}
				grid[y][x]=c;
				if(x==gridWidth-1) x=0; else x++;
			}
			y++;
		}
//		displayGrid();
		
		Collections.sort(carts);
		
		while(true) {
			Collections.sort(carts);
			Iterator<Cart> iter = carts.iterator();
			while(iter.hasNext()) {
				Cart c = iter.next();
				c.tick();
				removeCollided();
			}
			if(carts.size()==1) {
				Cart lastCart = carts.get(0);
				return lastCart.x+","+lastCart.y;
			}
//			displayGrid();
		}
//		displayGrid();
	}

	@Override
	protected void test() throws Exception {
		assert(A("/->-\\        \n" + 
				"|   |  /----\\\n" + 
				"| /-+--+-\\  |\n" + 
				"| | |  | v  |\n" + 
				"\\-+-/  \\-+--/\n" + 
				"  \\------/   ").equals("7,3"));
		assert(B("/->-\\        \n" + 
				"|   |  /----\\\n" + 
				"| /-+-<+-\\  |\n" + 
				"| | |  | v  |\n" + 
				"\\-+-/  \\-+--/\n" + 
				"  \\------/   ").equals("2,0"));
		assert(B("/>-<\\  \n" + 
				"|   |  \n" + 
				"| /<+-\\\n" + 
				"| | | v\n" + 
				"\\>+</ |\n" + 
				"  |   ^\n" + 
				"  \\<->/" + 
				"").equals("6,4"));
	}

}
