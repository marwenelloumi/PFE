package com.example.kheerMaro.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kheerMaro.Controller.ApiHold;
import com.example.kheerMaro.Controller.SensorAdapter;
import com.example.kheerMaro.Model.Plant;
import com.example.kheerMaro.Model.Value;
import com.example.kheerMaro.R;
import com.google.gson.JsonParser;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.LENGTH_LONG;
import static com.example.kheerMaro.SERVER.URLs.ROOT_URL;

public class CommndActivity extends AppCompatActivity {
    private TextView name , suffisance  , id;
    //private TextView description;
    private Button arrosage;
    private Bundle extras;
    private ApiHold apiHold;
    private Boolean arrosageOp;
    private ProgressBar mqttProgressBar;
    private ImageView suffisanceImage;
    private List<String> sensorList;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView ;

    //MQTT
    private static final String TOPIC_COMMANDE ="commande";//topic de commande manuelle
    private static final String TOPIC_RECEPTION ="value";//topic de vérification de connecton
    public static  String USERNAME ="devaptyx";//client mqtt username
    public static  String PASSWORD ="devaptyx" ;//client mqtt password
    public static  String BROKER ="tcp://192.168.1.16:1883";//adresse du broker
    private MqttAndroidClient client ; //création d'une client mqtt
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
        setContentView(R.layout.activity_commnd);
        recyclerView = findViewById(R.id.sensor_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sensorList =  new ArrayList<>();
        name = findViewById(R.id.textView1);
       // description = findViewById(R.id.textView2);
        suffisance = findViewById(R.id.textView3);
        suffisanceImage = findViewById(R.id.imageView2);
        arrosage = findViewById(R.id.button);
        extras = getIntent().getExtras();
        mqttProgressBar = findViewById(R.id.progressBar);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiHold = retrofit.create(ApiHold.class);
        Call<Plant> callplant = apiHold.getPlantsByID(extras.getString("id"));
        callplant.enqueue(new Callback<Plant>() {
            @Override
            public void onResponse(Call<Plant> call, Response<Plant> response) {
                Plant plante = response.body();
                name.setText(plante.getName());
                //description.setText(plante.getDescription());

            }

            @Override
            public void onFailure(Call<Plant> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        t.getMessage(), LENGTH_LONG).show();
            }
        });
        Call<Value> callvalue = apiHold.getValue(extras.getString("id"));
        callvalue.enqueue(new Callback <Value>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),
                            "Code: " + response.code(), LENGTH_LONG).show();
                    return;
                }

                Value valeurRecue = response.body();

                if (valeurRecue.getTemperature() != null)
                    sensorList.add("Température : " + valeurRecue.getTemperature() + "°C \n");
                if (valeurRecue.getHumidity() != null)
                    sensorList.add("Humidité : " + valeurRecue.getHumidity() + "% \n");
                if (valeurRecue.getSoilMoisture() != null)
                    sensorList.add("Humidité du sol: " + valeurRecue.getSoilMoisture() + "% \n");

                adapter = new SensorAdapter( getApplicationContext(),sensorList);
                recyclerView.setAdapter(adapter);

                if (valeurRecue.getWaterLevel()){
                    suffisanceImage.setImageResource(R.drawable.ic_green_water);
                    suffisance.setText("la quantité d'eau est suffisante");
                }
                else {
                    suffisanceImage.setImageResource(R.drawable.ic_red_water);
                    suffisance.setText("la quantité d'eau n'est pas suffusante");

                }

                if (valeurRecue.getWateringActivated()) {
                    arrosage.setText("Arreter l'arrosage");
                    arrosageOp = true;
                } else {
                    arrosage.setText("Arroser");
                    arrosageOp = false;
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        t.getMessage(), LENGTH_LONG).show();

            }
        });
        arrosage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (arrosageOp) {
                        //Si operation d'arrosage est actvié donc on doit la désactivée
                        //=> arroser false

                        arroser(false);
                       arrosageOp = false;
                       arrosage.setText("arroser");

                    } else {

                        arroser(true);
                        arrosageOp = true;
                       arrosage.setText("Arreter l'arrosage");
                    }
            }

        });
    }
    public void arroser(final Boolean arro)
    {
        // générer un ID du client
        String clientId = MqttClient.generateClientId();
        //creation d'un client mqtt
        client= new MqttAndroidClient(getApplicationContext(), BROKER , clientId);
        //Saisie des options nécessaires pour se connecter au BROKER
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());
        //connection
        try {
            // se connecter
            IMqttToken token = client.connect(options);
            // rappel de connexion au broker
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    SharedPreferences sharedPreferences =
                            getApplication().getSharedPreferences("userToken", Context.MODE_PRIVATE);
                    JSONObject objectToSend = new JSONObject();
                    try {
                        objectToSend.put("plantID",extras.getString("id"));

                        objectToSend.put("commandeSysteme",arro);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // affichage d'un toast
                    try {client.subscribe(TOPIC_RECEPTION,0);
                    }
                    catch (MqttException e) {
                        e.printStackTrace();}
                    try {
                        client.publish(TOPIC_COMMANDE,objectToSend.toString().getBytes(),0,false);
                    } catch (MqttException e) {
                        e.printStackTrace();}

                    mqttProgressBar.setVisibility(View.VISIBLE);
                    arrosage.setClickable(false);
                    ack();



                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(getApplicationContext(), "not connected", Toast.LENGTH_SHORT).show();
                }

            });

        }
        catch (MqttException e) {
            e.printStackTrace();}

    }
    private void ack()
    {
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                if(topic.equals(TOPIC_RECEPTION))
                {
                    String jsonStr= message.toString();
                    JSONObject jsonMessage = new JSONObject(jsonStr);
                    String idReceived =(String) jsonMessage.get("plantID");

                    if (idReceived.equals(extras.getString("id")) ) {

                        mqttProgressBar.setVisibility(View.INVISIBLE);
                        arrosage.setClickable(true);
                    }
                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

}
