package com.example.kheerMaro.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.kheerMaro.Model.User;
import com.example.kheerMaro.UI.LoginActivity;

public class SessionManager {
    /*
     pour enregistrer les informations de l'utilisateur dans le smartphone
    */
    //les mot clé que nous avons besoins pour retrouvers les informations de l'utilisateur
    private static final String SHARED_PREF_NAME = "userToken";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_ID = "user_id";

    private  static  SessionManager mInstance;
    private static Context mCtx;

    //construire umanager de session avec  le contexte actuel
    public SessionManager(Context context) {
        mCtx = context;
    }
    //retourner le seessionManager actuel avec le contexte actuel
    public static synchronized SessionManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new SessionManager(context);
        }
        return  mInstance;
    }
    /*
    enregitrer les informations dans le smartphone lors de l'opération du login
    */
    public void userLogin(User user){
        //recevoir les SharedPreferences que nous avons déjà déclaré
        SharedPreferences sharedPreferences =
                mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        // enregistrer les informations de l'utilisateur en créant SharedPreferences.Editor
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // enregistrer les informations
        editor.putString(KEY_ID, user.getId());
        editor.putString(KEY_NAME,user.getName());
        editor.putString(KEY_EMAIL,user.getEmail());
        editor.putString(KEY_PASSWORD,user.getPassword());
        editor.putString(KEY_TOKEN,user.getToken());
        //executer les  changements
        editor.apply();
    }

    public  boolean isLoggedIn(){
        //recevoir les SharedPreferences que nous avons déjà déclaré
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        // si KEY_TOKEN = null alors Token n'est pas stocker dans le smartphone alors  l'utilisateur n'est pas connecté
        return sharedPreferences.getString(KEY_TOKEN,null) != null;
    }

    public User getToken(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        //retourner un utilisateur avec le Token déjà saisi lors du connection
        return new User(sharedPreferences.getString(KEY_TOKEN,null));
    }

    public void userLogout(){
        SharedPreferences sharedPreferences =
                mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //effacer les données que noous avons stocker du téléphone
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }

    //recevoir tout les informations de l'utilisateur
    public User getUser(User user){
        SharedPreferences sharedPreferences =
                mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_ID,null),
                sharedPreferences.getString(KEY_NAME,null),
                sharedPreferences.getString(KEY_EMAIL,null),
                sharedPreferences.getString(KEY_PASSWORD,null),
                sharedPreferences.getString(KEY_TOKEN,null)
        );
    }
}
