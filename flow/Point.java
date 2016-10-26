package flow;

import flow.Point;

public class Point {
	
	int fil;
	int col;
	int value;
	boolean is_node;
	boolean is_end_node;
	
	public Point(int fil, int col, int value, boolean is_node, boolean is_end_node) {
		this.fil = fil;
		this.col = col;
		this.value = value;
		this.is_node = is_node;
		this.is_end_node = is_end_node;
	}
	
	public Point(int fil, int col, int value, boolean is_node) {
		this(fil, col, value, is_node, false);
	}
	
	public Point(int fil, int col) {
		this(fil, col, 0, false);
	}
	
	public Point(Point other) {
		this(other.fil, other.col, other.value, other.is_node, other.is_end_node);
	}
	
	public boolean equals(Point other) {
		if (fil == other.fil && col == other.col)
			return true;
		return false;
	}
	
}
