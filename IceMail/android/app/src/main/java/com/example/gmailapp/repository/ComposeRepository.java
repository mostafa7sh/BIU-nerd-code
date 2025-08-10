package com.example.gmailapp.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.gmailapp.model.MailRequest;
import com.example.gmailapp.network.AuthService;
import com.example.gmailapp.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComposeRepository {

    private final AuthService authService;
    public ComposeRepository() {
        authService = RetrofitClient.getInstance().create(AuthService.class);
    }
    public LiveData<Boolean> sendMail(String jwt, String to, String subject, String body) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        MailRequest request = new MailRequest(to, subject, body);
        authService.sendMail("Bearer " + jwt, request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    result.setValue(true);
                } else {
                    result.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                result.setValue(false);
            }
        });

        return result;
    }
}
