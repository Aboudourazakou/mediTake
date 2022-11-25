package com.example.meditake.services;

import com.example.meditake.database.dto.UtilisateurLogin;
import com.example.meditake.database.entities.Medecin;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UtilisateurService {

    @POST("utilisateur/medecin/login")
    Call<Medecin> logMedecinIn(@Body UtilisateurLogin utilisateurLogin);

    @POST("utilisateur/patient/login")
    Call<Medecin> logPatientIn(@Body UtilisateurLogin utilisateurLogin);

    @GET("utilisateur/medecin/all")
    Call<List<Medecin>> getAll();

}
