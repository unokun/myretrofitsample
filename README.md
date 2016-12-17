# myretrofitsample
## 概要
[Android:Retrofit2.0ではてなAPIとおしゃべりしてみた - techium](http://blog.techium.jp/entry/2016/04/10/090000)を参考にしてRetrofit + Gsonを試してみました。

この記事では、はてなブックマークエントリー情報取得APIを使って、ブックマーク一覧を取得しています。
[http://developer.hatena.ne.jp/ja/documents/bookmark/apis/getinfo](http://developer.hatena.ne.jp/ja/documents/bookmark/apis/getinfo)

はてなブックマークエントリー情報取得APIは、以下のURLでリクエストを送信するとjson形式で結果を取得することができます。
```
http://b.hatena.ne.jp/entry/json/?url=http%3A%2F%2Fb.hatena.ne.jp%2Fctop%2Fit
```
結果(一部)
```
{
    "related": [
        {
            "count": 1502,
            "url": "http://scrambleworks.net/windows-freesoft-recommend",
            "eid": 234774846,
            "title": "Windowsを16年使ってきて分かったおすすめ無料ソフトまとめ | Scramble Works",
            "entry_url": "http://b.hatena.ne.jp/entry/scrambleworks.net/windows-freesoft-recommend"
        },
        ...
    ],
    "count": 27,
    "bookmarks": [
        {
            "comment":"",
            "timestamp":"2016/01/04 17:38:03",
            "user":"kamiokando",
            "tags":[]
        },
        ...
    ]
}
```

## retrofit
retrofitは、HTTP通信レスポンスを、javaインスタンスにデータ変換するライブラリ。
HTTP通信はOkHttpを使っています。
データ変換は、複数の変換ライブラリから選択できる。
* gson(json:google)
* jackson(json:fasterxml)
* moshi(json:squareup)
* scalars(?)
* simplexml(xml:squareup)
* wire(?)

## 実装手順
retrofitを使ったAndroidアプリは以下の手順で実装できます。
### ライブラリを追加(build.gradle(Module:app))
それぞれのライブラリは、2016/12時点の最新バージョンです。
```
    compile 'com.squareup.retrofit2:retrofit:2.1.0'
    compile 'com.squareup.retrofit2:converter-gson:2.1.0'
    compile 'com.google.code.gson:gson:2.8.0'
```

### AndroidManifest.xml
インターネットアクセス用のパーミッションを追加する。
```
    <uses-permission android:name="android.permission.INTERNET"/>
```

### データクラス(Bookmark/BookmarkContainer)を作成する
jsonデータを参照して、javaインスタンス化したいクラスを作成します。

* BookmarkContainer
|name(json)|型(java)|プロパティ名|
|:---|:---|:---|
|related|無視||
|count|Integer|count|
|title|String|title|
|bookmarks|List<Bookmark>|bookmarks|

* Bookmark
|name(json)|型(java)|プロパティ名|
|:---|:---|:---|
|comment|String comment|comment|
|timestamp|String|timestamp|
|user|String|user|

#### APIインタフェースを作成する
GETの後ろに書くパスはベースURLからの相対パス。
@Query("url")が?url=に変換されます。

HatenaApiInterface.java
```
    String END_POINT = "http://b.hatena.ne.jp";
    String TARGET_URL = "http://b.hatena.ne.jp/ctop/it";

    @GET("/entry/jsonlite/")
    Call<BookmarkContainer> getBookmarksWithUrl(@Query("url") String target);
```

#### retrofitを実行
MainActivity.java

```java
    protected void onCreate(Bundle savedInstanceState) {

        // 結果表示用ListView
        mListView = (ListView) findViewById(R.id.listView);

        Retrofit retrofit = new Retrofit.Builder()
                // BASE URL
                .baseUrl(HatenaApiInterface.END_POINT)
                // gsonコンバーター
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // インタフェース作成
        mApiInterface = retrofit.create(HatenaApiInterface.class);
        execGetBookmarksWithUrl(HatenaApiInterface.TARGET_URL);
    }
    private void execGetBookmarksWithUrl(String targetUrl) {
        // Hatena API呼び出し
        Call<BookmarkContainer> call = mApiInterface.getBookmarksWithUrl(targetUrl);
        call.enqueue(new Callback<BookmarkContainer>() {
            // onResponseとonFailureをオーバーライド
            public void onResponse(Call<BookmarkContainer> call, Response<BookmarkContainer> response) {
                // HTTPレスポンスをJavaクラスで取得
                BookmarkContainer container = response.body();
                // ...
            }
        ｝

    }
```

## retrofit参考資料
* [retrofit(GitHub)](https://github.com/square/retrofit)
* [Android:Retrofit2.0ではてなAPIとおしゃべりしてみた - techium](http://blog.techium.jp/entry/2016/04/10/090000)
* [Retrofit + Gson で、 API にアクセス & JSON のパージング処理を簡潔に実装する - Qiita](http://qiita.com/Hachimori/items/c7349ec068924b7ce045)
* [Y.A.M の 雑記帳: Migrate from Retrofit to Retrofit2 （Retrofit から Retrofit    2 に移行する）](http://y-anz-m.blogspot.jp/2016/03/migrate-from-retrofit-to-retrofit2.html)
* [google-gson(Github)](https://github.com/google/gson)
* [Retrofit2.0に備えてKotlinで始めるMoshi(JSONパーサ)](http://qiita.com/droibit/items/73fe3d0ec7b11fa982ea)



