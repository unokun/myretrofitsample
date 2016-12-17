# myretrofitsample
## サンプルコード
[Android:Retrofit2.0ではてなAPIとおしゃべりしてみた - techium](http://blog.techium.jp/entry/2016/04/10/090000)を参考にしてRetrofit + Gsonのサンプルを作成しました。

はてなブックマークエントリー情報取得APIを使って、ブックマーク一覧を取得する。
[http://developer.hatena.ne.jp/ja/documents/bookmark/apis/getinfo](http://developer.hatena.ne.jp/ja/documents/bookmark/apis/getinfo)

### 手順
#### ライブラリを追加(build.gradle(Module:app))
```
    compile 'com.squareup.retrofit2:retrofit:2.1.0'
    compile 'com.squareup.retrofit2:converter-gson:2.1.0'
    compile 'com.google.code.gson:gson:2.8.0'
```
#### AndroidManifest.xml
```
    <uses-permission android:name="android.permission.INTERNET"/>
```

#### データクラス(Bookmark/BookmarkContainer)を作成する
* BookmarkContainer
* Bookmark

#### APIインタフェースを作成する
HatenaApiInterface
```
@GET("/entry/jsonlite/")
    Call<BookmarkContainer> getBookmarksWithUrl(@Query("url") String target);
```

#### APIを実行する
MainActivity.java

```java
    onCreate() {

        // 結果表示用
        mListView = (ListView) findViewById(R.id.listView);

        Retrofit retrofit = new Retrofit.Builder()
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

            }
        ｝

    }
```
## retrofit
* [retrofit(GitHub)](https://github.com/square/retrofit)
* [Android:Retrofit2.0ではてなAPIとおしゃべりしてみた - techium](http://blog.techium.jp/entry/2016/04/10/090000)
* [Retrofit + Gson で、 API にアクセス & JSON のパージング処理を簡潔に実装する - Qiita](http://qiita.com/Hachimori/items/c7349ec068924b7ce045)
* [Y.A.M の 雑記帳: Migrate from Retrofit to Retrofit2 （Retrofit から Retrofit    2 に移行する）](http://y-anz-m.blogspot.jp/2016/03/migrate-from-retrofit-to-retrofit2.html)


## gson
gsonコンバータは、jsonデータをJavaクラスにマッピングしてくれる。

* [google-gson(Github)](https://github.com/google/gson)


