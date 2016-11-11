package flow;

public class MyTimer {

	private static long TOP_TIME;
	private static long START_TIME;
	
	public MyTimer(long top_time) {
		TOP_TIME = top_time;
		START_TIME = System.currentTimeMillis();
	}
	
	public boolean finished() {
		long current_time = System.currentTimeMillis();
		if (current_time - START_TIME > TOP_TIME)
			return true;
		return false;
	}
	
	public void stallProgress() {
		long current_time = System.currentTimeMillis();
		long end_time = System.currentTimeMillis();
		
		while (end_time - current_time < 100) {
			end_time = System.currentTimeMillis();
		}
		return;
	}
	
}
