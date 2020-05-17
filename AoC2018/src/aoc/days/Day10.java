package aoc.days;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import aoc.Day;

public class Day10 extends Day<Void, Integer>{
	public static void main(String[] args) {
		(new Day10()).run(10);
	}
	
	public class Point{
		int x;
		int y;
		int xv;
		int yv;
		public Point(int x, int y, int xv, int yv) {
			this.x=x;
			this.y=y;
			this.xv=xv;
			this.yv=yv;
		}
		public void updateXY() {
			x+=xv;
			y+=yv;
		}
	}
	
	public void printStars(Map<List<Integer>, String> repr) {
		int xL = Integer.MAX_VALUE;
		int yL = Integer.MAX_VALUE;
		int xH = Integer.MIN_VALUE;
		int yH = Integer.MIN_VALUE;
		for(Map.Entry<List<Integer>, String> e: repr.entrySet()) {
			if(e.getKey().get(0)<xL) xL = e.getKey().get(0);
			if(e.getKey().get(0)>xH) xH = e.getKey().get(0);
			if(e.getKey().get(1)<yL) yL = e.getKey().get(1);
			if(e.getKey().get(1)>yH) yH = e.getKey().get(1);
		}
		for(int y=yL; y<=yH;y++) {
			for(int x=xL; x<=xH; x++) {
				List<Integer> l = new ArrayList<>();
				l.add(x);
				l.add(y);
				System.out.print(repr.getOrDefault(l, " "));
			}
			System.out.println();
		}
		
		
	}
	
	@Override
	protected Void A(String input) throws Exception {
		String[] lines = input.split("\n");
		List<Point> points = new ArrayList<>();
		for(String line: lines) {
			 Matcher m = Pattern.compile("(<.*> |<.*>$)").matcher(line);
			 m.find();
			 String[] coords = m.group().replaceAll("<?>?", "").split(",");
			 m.find();
			 String[] velos = m.group().replaceAll("<?>?", "").split(",");
			 points.add(new Point(
					 Integer.parseInt(coords[0].trim()), 
					 Integer.parseInt(coords[1].trim()),
					 Integer.parseInt(velos[0].trim()),
					 Integer.parseInt(velos[1].trim())
					 ));
		}

		Map<List<Integer>, String> starRepr = new HashMap<>();
		
		for(int i=0; i<20000; i++) {
			starRepr.clear();
			for(Point p : points) {
				List<Integer> l = new ArrayList<>();
				l.add(p.x);
				l.add(p.y);
				starRepr.put(l, "#");
				p.updateXY();
			}
			
			int xL = Integer.MAX_VALUE;
			int yL = Integer.MAX_VALUE;
			int xH = Integer.MIN_VALUE;
			int yH = Integer.MIN_VALUE;
			for(Map.Entry<List<Integer>, String> e: starRepr.entrySet()) {
				if(e.getKey().get(0)<xL) xL = e.getKey().get(0);
				if(e.getKey().get(0)>xH) xH = e.getKey().get(0);
				if(e.getKey().get(1)<yL) yL = e.getKey().get(1);
				if(e.getKey().get(1)>yH) yH = e.getKey().get(1);
			}
			
			if(yH-yL<10) {
				if(xH-xL<100) {
					printStars(starRepr);
				}
			}
			
			
		}
		return null;
	}

	@Override
	protected Integer B(String input) throws Exception {
		String[] lines = input.split("\n");
		List<Point> points = new ArrayList<>();
		for(String line: lines) {
			 Matcher m = Pattern.compile("(<.*> |<.*>$)").matcher(line);
			 m.find();
			 String[] coords = m.group().replaceAll("<?>?", "").split(",");
			 m.find();
			 String[] velos = m.group().replaceAll("<?>?", "").split(",");
			 points.add(new Point(
					 Integer.parseInt(coords[0].trim()), 
					 Integer.parseInt(coords[1].trim()),
					 Integer.parseInt(velos[0].trim()),
					 Integer.parseInt(velos[1].trim())
					 ));
		}

		Map<List<Integer>, String> starRepr = new HashMap<>();
		
		for(int i=0; i<20000; i++) {
			starRepr.clear();
			for(Point p : points) {
				List<Integer> l = new ArrayList<>();
				l.add(p.x);
				l.add(p.y);
				starRepr.put(l, "#");
				p.updateXY();
			}
			
			int xL = Integer.MAX_VALUE;
			int yL = Integer.MAX_VALUE;
			int xH = Integer.MIN_VALUE;
			int yH = Integer.MIN_VALUE;
			for(Map.Entry<List<Integer>, String> e: starRepr.entrySet()) {
				if(e.getKey().get(0)<xL) xL = e.getKey().get(0);
				if(e.getKey().get(0)>xH) xH = e.getKey().get(0);
				if(e.getKey().get(1)<yL) yL = e.getKey().get(1);
				if(e.getKey().get(1)>yH) yH = e.getKey().get(1);
			}
			
			if(yH-yL<10) {
				if(xH-xL<100) {
					return i;
				}
			}
			
			
		}
		return -1;
	}

	@Override
	protected void test() throws Exception {
	    A("position=< 9,  1> velocity=< 0,  2>\nposition=< 7,  0> velocity=<-1,  0>\nposition=< 3, -2> velocity=<-1,  1>\nposition=< 6, 10> velocity=<-2, -1>\nposition=< 2, -4> velocity=< 2,  2>\nposition=<-6, 10> velocity=< 2, -2>\nposition=< 1,  8> velocity=< 1, -1>\nposition=< 1,  7> velocity=< 1,  0>\nposition=<-3, 11> velocity=< 1, -2>\nposition=< 7,  6> velocity=<-1, -1>\nposition=<-2,  3> velocity=< 1,  0>\nposition=<-4,  3> velocity=< 2,  0>\nposition=<10, -3> velocity=<-1,  1>\nposition=< 5, 11> velocity=< 1, -2>\nposition=< 4,  7> velocity=< 0, -1>\nposition=< 8, -2> velocity=< 0,  1>\nposition=<15,  0> velocity=<-2,  0>\nposition=< 1,  6> velocity=< 1,  0>\nposition=< 8,  9> velocity=< 0, -1>\nposition=< 3,  3> velocity=<-1,  1>\nposition=< 0,  5> velocity=< 0, -1>\nposition=<-2,  2> velocity=< 2,  0>\nposition=< 5, -2> velocity=< 1,  2>\nposition=< 1,  4> velocity=< 2,  1>\nposition=<-2,  7> velocity=< 2, -2>\nposition=< 3,  6> velocity=<-1, -1>\nposition=< 5,  0> velocity=< 1,  0>\nposition=<-6,  0> velocity=< 2,  0>\nposition=< 5,  9> velocity=< 1, -2>\nposition=<14,  7> velocity=<-2,  0>\nposition=<-3,  6> velocity=< 2, -1>");		
	}

}
