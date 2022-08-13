package com.example.kheerMaro.Controller;

import com.example.kheerMaro.Model.Command;
import com.example.kheerMaro.Model.Plant;
import com.example.kheerMaro.Model.User;
import com.example.kheerMaro.Model.Value;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiHold {
    @POST("login")
    Call<User> login(@Body User u);
    @GET("plantes")
    Call<List<Plant>> getPlants(@Header("Authorization")String Header);
    @GET("plantes/{id}/")
    Call<Plant> getPlantsByID(@Path("id") String PlantID);
    @GET("capteurs/{id}/")
    Call <Value> getValue(@Path("id") String PlantID);
    @GET("commandes/{id}/{nbr}")
    Call<Command> getCommandsDate(@Path("id")String plantID, @Path("nbr") Integer nbr);
    @GET("commandes/{id}/{nbr}")
    Call<List<Command>> getCommands(@Path("id")String plantID, @Path("nbr") Integer nbr);
    @PATCH("utilisateurs/{password}")
    Call<User> updatePassword(@Path("password") String password , @Body User u);
}
