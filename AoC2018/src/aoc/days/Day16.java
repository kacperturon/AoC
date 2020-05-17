package aoc.days;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import aoc.Day;

public class Day16 extends Day<Integer, Integer> {
	public static void main(String[] args) {
		(new Day16()).run(16);
	}
	
	Map<Integer, Map<String, Integer>> operationsSummed = new HashMap<>();
	private int matchingOperations(List<Integer> before, List<Integer> operation, List<Integer> after) {
		Map<String, Integer> operations = new HashMap<>();
		//addr
		if(after.get(operation.get(3)) == before.get(operation.get(1)) + before.get(operation.get(2)))
			operations.put("addr", operations.getOrDefault("addr", 0)+1);
		//addi
		if(after.get(operation.get(3)) == operation.get(operation.get(1))+operation.get(2)) 
			operations.put("addi", operations.getOrDefault("addi", 0)+1);
		//mulr
		if(after.get(operation.get(3)) == before.get(operation.get(1))*before.get(operation.get(2))) 
			operations.put("mulr", operations.getOrDefault("mulr", 0)+1);
		//muli
		if(after.get(operation.get(3)) == operation.get(operation.get(1))*operation.get(2))
			operations.put("muli", operations.getOrDefault("muli", 0)+1);
		//banr
		if(after.get(operation.get(3)) == (before.get(operation.get(1))&before.get(operation.get(2))))
			operations.put("banr", operations.getOrDefault("banr", 0)+1);
		//bani
		if(after.get(operation.get(3)) == (before.get(operation.get(1))&before.get(operation.get(2))))
			operations.put("bani", operations.getOrDefault("bani", 0)+1);
		//borr
		if(after.get(operation.get(3)) == (before.get(operation.get(1))|before.get(operation.get(2))))
			operations.put("borr", operations.getOrDefault("borr", 0)+1);
		//bori
		if(after.get(operation.get(3)) == (operation.get(operation.get(1))|operation.get(2)))
			operations.put("bori", operations.getOrDefault("bori", 0)+1);
		//setr
		if(after.get(operation.get(3)) == before.get(operation.get(1)))
			operations.put("setr", operations.getOrDefault("setr", 0)+1);
		//seti
		if(after.get(operation.get(3)) == operation.get(1))
			operations.put("seti", operations.getOrDefault("seti", 0)+1);
		//gtir
		if(after.get(operation.get(3)) == (operation.get(1) > before.get(operation.get(2))?1:0))
			operations.put("gtir", operations.getOrDefault("gtir", 0)+1);
		//gtri
		if(after.get(operation.get(3)) == (before.get(operation.get(1))> operation.get(2)?1:0))
			operations.put("gtri", operations.getOrDefault("gtri", 0)+1);
		//gtrr
		if(after.get(operation.get(3)) == (before.get(operation.get(1))> before.get(operation.get(2))?1:0))
			operations.put("gtrr", operations.getOrDefault("gtrr", 0)+1);
		//eqir
		if(after.get(operation.get(3)) == (operation.get(1) == before.get(operation.get(2))?1:0))
			operations.put("eqir", operations.getOrDefault("eqir", 0)+1);
		//eqri
		if(after.get(operation.get(3)) == (before.get(operation.get(1))== operation.get(2)?1:0))
			operations.put("eqri", operations.getOrDefault("eqri", 0)+1);
		//eqrr
		if(after.get(operation.get(3)) == (before.get(operation.get(1))== before.get(operation.get(2))?1:0))
			operations.put("eqrr", operations.getOrDefault("eqrr", 0)+1);
		int count =0;
		
		Map<String, Integer> op = operationsSummed.getOrDefault(operation.get(0), new HashMap<>());
		for(String k :operations.keySet()) {
			count+=operations.get(k);
			op.put(k, op.getOrDefault(k, 0) + operations.get(k));
		}
		operationsSummed.put(operation.get(0), op);
		return count;
	}
	
