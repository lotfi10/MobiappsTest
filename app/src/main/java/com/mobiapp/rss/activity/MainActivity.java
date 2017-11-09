package com.mobiapp.rss.activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.mobiapp.rss.R;
import com.mobiapp.rss.adapter.NewsAdapter;
import com.mobiapp.rss.model.ArticlesResponse;
import com.mobiapp.rss.model.NewsStore;
import com.mobiapp.rss.networking.NewsRetrofitApi;
import com.mobiapp.rss.sqlitedata.SQLiteHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lotfi Fetteni on 08/11/2017.
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView newsRecyclerView;
    private CoordinatorLayout coordinatorLayout;
    private ProgressBar progressBar;
    SQLiteDatabase sqLiteDatabase;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsRecyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerview);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.activity_main_progressbar);
        if (isNetworkAvailable()) {
            Call<ArticlesResponse> callArticles = NewsRetrofitApi.getApi().getArticles("new-york-magazine", "top");
            callArticles.enqueue(new Callback<ArticlesResponse>() {
                @Override
                public void onResponse(Call<ArticlesResponse> call, Response<ArticlesResponse> response) {

                    progressBar.setVisibility(View.GONE);
                    showNewsApiSnack();
                    ArticlesResponse getArticlesResponse = response.body();
                    NewsStore.setNewsArticles(getArticlesResponse.getArticles());

                    Toast.makeText(MainActivity.this, "Response received", Toast.LENGTH_SHORT).show();
                    NewsAdapter homeNewsAdapter = new NewsAdapter(getArticlesResponse.getArticles());
                    newsRecyclerView.setAdapter(homeNewsAdapter);
                }

                @Override
                public void onFailure(Call<ArticlesResponse> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Error received", Toast.LENGTH_SHORT).show();
                }
            });
        }
        //OffLine Mode
        else if (!(isNetworkAvailable())) {


        }
         }

    private void showNewsApiSnack() {
        Snackbar.make(coordinatorLayout, "Powered by NewsApi.org", Snackbar.LENGTH_LONG)
                .setAction("Visit", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadNewsApiWebsite();
                    }
                }).show();
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
    private void loadNewsApiWebsite() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://newsapi.org")));
    }


    public void SQLiteDataBaseBuild(){

        sqLiteDatabase = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    public void SQLiteTableBuild(){

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+SQLiteHelper.TABLE_NAME+"("+SQLiteHelper.Table_Column_ID+
                " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " "+SQLiteHelper.Table_Column_1_Author+" VARCHAR," +
                " "+SQLiteHelper.Table_Column_2_title+" VARCHAR,"+" " +
                ""+SQLiteHelper.Table_Column_3_description+" VARCHAR," +
                ""+" "+SQLiteHelper.Table_Column_4_URL+" VARCHAR," +
                ""+" "+SQLiteHelper.Table_Column_5_UrlImage+" VARCHAR," +
                ""+" "+SQLiteHelper.Table_Column_6_PublishedAt+" VARCHAR);");

    }

    public void DeletePreviousData(){

        sqLiteDatabase.execSQL("DELETE FROM "+SQLiteHelper.TABLE_NAME+"");

    }
    }

