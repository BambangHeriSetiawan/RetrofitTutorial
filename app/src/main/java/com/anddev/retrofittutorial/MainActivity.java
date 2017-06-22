package com.anddev.retrofittutorial;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.anddev.retrofittutorial.adapter.AnswersAdapter;
import com.anddev.retrofittutorial.apis.SOService;
import com.anddev.retrofittutorial.apis.ApisUntil;
import com.anddev.retrofittutorial.modeling.Item;
import com.anddev.retrofittutorial.modeling.SOAnswersResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "From Service Retrofit";
    private SOService mApisService;
    private RecyclerView rcvAnswer;
    private AnswersAdapter answersAdapter;
    private RecyclerView.LayoutManager llm;
    private RecyclerView.ItemDecoration itemDecoration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApisService = ApisUntil.getSOService();
        rcvAnswer = (RecyclerView) findViewById(R.id.rcvAnswer);
        answersAdapter = new AnswersAdapter(this,new ArrayList<Item>(0), new AnswersAdapter.PostItemListener() {
            @Override
            public void onPostClick(long id) {
                Toast.makeText(MainActivity.this, "Post id is" + id, Toast.LENGTH_SHORT).show();
            }
        });
        llm = new LinearLayoutManager(this);
        itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcvAnswer.setHasFixedSize(true);
        rcvAnswer.setLayoutManager(llm);
        rcvAnswer.addItemDecoration(itemDecoration);
        rcvAnswer.setAdapter(answersAdapter);
        loadAnswers();
    }

    private void loadAnswers() {

        mApisService.getAnswers().enqueue(new Callback<SOAnswersResponse>() {
            @Override
            public void onResponse(Call<SOAnswersResponse> call, Response<SOAnswersResponse> response) {
                if (response != null){
                    //Log.e(TAG,""+response.body().getQuotaRemaining());
                    answersAdapter.updateAnswers(response.body().getItems());
                }else {
                    Log.e(TAG,""+response.code());
                }
            }

            @Override
            public void onFailure(Call<SOAnswersResponse> call, Throwable t) {
                Log.e(TAG,""+t.getMessage());
            }
        });
    }
}
