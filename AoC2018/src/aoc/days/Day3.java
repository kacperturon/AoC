package aoc.days;
import java.util.ArrayList;
import java.util.List;
import aoc.Day;

public class Day3 extends Day<Integer, Integer> {
	public static void main(String[] args) {
		(new Day3()).run(3);
	}

	@Override
	protected Integer A(String input) {
		int count = 0;
		int[][] grid = new int[1000][1000];
		String[] lines = input.split("\n");
		for(String val: lines) {
			String[] vals = val.split(" ");
			String[] coordinates = vals[2].substring(0, vals[2].length()-1).split(",");
			String[] dimensions = vals[3].split("x");
			int claimNo = Integer.parseInt(vals[0].substring(1));
			int x = Integer.parseInt(coordinates[0]);
			int y = Integer.parseInt(coordinates[1]);
			int width = Integer.parseInt(dimensions[0]);
			int height = Integer.parseInt(dimensions[1]);
			for(int i=0;i<height;i++) {
				for(int j=0;j<width;j++) {
					if(grid[y+i][x+j]==-1)continue;
					if(grid[y+i][x+j]!=0) {
						grid[y+i][x+j] = -1;
						count++;
					}else {
						grid[y+i][x+j]=claimNo;
					}
				}
			}
		}
		return count;
	}

	@Override
	protected Integer B(String input) {
		int[][] grid = new int[1000][1000];
		List<Integer> claimsNoOverlap = new ArrayList<>();
		String[] lines = input.split("\n");
		for(String val: lines) {
			String[] vals = val.split(" ");
			String[] coordinates = vals[2].substring(0, vals[2].length()-1).split(",");
			String[] dimensions = vals[3].split("x");
			int claimNo = Integer.parseInt(vals[0].substring(1));
			int x = Integer.parseInt(coordinates[0]);
			int y = Integer.parseInt(coordinates[1]);
			int width = Integer.parseInt(dimensions[0]);
			int height = Integer.parseInt(dimensions[1]);
			
			claimsNoOverlap.add(claimNo);
			for(int i=0;i<height;i++) {
				for(int j=0;j<width;j++) {
					if(grid[y+i][x+j]!=0) {
						claimsNoOverlap.remove(new Integer(grid[y+i][x+j]));
						claimsNoOverlap.remove(new Integer(claimNo));
						grid[y+i][x+j] = -1;
					}else grid[y+i][x+j]=claimNo;
				}
			}
		}
		return claimsNoOverlap.get(0);
	}

	@Override
	protected void test() {
		// TODO Auto-generated method stub
		
	}
}
