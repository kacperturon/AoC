package aoc.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aoc.Day;

public class Day23 extends Day<Integer, Integer> {
	public static void main(String[] args) {
		(new Day23()).run(23);
	}
	
	@Override
	protected Integer A(String input) throws Exception {
		String[] lines = input.split("\n");
		int[][] values = new int[lines.length][4];
		for(int i =0 ; i< lines.length; i++) {
			String line = lines[i];
			String[] v = line.split(", ");
			String[] v2 = v[0].substring(5, v[0].length()-1).split(",");
			values[i][0] = Integer.parseInt(v2[0]);
			values[i][1] = Integer.parseInt(v2[1]);
			values[i][2] = Integer.parseInt(v2[2]);
			values[i][3] = Integer.parseInt(v[1].substring(2));
		}
		int largestNanoIndex = 0;
		int largestRad = Integer.MIN_VALUE;
		for(int i=0; i<values.length;i++) {
			int[] val = values[i];
			if(val[3]>largestRad) {
				largestRad = val[3];
				largestNanoIndex = i;
			}
		}
		int val = countWithinDistance(largestNanoIndex, values);
		
		return val+1;
	}
	
	private int countWithinDistance(int largestIndex, int[][] values) {
		int count = 0;
		for(int i=0; i<values.length; i++) {
			if(i==largestIndex) continue;
			int[] val = values[i];
			int dist = getManhattanDistance(val, values[largestIndex]);
			if(dist<=values[largestIndex][3]) count++;
			
		}
		return count;
	}
	
	private Map<List<Integer>, Integer> countWithinDistance(int[][] values) {
		Map<List<Integer>, Integer> distances = new HashMap<>();
		for(int z=Integer.MIN_VALUE; z<Integer.MAX_VALUE; z++)
			for(int y=Integer.MIN_VALUE; y<Integer.MAX_VALUE; y++)
				for(int x=Integer.MIN_VALUE; x<Integer.MAX_VALUE; x++) {
					int count=0;
					for(int i=1; i<values.length; i++) {
						int[] val = values[i];
						int dist = getManhattanDistance(val, new int[] {x,y,z});
						if(dist<=val[3]) count++;
					}
					List l = new ArrayList<>();
					l.add(x);
					l.add(y);
					l.add(z);
					distances.put(l, count);
				}
		return distances;
	}
	
	private int getManhattanDistance(int[] val1, int[] val2) {
		return Math.abs(val1[0]-val2[0]) +
				Math.abs(val1[1]-val2[1]) +
				Math.abs(val1[2]-val2[2]);
	}

	@Override
	protected Integer B(String input) throws Exception {
		String[] lines = input.split("\n");
		int[][] values = new int[lines.length][4];
		for(int i =0 ; i< lines.length; i++) {
			String line = lines[i];
			String[] v = line.split(", ");
			String[] v2 = v[0].substring(5, v[0].length()-1).split(",");
			values[i][0] = Integer.parseInt(v2[0]);
			values[i][1] = Integer.parseInt(v2[1]);
			values[i][2] = Integer.parseInt(v2[2]);
			values[i][3] = Integer.parseInt(v[1].substring(2));
		}
		
		Map<List<Integer>, Integer> distances = countWithinDistance(values);

		int largestC = Integer.MIN_VALUE;
		for(Map.Entry<List<Integer>, Integer> d : distances.entrySet()) {
			int c = d.getValue();
			if(c>largestC) {
				largestC=c;
			}
		}
		
		int shortestDistance = Integer.MAX_VALUE;
		for(Map.Entry<List<Integer>, Integer> d : distances.entrySet()) {
			if(d.getValue()==largestC) {
				int[] arr = new int[d.getKey().size()];
				for(int i=0; i<arr.length;i++)
					arr[i] = d.getKey().get(i);
				int dist = getManhattanDistance(new int[] {0,0,0}, arr);
				if(dist<shortestDistance)
					shortestDistance = dist;
			}
		}
		System.out.println(shortestDistance);
		
		return shortestDistance;
	}

	@Override
	protected void test() throws Exception {
//		assert(A("pos=<0,0,0>, r=4\n" + 
//				"pos=<1,0,0>, r=1\n" + 
//				"pos=<4,0,0>, r=3\n" + 
//				"pos=<0,2,0>, r=1\n" + 
//				"pos=<0,5,0>, r=3\n" + 
//				"pos=<0,0,3>, r=1\n" + 
//				"pos=<1,1,1>, r=1\n" + 
//				"pos=<1,1,2>, r=1\n" + 
//				"pos=<1,3,1>, r=1")==7);
		assert(B("pos=<10,12,12>, r=2\n" + 
				"pos=<12,14,12>, r=2\n" + 
				"pos=<16,12,12>, r=4\n" + 
				"pos=<14,14,14>, r=6\n" + 
				"pos=<50,50,50>, r=200\n" + 
				"pos=<10,10,10>, r=5")==36);
	}

}
