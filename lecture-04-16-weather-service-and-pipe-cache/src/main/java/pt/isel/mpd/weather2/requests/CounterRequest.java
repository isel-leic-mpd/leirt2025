package pt.isel.mpd.weather2.requests;


import java.io.Reader;

public class CounterRequest implements Request {
//
//	@Override
//	public Reader get(String path) {
//		return null;
//	}
	
	private int count;
	private Request req;
	public CounterRequest(Request req) {
		// TO IMPLEMENT
		this.req = req;
	}
	
	@Override
	public Reader get(String path) {
		// TO IMPLEMENT
		count++;
		return req.get(path);
	}
	
	public int getCount() {
		// TO IMPLEMENT
		return count;
		
	}
}

