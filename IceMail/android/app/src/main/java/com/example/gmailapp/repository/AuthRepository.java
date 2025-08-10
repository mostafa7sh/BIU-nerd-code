package com.example.gmailapp.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.gmailapp.model.LoginRequest;
import com.example.gmailapp.model.LoginResponse;
import com.example.gmailapp.network.AuthService;
import com.example.gmailapp.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.io.IOException;

public class AuthRepository {
    private final AuthService authService;

    public AuthRepository() {
        authService = RetrofitClient.getInstance().create(AuthService.class);
    }

    public LiveData<String> login(String email, String password) {
        MutableLiveData<String> tokenLiveData = new MutableLiveData<>();
        LoginRequest request = new LoginRequest(email, password);
        authService.login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tokenLiveData.setValue(response.body().getToken());
                } else {
                    tokenLiveData.setValue(null);
                }
            }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                tokenLiveData.setValue(null);
            }
        });

        return tokenLiveData;
    }
}
