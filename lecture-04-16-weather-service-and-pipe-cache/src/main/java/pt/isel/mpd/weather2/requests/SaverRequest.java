package pt.isel.mpd.weather2.requests;

import pt.isel.mpd.weather2.resources.ResourceUtils;

import java.io.*;

public class SaverRequest implements Request {
    
    private final Request baseRequest;
    
    public SaverRequest(Request baseRequest) {
        this.baseRequest = baseRequest;
    }
    @Override
    public Reader get(String path) {
        Reader reader = baseRequest.get(path);
        ResourceUtils.saveOnCache(path, reader);
        return ResourceUtils.getFromCache(path);
    }
}