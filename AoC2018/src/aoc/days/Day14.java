package aoc.days;

import aoc.Day;

public class Day14 extends Day<String, Integer> {
	public static void main(String[] args) {
		(new Day14()).run(14);
	}
	
	public class Node<T>{
		T data;
		Node<T> next;
		Node<T> prev;
		public Node(T data) {
			this.data = data;
		}
		public Node(T data, Node<T> next, Node<T> prev) {
			this.data = data;
			this.next = next;
			this.prev = prev;
		}
	}
	
	Node<Integer> head;
	Node<Integer> tail;
	int size=0;
	
	private void addNode(Node<Integer> node) {
		node.prev = tail;
		if(head==null) head=tail=node;
		else {
			tail.next = node;
			tail = tail.next;
			tail.next = head;
		}
		head.prev = tail;
		size++;
	}
	
	private Node<Integer> nextElfNode(Node<Integer> node) {
		int nodeVal = node.data;
		for(int i=0; i<(nodeVal+1); i++, node=node.next) {}
		return node;
	}
	
	private void clear() {
		size=0;
		head=null;
		tail=null;
	}
	
	@Override
	protected String A(String input) throws Exception {
		clear();
		int recipes = Integer.parseInt(input);
		Node<Integer> elf1 = new Node<>(3);
		Node<Integer> elf2 = new Node<>(7);
		addNode(elf1);
		addNode(elf2);
		
		while(true) {
//			printLine();
			if(size>recipes+10) break;
			int sum = elf1.data+elf2.data;
			if(sum>9) {
				addNode(new Node<>(sum/10));
				addNode(new Node<>(sum%10));
			}else addNode(new Node<>(sum));
			elf1 = nextElfNode(elf1);
			elf2 = nextElfNode(elf2);
		}
//		printLine();
		Node<Integer> node = tail;
		if(size-recipes!=10) {//backtrack
			for(int i=0; i<(size-recipes-1); i++)
				node = node.prev;
		}
		String out = "";
		for(int i=0; i<10;i++) {
			out+= node.data;
			node = node.next;
		}
		return out;
		
	}
	
	private String line() {
		String out = "";
		Node<Integer> node = head;
		while(node!=null) {
			out+=node.data+" ";
			if(node.equals(tail)) break;
			node = node.next;
		}
		return out;
	}
	
	private void printLine() {
		System.out.println(line());
	}

	@Override
	protected Integer B(String input) throws Exception {
		clear();
		Node<Integer> elf1 = new Node<>(3);
		Node<Integer> elf2 = new Node<>(7);
		addNode(elf1);
		addNode(elf2);
		
		StringBuilder val = new StringBuilder("37");
		int i = 2;
		while(true) {
			if(val.length() == input.length() && val.toString().equals(input)) return i-input.length();
			int sum = elf1.data+elf2.data;
			if(sum>9) {
				i++;
				addNode(new Node<>(sum/10));
				addNode(new Node<>(sum%10));
				val.append(sum/10);
				if(val.length()>input.length()) val.deleteCharAt(0);
				if(val.length() == input.length() && val.toString().equals(input)) return i-input.length();
				val.append(sum%10);
				if(val.length()>input.length()) val.deleteCharAt(0);
			}else{
				 val.append(sum);
				 addNode(new Node<>(sum));
			};
			if(val.length()>input.length()) val.deleteCharAt(0);
			elf1 = nextElfNode(elf1);
			elf2 = nextElfNode(elf2);
			i++;
		}
	}

	@Override
	protected void test() throws Exception {
		assert(A("9").equals("5158916779"));
	    assert(A("5").equals("0124515891"));
	    assert(A("18").equals("9251071085"));
	    assert(A("2018").equals("5941429882"));
	    assert(B("51589")==9);
	    assert(B("01245")==5);
	    assert(B("92510")==18);
	    assert(B("851")==26);
	    assert(B("59414")==2018);

	}
}
