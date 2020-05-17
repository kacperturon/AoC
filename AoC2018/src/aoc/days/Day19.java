package aoc.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aoc.Day;

public class Day19 extends Day<Integer, Integer> {
	public static void main(String[] args) {
		(new Day19()).run(19);
	}
	
	@Override
	protected Integer A(String input) throws Exception {
		// PARSE
		Map<Integer, Object[]> instructions = new HashMap<>();
		String[] lines = input.split("\n");
		int ipReg = Integer.parseInt(lines[0].split(" ")[1]);
		for(int i=1;i<lines.length;i++) {
			String[] values = lines[i].split(" ");
			String instr = values[0];
			Integer[] instrVals = new Integer[] {Integer.parseInt(values[1]),
					Integer.parseInt(values[2]),Integer.parseInt(values[3])};
			instructions.put(i-1, new Object[] {instr, instrVals});
		}
		
		// LOGIC
		List<Integer> register = new ArrayList<>();
		register.add(0);
		register.add(0);
		register.add(0);
		register.add(0);
		register.add(0);
		register.add(0);
		
		Object[] instr;
		while(register.get(ipReg)<instructions.size()) {
			instr = instructions.get(register.get(ipReg));
			executeInstruction((String) instr[0], Arrays.asList((Integer[]) instr[1]), register);
			register.set(ipReg, (register.get(ipReg)+1));
		}		
		return register.get(0);
	}

	private void executeInstruction(String instruction, List<Integer> instructionValues, List<Integer> register) {
		if(instruction.equals("addr"))
			register.set(instructionValues.get(2), (register.get(instructionValues.get(0)) + register.get(instructionValues.get(1))));
		else if(instruction.equals("addi"))
			register.set(instructionValues.get(2), (register.get(instructionValues.get(0)) + instructionValues.get(1)));
		else if(instruction.equals("mulr"))
			register.set(instructionValues.get(2), (register.get(instructionValues.get(0)) * register.get(instructionValues.get(1))));
		else if(instruction.equals("muli"))
			register.set(instructionValues.get(2), (register.get(instructionValues.get(0)) * instructionValues.get(1)));
		else if(instruction.equals("banr"))
			register.set(instructionValues.get(2), (register.get(instructionValues.get(0)) & register.get(instructionValues.get(1))));
		else if(instruction.equals("bani"))
			register.set(instructionValues.get(2), (register.get(instructionValues.get(0)) & instructionValues.get(1)));
		else if(instruction.equals("borr"))
			register.set(instructionValues.get(2), (register.get(instructionValues.get(0)) | register.get(instructionValues.get(1))));
		else if(instruction.equals("bori"))
			register.set(instructionValues.get(2), (register.get(instructionValues.get(0)) | instructionValues.get(1)));
		else if(instruction.equals("setr"))
			register.set(instructionValues.get(2), register.get(instructionValues.get(0)));
		else if(instruction.equals("seti"))
			register.set(instructionValues.get(2), instructionValues.get(0));
		else if(instruction.equals("gtir"))
			register.set(instructionValues.get(2), (instructionValues.get(0) > register.get(instructionValues.get(1)) ? 1:0));
		else if(instruction.equals("gtri"))
			register.set(instructionValues.get(2), (register.get(instructionValues.get(0)) > instructionValues.get(1) ? 1:0));
		else if(instruction.equals("gtrr"))
			register.set(instructionValues.get(2), (register.get(instructionValues.get(0)) > register.get(instructionValues.get(1)) ? 1:0));
		else if(instruction.equals("eqir"))
			register.set(instructionValues.get(2), (instructionValues.get(0).equals(register.get(instructionValues.get(1))) ? 1:0));
		else if(instruction.equals("eqri"))
			register.set(instructionValues.get(2), (register.get(instructionValues.get(0)) == instructionValues.get(1) ? 1:0));
		else if(instruction.equals("eqrr"))
			register.set(instructionValues.get(2), (register.get(instructionValues.get(0)).equals(register.get(instructionValues.get(1))) ? 1:0));
	}
	
	@Override
	protected Integer B(String input) throws Exception {
		// Calculate the sum of divisors of 10551370
	    int sum = 0; 
	    for (int i=1; i<=Math.sqrt(10551370); i++) 
	    { 
	        if (10551370%i==0) 
	        { 
	            if (i==(10551370/i)) 
	            	sum += i; 
	            else
	            	sum += (i + 10551370/i); 
	        } 
	    } 
	    return sum;
		
	}

	@Override
	protected void test() throws Exception {
		assert(A("#ip 0\n" + 
				"seti 5 0 1\n" + 
				"seti 6 0 2\n" + 
				"addi 0 1 0\n" + 
				"addr 1 2 3\n" + 
				"setr 1 0 0\n" + 
				"seti 8 0 4\n" + 
				"seti 9 0 5")==7);
		
	}

}
