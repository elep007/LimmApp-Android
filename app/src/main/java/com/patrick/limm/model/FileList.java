package com.patrick.limm.model;

public class FileList {
    String type, name, url;

    public FileList(String type, String name, String url){
        this.type=type;
        this.name=name;
        this.url=url;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }
}
