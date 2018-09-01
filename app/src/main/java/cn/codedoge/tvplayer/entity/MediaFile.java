package cn.codedoge.tvplayer.entity;

import android.graphics.Bitmap;

/**
 * Created by codedoge on 2018/1/4.
 */

public class MediaFile {

    private int id;
    private String title;
    private String path;
    private long duration;
    private long size;
    private String resolution;
    private Bitmap preview;

    public MediaFile() {
    }

    public MediaFile(int id, String title, String path, long duration, long size, String resolution, Bitmap preview) {
        this.id = id;
        this.title = title;
        this.path = path;
        this.duration = duration;
        this.size = size;
        this.resolution = resolution;
        this.preview = preview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDuration() {
        int d = (int) duration / 1000;
        int h = 0;
        int m = 0;
        int s = d % 60;
        String result;
        if (d >= 60) {
            d = d / 60;
            if (d >= 60) {
                m = d % 60;
                h = d / 60;
                result = h + "h" + m + "m" + s + "s";
            } else {
                result = d + "m" + s + "s";
            }
        } else {
            result = s + "s";
        }
        return result;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getSize() {
        String result = "KB";
        double s = (double) size / 1024;
        if (s > 1024.00) {
            s = s / 1024.00;
            result = "MB";
        }
        if (s > 1024.00) {
            s = s / 1024.00;
            result = "GB";
        }


        return String.format("%.2f", s) + result;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public Bitmap getPreview() {
        return preview;
    }

    public void setPreview(Bitmap preview) {
        this.preview = preview;
    }

    @Override
    public String toString() {
        return "MediaFile{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", duration=" + duration +
                ", size=" + size +
                ", resolution='" + resolution + '\'' +
                ", preview=" + preview +
                '}';
    }
}
