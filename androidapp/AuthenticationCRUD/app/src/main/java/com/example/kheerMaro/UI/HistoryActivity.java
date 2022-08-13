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
import com.example.kheerMaro.Controller.HistoryAdapter;
import com.example.kheerMaro.Model.Command;
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

public class HistoryActivity extends AppCompatActivity {
    private Bundle extras;
    private ApiHold apiHold;
    private List<Command> commandList;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView ;
    private static final Integer NUMBER_OF_OPERATIONS = 20 ;
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
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.reyclerHistoryViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        commandList =  new ArrayList<>();
        extras = getIntent().getExtras();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL )
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiHold = retrofit.create(ApiHold.class);
        Call<List<Command>> call = apiHold.getCommands(extras.getString("id")
                ,NUMBER_OF_OPERATIONS);
        call.enqueue(new Callback<List<Command>>() {
            @Override
            public void onResponse(Call<List<Command>> call, Response<List<Command>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),
                            "Code: " + response.code(), LENGTH_LONG).show();
                    return;
                }
                List<Command> commandes = response.body();
                for (Command commande : commandes)
                {
                    commandList.add(commande);
                }

                adapter = new HistoryAdapter( getApplicationContext(),commandList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Command>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        t.getMessage(), LENGTH_LONG).show();
            }
        });
    }
}
