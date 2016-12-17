package jp.smaphonia.myretrofitsample.data;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by unokun on 2016/12/17.
 */

public class BookmarkContainer {
    private static final String TAG = BookmarkContainer.class.getSimpleName();

    @Expose
    private List<Bookmark> bookmarks = new ArrayList<Bookmark>();
    @Expose
    private Integer count;
    @Expose
    private Integer eid;
    @Expose
    private String entryUrl;
    @Expose
    private String screenshot;
    @Expose
    private String title;
    @Expose
    private String url;

    public List<Bookmark> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getEntryUrl() {
        return entryUrl;
    }

    public void setEntryUrl(String entryUrl) {
        this.entryUrl = entryUrl;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
