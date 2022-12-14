package com.example.meditake.services;

import com.example.meditake.database.dto.UtilisateurLogin;
import com.example.meditake.database.entities.Medecin;
import com.example.meditake.database.entities.Utilisateur;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UtilisateurService {

    @POST("utilisateur/medecin/login")
    Call<Medecin> logMedecinIn(@Body UtilisateurLogin utilisateurLogin);

    @POST("utilisateur/patient/login")
    Call<Medecin> logPatientIn(@Body UtilisateurLogin utilisateurLogin);

    @POST("utilisateur/login")
    Call<Utilisateur> login(@Body UtilisateurLogin utilisateurLogin);

    @GET("utilisateur/medecin/all")
    Call<List<Medecin>> getAll();

    @GET("utilisateur/verifymail/{mail}")
    Call<String> verifyMail(@Path("mail") String mail);
    @GET("utilisateur/changepassword/{mail}/{password}")
    Call<String> changePassword(@Path("mail") String mail,@Path("password") String password);

}
