package com.truedreamz.modal;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JAYAPRAKASH-FGTS on 15-03-2018.
 */

public class Post {

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("body")
    private String body;

    public Post(String id,String title,String body){
        this.id=id;
        this.title=title;
        this.body=body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
