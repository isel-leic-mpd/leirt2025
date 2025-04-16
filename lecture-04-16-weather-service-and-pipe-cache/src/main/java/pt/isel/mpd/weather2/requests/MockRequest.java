package pt.isel.mpd.weather2.requests;

import pt.isel.mpd.weather2.resources.ResourceUtils;

import java.io.Reader;

public class MockRequest implements Request{
	
	@Override
	public Reader get(String path) {
		return ResourceUtils.getFromCache(path);
	}
	
}
