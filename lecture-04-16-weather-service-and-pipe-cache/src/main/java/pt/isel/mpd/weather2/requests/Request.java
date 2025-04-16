package pt.isel.mpd.weather2.requests;

import java.io.Reader;

public interface Request {
	Reader get(String path);
}
