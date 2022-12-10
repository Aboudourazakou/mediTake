package com.example.meditake.services;

import com.example.meditake.database.dto.RapportDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/***
 "Created by  Godwin Kvg on "12/8/2022
 "Project name "MediTake
 */
public interface RapportService {

    @POST("rapport/sendMail")
    Call<Void> sendReportToMail(@Body List<RapportDto> rapports);

}
