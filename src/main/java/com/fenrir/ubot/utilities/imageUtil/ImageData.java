package com.fenrir.ubot.utilities.imageUtil;

public class ImageData {

    private String url;
    private String author;
    private String title;
    private String source;
    private boolean isNFSW;

    public ImageData(String url, String author, String title, String source, boolean isNFSW) {
        this.url = url;
        this.author = author;
        this.title = title;
        this.source = source;
        this.isNFSW = isNFSW;
    }

    public String getAuthor() {
        return author;
    }

    public String getSource() {
        return source;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
