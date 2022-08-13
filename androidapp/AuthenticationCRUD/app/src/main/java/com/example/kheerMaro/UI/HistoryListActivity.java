package com.example.kheerMaro.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kheerMaro.Controller.ApiHold;
import com.example.kheerMaro.Controller.HistoryListAdapter;
import com.example.kheerMaro.Controller.SessionManager;
import com.example.kheerMaro.Model.Plant;
import com.example.kheerMaro.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.LENGTH_LONG;
import static com.example.kheerMaro.SERVER.URLs.ROOT_URL;

public class HistoryListActivity extends AppCompatActivity {
    private RecyclerView recyclerView ;
    private List<Plant> plantList;
    private RecyclerView.Adapter adapter;
    private ApiHold apiHold;
    private String token;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.historique: {
                startActivity(new Intent(getApplicationContext(), HistoryListActivity.class));
            }
            return true;
            case R.id.profile:{
                startActivity(new Intent(getApplicationContext(), ProfilActivity.class));
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.icones_menu,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R
                .layout.activity_history_list);


        if(SessionManager.getInstance(this).getToken() != null){
            //mettre à jour token
            token = SessionManager.getInstance(this).getToken().getToken();
        }
        recyclerView = findViewById(R.id.reyclerHistoryListViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        plantList =  new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiHold = retrofit.create(ApiHold.class);

        String head = "Bearer " + token;
        Call<List<Plant>> call =apiHold.getPlants(head);
        call.enqueue(new Callback<List<Plant>>() {
            @Override
            public void onResponse(Call<List<Plant>> call, Response<List<Plant>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),
                            "Code: " + response.code(), LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getApplicationContext(),
                        "success", LENGTH_LONG).show();
                List<Plant> plantes = response.body();
                for (Plant plante : plantes)
                {
                    plantList.add(plante);
                }

                adapter = new HistoryListAdapter( HistoryListActivity.this,plantList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Plant>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        t.getMessage(), LENGTH_LONG).show();

            }
        });
    }
}