	@Override
	protected Integer A(String input) throws Exception {
		// PARSING
		List<List<List<Integer>>> values = new ArrayList<>();
		List<List<Integer>> value = null;
		int counter = 0;
		for(String line : input.split("\n")) {
			if(counter == 0 && line.isEmpty()) break;
			if(counter == 3) {
				counter = 0;
				continue;
			}
			if(counter == 0) value = new ArrayList<>();
			if(counter<3) {
				List<Integer> lineVals = new ArrayList<>();
				line = line.replaceAll("\\D+"," ").trim();
				for(String val : line.split(" ")) {
					lineVals.add(Integer.parseInt(val));
				}
				value.add(lineVals);
			}
			counter++;

			if(counter == 3) values.add(value);
		}
		
		// LOGIC
		int out = 0;
		for(List<List<Integer>> v : values) {
			if(matchingOperations(v.get(0), v.get(1), v.get(2))>=3)
			out+=1;
		}
		return out;
	}


	@Override
	protected Integer B(String input) throws Exception {
		// PARSING
		List<List<Integer>> values = new ArrayList<>();
		input = input.replaceAll("B.+\\]\n.+\n.+\\]", "").trim();
		for(String line : input.split("\n")) {
			String[] val = line.split(" ");
			List<Integer> v = new ArrayList<>();
			for(String i : val) v.add(Integer.parseInt(i));
			values.add(v);
 		}
		
		// LOGIC
		List<Integer> register = new ArrayList<>();
		register.add(0);
		register.add(0);
		register.add(0);
		register.add(0);
		for(List<Integer> instruction : values) {
			executeInstruction(instruction, register);
//			System.out.println(instruction + " " + register.toString());
		}
		return register.get(0);
	}
	private void executeInstruction(List<Integer> operation, List<Integer> register) {
		//gtrr
		if(operation.get(0)==13)
			register.set(operation.get(3), (register.get(operation.get(1)) > register.get(operation.get(2)) ? 1:0));
		//borr
		if(operation.get(0)==11)
			register.set(operation.get(3), register.get(operation.get(1)) | register.get(operation.get(2)));
		//gtir
		if(operation.get(0)==10)
			register.set(operation.get(3), (operation.get(1) > register.get(operation.get(2)) ? 1:0));
		//eqri
		if(operation.get(0)==9)
			register.set(operation.get(3), (register.get(operation.get(1)) == operation.get(2) ? 1:0));
		//addr
		if(operation.get(0)==12)
			register.set(operation.get(3), register.get(operation.get(1)) + register.get(operation.get(2)));
		//seti
		if(operation.get(0)==3)
			register.set(operation.get(3), operation.get(1));
		//eqrr
		if(operation.get(0)==4)
			register.set(operation.get(3), (register.get(operation.get(1)) == register.get(operation.get(2)) ? 1:0));
		//gtri
		if(operation.get(0)==8)
			register.set(operation.get(3), (register.get(operation.get(1)) > operation.get(2) ? 1:0));
		//banr
		if(operation.get(0)==15)
			register.set(operation.get(3), register.get(operation.get(1)) & register.get(operation.get(2)));
		//addi
		if(operation.get(0)==2)
			register.set(operation.get(3), register.get(operation.get(1)) + operation.get(2));
		//setr
		if(operation.get(0)==6)
			register.set(operation.get(3), register.get(operation.get(1)));
		//mulr
		if(operation.get(0)==14)
			register.set(operation.get(3), register.get(operation.get(1)) * register.get(operation.get(2)));
		//bori
		if(operation.get(0)==7)
			register.set(operation.get(3), register.get(operation.get(1)) | operation.get(2));
		//muli
		if(operation.get(0)==0)
			register.set(operation.get(3), register.get(operation.get(1)) * operation.get(2));
		//eqir
		if(operation.get(0)==5)
			register.set(operation.get(3), (operation.get(1) == register.get(operation.get(2)) ? 1:0));
		//bani
		if(operation.get(0)==1)
			register.set(operation.get(3), register.get(operation.get(1)) & operation.get(2));
	}
	@Override
	protected void test() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
