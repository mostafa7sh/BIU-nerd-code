package com.example.gmailapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.gmailapp.model.AssignLabelsRequest;
import com.example.gmailapp.model.Label;
import com.example.gmailapp.network.AuthService;
import com.example.gmailapp.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LabelRepository {
    private final AuthService authService;

    public LabelRepository() {
        authService = RetrofitClient.getInstance().create(AuthService.class);
    }

    public LiveData<List<Label>> getLabels(String jwt) {
        MutableLiveData<List<Label>> data = new MutableLiveData<>();

        authService.getLabels("Bearer " + jwt).enqueue(new Callback<List<Label>>() {
            @Override
            public void onResponse(Call<List<Label>> call, Response<List<Label>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Label>> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<Boolean> assignLabelsToMail(String jwt, String mailId, List<String> labelIds) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        AssignLabelsRequest request = new AssignLabelsRequest(labelIds);

        authService.assignLabelsToMail("Bearer " + jwt, mailId, request).enqueue(new Callback<Void>() {
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

    public LiveData<Boolean> updateLabel(String jwt, Label label) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        authService.updateLabel("Bearer " + jwt, label.getId(), label).enqueue(new Callback<Void>() {
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

    public LiveData<Boolean> deleteLabel(String jwt, String labelId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        authService.deleteLabel("Bearer " + jwt, labelId).enqueue(new Callback<Void>() {
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

}
