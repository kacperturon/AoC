package aoc.days;

import java.util.ArrayList;
import java.util.List;

import aoc.Day;

public class Day8 extends Day<Integer, Integer>{
	public static void main(String[] args) {
		(new Day8()).run(8);
	}
	
	class Node{
		int childrenCount;
		int metadataCount;
		List<Node> children = new ArrayList<>();
		List<Integer> metadata = new ArrayList<>();
		public Node(int childrenCount, int metadataCount) {
			this.childrenCount = childrenCount;
			this.metadataCount = metadataCount;
		}
	}
	
	private int getChildren(Node node, int pointer, int[] values) {
		int cCount = node.childrenCount;
		if(cCount==0) {			
			for(int i=0;i<node.metadataCount; i++) {
				pointer++;
				node.metadata.add(values[pointer]);
			}
			return pointer;
		}else {
			while(cCount>0) {
				pointer++;
				Node child = new Node(values[pointer], values[pointer+1]);
				pointer++;
				pointer = getChildren(child, pointer, values);
				node.children.add(child);
				cCount--;
				if(cCount==0)
					for(int i=0; i<node.metadataCount; i++) {
						pointer++;
						node.metadata.add(values[pointer]);
					}
				}
			}
		return pointer;
	}
	
	private int sumMetadataA(Node node) {
		int index = 0;
		int metadataSum = 0;
		for(int i=0; i<node.metadataCount; i++)
			metadataSum += node.metadata.get(i);
		if(node.childrenCount == 0) return metadataSum;
		while(index<node.childrenCount) {
			metadataSum+=sumMetadataA(node.children.get(index));
			index++;
		}
		return metadataSum; 
	}
	
	private int sumMetadataB(Node node) {
		int metadataSum = 0;
		if(node.childrenCount==0)
		for(int i=0; i<node.metadataCount; i++)
			metadataSum += node.metadata.get(i);
		if(node.childrenCount == 0) return metadataSum;
		for(int m : node.metadata) {
			Node c;
			if(m-1<0||m-1>=node.childrenCount) c = null;
			else c = node.children.get(m-1);
			if (c!=null)
				metadataSum+=sumMetadataB(c);
		}
		return metadataSum; 
	}
	
	@Override
	protected Integer A(String input) throws Exception {
		String[] valuesStr = input.split(" ");
		int[] values = new int[valuesStr.length];
		for(int i=0; i<valuesStr.length; i++) 
			values[i] = Integer.parseInt(valuesStr[i]);
		int pointer = 0;
		Node root = new Node(values[pointer], values[pointer+1]);
		pointer++;
		getChildren(root, 1, values);
		return sumMetadataA(root);
	}

	@Override
	protected Integer B(String input) throws Exception {
		String[] valuesStr = input.split(" ");
		int[] values = new int[valuesStr.length];
		for(int i=0; i<valuesStr.length; i++) 
			values[i] = Integer.parseInt(valuesStr[i]);
		int pointer = 0;
		Node root = new Node(values[pointer], values[pointer+1]);
		pointer++;
		getChildren(root, 1, values);
		return sumMetadataB(root);
	}

	@Override
	protected void test() throws Exception {
		assert(A("2 1 1 1 0 3 8 9 5 8 2 1 1 1 0 1 7 9 0 1 2 3 4")== 55);
		assert(B("2 1 1 1 0 3 8 9 5 8 2 1 1 1 0 1 7 9 0 1 2 3 0")== 0);
		assert(B("2 1 0 1 3 3 2 0 1 5 0 1 3 1 1 0 1 7 1 1 3 2")== 12);

	}

}
