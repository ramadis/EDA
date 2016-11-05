package flow;

public class MyTimer {

	private static long top_time;
	private static long start_time;
	
	public MyTimer(long top_time) {
		this.top_time = top_time;
		this.start_time = System.currentTimeMillis();
	}
	
	public boolean finished() {
		long current_time = System.currentTimeMillis();
		if (current_time - start_time > top_time)
			return true;
		return false;
	}
	
}
