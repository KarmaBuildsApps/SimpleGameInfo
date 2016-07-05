package myapp.tae.ac.uk.simplegameinfo.CacheManager;

import java.util.Comparator;

/**
 * Created by Karma on 05/07/16.
 */
public class FileMetaInfo implements Comparable{
    private String absolutePath;
    private long lastModified;
    private long size;

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return absolutePath+","+lastModified+","+size;
    }

    @Override
    public int compareTo(Object another) {
        long anotherLastModified = ((FileMetaInfo) another).lastModified;
        return lastModified < anotherLastModified?-1:lastModified==anotherLastModified?0:1;
    }
}
