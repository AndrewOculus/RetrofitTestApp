package com.noname.mynewretrofitapp;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private String BASE_URL = "https://api.github.com" ;
    private TextView tv1;
    private ImageView imageView;
    private MainActivity main;
    private List<GitApiInterface> posts;
    GitApiInterface service;
    RecyclerView recyclerView;
    RVAdapter adapter;
    Callback<GithubUser> callb;

    private List<Person> persons;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main = this;

        setContentView(R.layout.recyclerview_activity);

        rv=(RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();

        //tv1 = (TextView) findViewById(R.id.tv1);
        //imageView = (ImageView)  findViewById(R.id.image);



        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        service = client.create(GitApiInterface.class);
        Call<GithubUser> call = service.getUser("AndrewOculus");


        callb = new Callback<GithubUser>() {

            @Override
            public void onResponse(Call<GithubUser> call, Response<GithubUser> response) {

                persons.add(new Person(response.body().getName().toString(), response.body().getFollowers()+" followers",response.body().getAvatarUrl() ));//response.body().getAvatarUrl()
                rv.setAdapter(adapter);

                //Toast.makeText(MainActivity.this, response.body().getLogin().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<GithubUser> call, Throwable t) {

            }
        };
        call.enqueue(callb);

        call = service.getUser("tutsplus");
        call.enqueue(callb);

        call = service.getUser("uber-node");
        call.enqueue(callb);

        call = service.getUser("Raynos");
        call.enqueue(callb);

    }

    private void initializeData(){
        persons = new ArrayList<>();
        adapter = new RVAdapter(persons , main);
        rv.setAdapter(adapter);

    }

}
