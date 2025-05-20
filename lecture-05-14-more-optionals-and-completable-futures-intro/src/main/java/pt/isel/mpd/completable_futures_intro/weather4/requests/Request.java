package pt.isel.mpd.completable_futures_intro.weather4.requests;

import java.io.Reader;

public interface Request {
	Reader get(String path);
}
