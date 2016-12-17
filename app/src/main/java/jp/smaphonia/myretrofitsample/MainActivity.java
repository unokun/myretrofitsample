package jp.smaphonia.myretrofitsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import jp.smaphonia.myretrofitsample.data.Bookmark;
import jp.smaphonia.myretrofitsample.data.BookmarkEntry;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private HatenaApiInterface mApiInterface;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // ここから追加
        mListView = (ListView) findViewById(R.id.listView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HatenaApiInterface.END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mApiInterface = retrofit.create(HatenaApiInterface.class);
        getBookmarkEntry(HatenaApiInterface.TARGET_URL);
    }

    private void getBookmarkEntry(String targetUrl) {
        Log.d(TAG, "getBookmarkEntry: targetUrl = " + targetUrl);

        // Hatena API呼び出し
        Call<BookmarkEntry> call = mApiInterface.getBookmarkEntry(targetUrl);
        call.enqueue(new Callback<BookmarkEntry>() {
            @Override
            public void onResponse(Call<BookmarkEntry> call, Response<BookmarkEntry> response) {
                Log.d(TAG, "onResponse: " + response.isSuccessful());
                BookmarkEntry entry = response.body();
                List<Bookmark> bookmarks = entry.getBookmarks();

                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1);
                for (Bookmark b : bookmarks) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(b.getUser() + "(" + b.getTimestamp() + ")");
                    String comment = b.getComment();
                    if (comment.length() > 0) {
                        builder.append("\n" + comment);
                    }

                    adapter.add(builder.toString());
                }
                mListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<BookmarkEntry> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getCause() + ", " + t.getMessage());
                t.printStackTrace();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
