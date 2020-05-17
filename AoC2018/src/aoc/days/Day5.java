package aoc.days;
import java.util.ArrayDeque;
import java.util.Deque;
import aoc.Day;

public class Day5 extends Day<Integer, Integer> {
	public static void main(String[] args) {
		(new Day5()).run(5);
	}
	
	@Override
	protected Integer A(String input) {
		Deque<Character> stack = new ArrayDeque<>();
		for(int i=0; i<input.length(); i++) {
			char c = input.charAt(i);
			if(stack.isEmpty()) stack.push(c);
			else {
				if(stack.peek() == c+32 || stack.peek() == c-32) {
					stack.pop();
					continue;
				}else stack.push(c);
			}
		}
		return stack.size();
	}

	@Override
	protected Integer B(String input) {
		int min = Integer.MAX_VALUE;
		for(int i='a'; i <= 'z'; i++) {
			String copy = input;
			copy = copy.replaceAll("["+(char)i+(char)(i-32)+"]", "");
			int val = A(copy);
			if(val<min) min = val; 
		}
		return min;
	}

	@Override
	protected void test() {
		// TODO Auto-generated method stub
		
	}
}
