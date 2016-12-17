package jp.smaphonia.myretrofitsample.data;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by unokun on 2016/12/17.
 */

public class Bookmark {
    private static final String TAG = Bookmark.class.getSimpleName();

    @Expose
    private String comment;

    @Expose
    private List<Object> tags = new ArrayList<>();
    @Expose
    private String timestamp;
    @Expose
    private String user;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Object> getTags() {
        return tags;
    }

    public void setTags(List<Object> tags) {
        this.tags = tags;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
