package com.example.gmailapp.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.gmailapp.model.AssignLabelsRequest;
import com.example.gmailapp.model.Mail;
import com.example.gmailapp.network.AuthService;
import com.example.gmailapp.network.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InboxRepository {
    private final AuthService authService;

    public InboxRepository() {
        authService = RetrofitClient.getInstance().create(AuthService.class);
    }

    public LiveData<List<Mail>> getMails(String jwt) {
        MutableLiveData<List<Mail>> data = new MutableLiveData<>();


        authService.getMails("Bearer " + jwt).enqueue(new Callback<List<Mail>>() {
            @Override
            public void onResponse(Call<List<Mail>> call, Response<List<Mail>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                } else {
                    String errorMsg = "Unknown error";
                    try {
                        if (response.errorBody() != null) {
                            errorMsg = response.errorBody().string();
                        }
                    } catch (Exception e) {
                        Log.e("INBOX_ERROR", "Error reading errorBody", e);
                    }
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Mail>> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<List<Mail>> searchMails(String jwt, String query) {
        MutableLiveData<List<Mail>> data = new MutableLiveData<>();

        authService.searchMails("Bearer " + jwt, query).enqueue(new Callback<List<Mail>>() {
            @Override
            public void onResponse(Call<List<Mail>> call, Response<List<Mail>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Mail>> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
    public LiveData<Boolean> markAsImportant(String jwt, String mailId, boolean importantStatus) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        Map<String, Boolean> body = new HashMap<>();
        body.put("important", importantStatus);
        authService.markAsImportant("Bearer " + jwt, mailId, body).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                result.setValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                result.setValue(false);
            }
        });

        return result;
    }






    public LiveData<List<Mail>> getMailsByLabel(String jwt, String labelId) {
        MutableLiveData<List<Mail>> data = new MutableLiveData<>();
        authService.getMailsByLabel("Bearer " + jwt, labelId).enqueue(new Callback<List<Mail>>() {
            @Override
            public void onResponse(Call<List<Mail>> call, Response<List<Mail>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Mail>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<List<Mail>> getImportantMails(String jwt) {
        MutableLiveData<List<Mail>> data = new MutableLiveData<>();
        authService.getMails("Bearer " + jwt).enqueue(new Callback<List<Mail>>() {
            @Override
            public void onResponse(Call<List<Mail>> call, Response<List<Mail>> response) {
                if (response.isSuccessful()) {
                    List<Mail> allMails = response.body();
                    List<Mail> importantMails = new ArrayList<>();
                    for (Mail mail : allMails) {
                        if (mail.isImportant()) {
                            importantMails.add(mail);
                        }
                    }
                    data.setValue(importantMails);
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Mail>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }










}
