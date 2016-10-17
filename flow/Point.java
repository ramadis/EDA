package flow;

public class Point {
	
	int fil;
	int col;
	
	public Point(int fil, int col) {
		this.fil = fil;
		this.col = col;
	}
	
	public boolean equals(Point other) {
		if (fil == other.fil && col == other.col)
			return true;
		return false;
	}
	
}
