package aoc.days;

import aoc.Day;

public class Day9 extends Day<Integer, Long>{
	public static void main(String[] args) {
		(new Day9()).run(9);
	}

	public class Marble{
		Marble next;
		Marble prev;
		int value;
		public Marble(int value) {
			this.value = value;
		}
		public Marble(Marble prev, Marble next, int value) {
			this.next = next;
			this.prev = prev;
			this.value = value;
		}
	}
	@Override
	protected Integer A(String input) throws Exception {
		String[] split = input.split(" ");
		int players = Integer.parseInt(split[0]);
		int lastMarblePts = Integer.parseInt(split[split.length-2]);
		
		Marble currMarble = new Marble(0);
		currMarble.next = currMarble;
		currMarble.prev = currMarble;
		int[] scores = new int[players];
		
		int player = 0;
		int currVal = 0;
		while(currMarble.value<lastMarblePts) {
			currVal++;
			if(player>players-1) player=0;
			if(currVal % 23 ==0) {
				scores[player] += currVal;
				for(int i=0;i<7;i++) {
					currMarble = currMarble.prev;
				}
				scores[player] += currMarble.value;
				currMarble.prev.next = currMarble.next; 
				currMarble.next.prev = currMarble.prev;
				currMarble = currMarble.next;
			}else {
				Marble nextMarble = new Marble(currMarble.next, currMarble.next.next, currVal);
				currMarble.next.next = nextMarble;
				currMarble.next.next.next.prev = nextMarble;
				currMarble = nextMarble;
			}
			player++;
		}
		
		int maxScore = Integer.MIN_VALUE;
		for(int i : scores) {
			if(maxScore<i)
				maxScore = i;
		}
		
		return maxScore;
	}

	@Override
	protected Long B(String input) throws Exception {
		String[] split = input.split(" ");
		int players = Integer.parseInt(split[0]);
		int lastMarblePts = Integer.parseInt(split[split.length-2])*100;
		
		Marble currMarble = new Marble(0);
		currMarble.next = currMarble;
		currMarble.prev = currMarble;
		long[] scores = new long[players];
		
		int player = 0;
		int currVal = 0;
		while(currMarble.value<lastMarblePts) {
			currVal++;
			if(player>players-1) player=0;
			if(currVal % 23 ==0) {
				scores[player] += currVal;
				for(int i=0;i<7;i++) {
					currMarble = currMarble.prev;
				}
				scores[player] += currMarble.value;
				currMarble.prev.next = currMarble.next; 
				currMarble.next.prev = currMarble.prev;
				currMarble = currMarble.next;
			}else {
				Marble nextMarble = new Marble(currMarble.next, currMarble.next.next, currVal);
				currMarble.next.next = nextMarble;
				currMarble.next.next.next.prev = nextMarble;
				currMarble = nextMarble;
			}
			player++;
		}
		
		long maxScore = Integer.MIN_VALUE;
		for(long i : scores) {
			if(maxScore<i)
				maxScore = i;
		}
		
		return maxScore;
	}

	@Override
	protected void test() throws Exception {
	    assert(A("9 players; last marble is worth 25 points")==32);
	    assert(A("10 players; last marble is worth 1618 points")==8317);
	    assert(A("13 players; last marble is worth 7999 points")==146373);
	    assert(A("17 players; last marble is worth 1104 points")==2764);
	    assert(A("21 players; last marble is worth 6111 points")==54718);
	    assert(A("30 players; last marble is worth 5807 points")==37305);
	}
	

}
