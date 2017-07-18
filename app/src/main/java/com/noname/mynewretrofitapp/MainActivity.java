package com.noname.mynewretrofitapp;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.tv1);
        imageView = (ImageView)  findViewById(R.id.image);
        main = this;

        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        GitApiInterface service = client.create(GitApiInterface.class);
        Call<GithubUser> call = service.getUser("AndrewOculus");

        call.enqueue(new Callback<GithubUser>() {

            @Override
            public void onResponse(Call<GithubUser> call, Response<GithubUser> response) {


                addString(response.body().getId().toString());
                if(response.body().getBio() != null)
                    addString(response.body().getBio().toString());
                if(response.body().getBlog() != null)
                    addString(response.body().getBlog().toString());
                if(response.body().getCompany() != null)
                    addString(response.body().getCompany().toString());
                if(response.body().getAvatarUrl() != null)
                    addString(response.body().getAvatarUrl().toString());

                if(response.body().getAvatarUrl() != null)
                Picasso.with(main) //передаем контекст приложения
                        .load(response.body().getAvatarUrl())
                        .into(imageView); //ссылка на ImageView

                Toast.makeText(MainActivity.this, response.body().getLogin().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<GithubUser> call, Throwable t) {

            }
        });

    }

    public void addString(String str)
    {
        tv1.setText(tv1.getText()+"\n"+str);
    }

}
