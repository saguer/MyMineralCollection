package mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass;

/**
 * Created by Santiago on 11/28/2016.
 */
public class PathObject {

    private String path;
    private String thumbPath;
    private boolean orig;
    private boolean mod;
    private long timeStamp;


    public PathObject(String _path, String _thumbPath, boolean _orig, boolean _mod, long _timeStamp) {
        this.path = _path;
        this.thumbPath = _thumbPath;
        this.orig = _orig;
        this.mod = _mod;
        this.timeStamp = _timeStamp;
    }

    public String getPath() {
        return path;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public boolean isOrig() {
        return orig;
    }

    public boolean isMod() {
        return mod;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
