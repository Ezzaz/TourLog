package com.tour_log.tourlog.gallery;

public class GalleryImg {

    public String name;
    public String url;

    public String getName () {
        return name;
    }

    public String getUrl () {
        return url;
    }

    public GalleryImg ( String name, String url ) {
        this.name = name;
        this.url = url;
    }

    public GalleryImg() {
    }


}
