package aoc.days;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import aoc.Day;

public class Day7 extends Day<String, Integer> {
	public static void main(String[] args) {
		(new Day7()).run(7);
	}
	
	@Override
	protected String A(String input) throws Exception {
		String[] lines = input.split("\n");
		Map<Character, List<Character>> order = new HashMap<>();
		Set<Character> jobs = new HashSet<>(); 
		for(String line : lines) {
			String[] val = line.split(" ");
			char prerequisite = val[1].charAt(0);
			char task = val[7].charAt(0);
			jobs.add(prerequisite);
			jobs.add(task);
			
			List<Character> l;
			if(order.get(task)==null)l = new ArrayList<>();
			else l = order.get(task);
			l.add(prerequisite);
			order.put(task, l);
		}

		String out = "";
		
		while(!jobs.isEmpty()) {
			for(int i='A'; i<='Z';i++) {
				Character c = (char) i;
				if(jobs.contains((char)i)) {
					List<Character> l = order.get(c);
					if((l == null || l.isEmpty()) && out.indexOf(c) == -1) {
						out+=c;
						for(Map.Entry<Character, List<Character>> entry:order.entrySet()) {
							entry.getValue().remove(c);
						}
						jobs.remove(c);
						break;
					}
				}
			}
		}
		return out;
	}

	@Override
	protected Integer B(String input) throws Exception {
		String[] lines = input.split("\n");
		Map<Character, List<Character>> order = new HashMap<>();
		Set<Character> jobs = new HashSet<>(); 
		for(String line : lines) {
			String[] val = line.split(" ");
			char prerequisite = val[1].charAt(0);
			char task = val[7].charAt(0);
			jobs.add(prerequisite);
			jobs.add(task);
			
			List<Character> l;
			if(order.get(task)==null)l = new ArrayList<>();
			else l = order.get(task);
			l.add(prerequisite);
			order.put(task, l);
		}

		String out = "";
		// create 5 employees in idle (0) and how many seconds completed (0)
		Map<Integer, Integer[]> employeeTask = new HashMap<>();
		employeeTask.put(1, new Integer[] {0,0});
		employeeTask.put(2, new Integer[] {0,0});
		employeeTask.put(3, new Integer[] {0,0});
		employeeTask.put(4, new Integer[] {0,0});
		employeeTask.put(5, new Integer[] {0,0});
		int tick = 0;
		boolean shouldTick =false;
		while(!jobs.isEmpty()) {
//			printEmployees(employeeTask);
			boolean isIdle = false;
			for(Entry<Integer, Integer[]> et : employeeTask.entrySet()) {
				if(et.getValue()[0]==0) {
					isIdle= true;
				}
				else {
					shouldTick = true;
					if(et.getValue()[1]>0) {
						et.getValue()[1]--; // Tick each worker
					}
					if(et.getValue()[1]==0) {
						Character c = (char)(int)et.getValue()[0];
						for(Map.Entry<Character, List<Character>> entry:order.entrySet()) {
							entry.getValue().remove(c);
						}
						jobs.remove(c);
						out+= c;
						et.getValue()[0] = 0;
						isIdle = true;
					}
				}
			}
			
			if(isIdle)
			{
				Set<Character> inProgress = new HashSet<>();
				for(Entry<Integer, Integer[]> et : employeeTask.entrySet()) {
					if(et.getValue()[0] != 0)
						inProgress.add((char)(int)et.getValue()[0]);
				}
					for(Entry<Integer, Integer[]> et : employeeTask.entrySet()) {
						if(et.getValue()[0]==0) {
							for(int i='A'; i<='Z';i++) {
								Character c = (char) i;
								if(jobs.contains(c) && !inProgress.contains(c) && out.indexOf(c)== -1) {
									List<Character> l = order.get(c);
									if((l == null || l.isEmpty()) && out.indexOf(c) == -1) {
										et.setValue(new Integer[] {i, i-4});
										inProgress.add(c);
										break;
									}
								}
							}
						}
					}
			}
			if(shouldTick)
				tick++;
		}
		return tick;
	}
	
	private void printEmployees(Map<Integer, Integer[]> e) {
		for(Entry<Integer, Integer[]> emp : e.entrySet()) {
			System.out.println("Employee: "+emp.getKey()+" works on: "+ emp.getValue()[0] +" progress: " +emp.getValue()[1]);
		}
	}

	@Override
	protected void test() throws Exception {
		assert(A(
				"Step D must be finished before step A can begin.\nStep C must be finished before step B can begin.\nStep B must be finished before step A can begin."
				).equals("CBDA"));
		assert(B(
				"Step D must be finished before step A can begin.\nStep C must be finished before step B can begin.\nStep B must be finished before step A can begin."
				)==186);
		
	}

}
