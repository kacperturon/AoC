package aoc.days;

import aoc.Day;

public class Day11 extends Day<String, String> {
	public static void main(String[] args) {
		(new Day11()).run(11);
	}
	
	public class ScanArea{
		FuelCell[][] cells;
		public ScanArea(int x, int y, int sn, int width, int height) {
			cells = new FuelCell[height][width]; 
			for(int i=0;i<height;i++) {
				for(int j=0; j<width; j++) {
					if(x+j<301 && y+i<301) {
						cells[i][j]= new FuelCell((x+j+1),(y+i+1),sn);
					}
					else
						cells[i][j] = null;
				}
			}
		}
		
		public int getSum() {
			int sum = 0;
			for(int i=0 ;i<cells.length; i++) {
				for(int j=0 ;j<cells[0].length; j++) {
					sum += cells[i][j] == null ? 0 : cells[i][j].powerLevel();
				}
			}
			return sum;
		}
	}
	
	public class FuelCell{
		int x;
		int y;
		int sn;
		
		public FuelCell(int x, int y, int sn) {
			this.x = x;
			this.y = y;
			this.sn = sn;
		}
		public int getRackID() {
			return x+10;
		}
		public int powerLevel() {
			int val = (y*getRackID()+sn)*getRackID();
			
			val = val/100;
			val = val%10;
			
			return val-5;
		}
	}
	
	@Override
	protected String A(String input) throws Exception {
		int sn = Integer.parseInt(input);
		int highestTotalPower = Integer.MIN_VALUE;
		ScanArea sa;
		ScanArea highestSa = null;
		for(int y=1; y<=300; y++) {
			for(int x=1; x<=300; x++) {
				sa = new ScanArea(x,y,sn, 3, 3);
				if(sa.getSum()>highestTotalPower) {
					highestSa = sa;
					highestTotalPower = sa.getSum();
				}
			}
		}
		return highestSa.cells[0][0].x + ","+highestSa.cells[0][0].y;
	}

	@Override
	protected String B(String input) throws Exception {
		int sn = Integer.parseInt(input);
		int highestTotalPower = Integer.MIN_VALUE;
		ScanArea sa;
		ScanArea highestSa = null;
		int size=0;
		for(int w=1; w<=20; w++) {
			for(int y=1; y<=301-w; y++) {
				for(int x=1; x<=301-w; x++) {
					sa = new ScanArea(x,y,sn, w, w);
					if(sa.getSum()>highestTotalPower) {
						highestSa = sa;
						highestTotalPower = sa.getSum();
						size = w;
					}
				}
			}
//			System.out.println(highestSa.cells[0][0].x + ","+highestSa.cells[0][0].y+","+size + " "+w);
		}
		return highestSa.cells[0][0].x + ","+highestSa.cells[0][0].y+","+size;

	}

	@Override
	protected void test() throws Exception {
		assert(A("42").equals("21,61"));
		
	}

}
