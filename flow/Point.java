package flow;

import flow.Point;

public class Point {
	
	int fil;
	int col;
	int value;
	boolean is_node;
	boolean is_end_node;
	
	int direction_fil;
	int direction_col;
	
	public Point(int fil, int col, int value, boolean is_node, boolean is_end_node, int dir_fil, int dir_col) {
		this.fil = fil;
		this.col = col;
		this.value = value;
		this.is_node = is_node;
		this.is_end_node = is_end_node;
		this.direction_fil = dir_fil;
		this.direction_col = dir_col;
	}
	
	public Point(int fil, int col, int value, boolean is_node) {
		this(fil, col, value, is_node, false, 0, 0);
	}
	
	public Point(int fil, int col) {
		this(fil, col, 0, false);
	}
	
	public Point(Point other) {
		this(other.fil, other.col, other.value, other.is_node, other.is_end_node, other.direction_fil, other.direction_col);
	}
	
	public boolean equals(Point other) {
		if (fil == other.fil && col == other.col)
			return true;
		return false;
	}
	
	public void setDirection(int dir_fil, int dir_col) {
		direction_fil = dir_fil;
		direction_col = dir_col;
	}
	
	public void clearDirection() {
		direction_fil = 0;
		direction_col = 0;
	}
	
}
