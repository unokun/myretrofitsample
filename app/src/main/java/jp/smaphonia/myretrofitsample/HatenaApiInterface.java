package jp.smaphonia.myretrofitsample;

import jp.smaphonia.myretrofitsample.data.BookmarkEntry;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by unokun on 2016/12/17.
 */

public interface HatenaApiInterface {
    String END_POINT = "http://b.hatena.ne.jp";
    String TARGET_URL = "http://b.hatena.ne.jp/ctop/it";

    // はてなブックマークエントリー情報取得API
    // http://developer.hatena.ne.jp/ja/documents/bookmark/apis/getinfo
    @GET("/entry/jsonlite/")
    Call<BookmarkEntry> getBookmarkEntry(@Query("url") String target);

}
