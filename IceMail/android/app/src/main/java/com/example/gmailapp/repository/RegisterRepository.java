package com.example.gmailapp.repository;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.gmailapp.model.LoginResponse;
import com.example.gmailapp.network.AuthService;
import com.example.gmailapp.network.RetrofitClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRepository {
    private final AuthService authService;

    public RegisterRepository() {
        authService = RetrofitClient.getInstance().create(AuthService.class);
    }

    public LiveData<String> register(String username, String email, String password, String dateOfBirth, Uri imageUri, Context context) {
        MutableLiveData<String> result = new MutableLiveData<>();

        try {
            RequestBody usernameBody = RequestBody.create(MediaType.parse("text/plain"), username);
            RequestBody emailBody = RequestBody.create(MediaType.parse("text/plain"), email);
            RequestBody passwordBody = RequestBody.create(MediaType.parse("text/plain"), password);
            RequestBody dateOfBirthBody = RequestBody.create(MediaType.parse("text/plain"), dateOfBirth);

            MultipartBody.Part imagePart = null;

            if (imageUri != null) {
                InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
                File file = File.createTempFile("profile", ".jpg", context.getCacheDir());
                OutputStream outputStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int read;
                while ((read = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, read);
                }
                outputStream.close();
                inputStream.close();

                RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
                imagePart = MultipartBody.Part.createFormData("profilePic", file.getName(), fileBody);
            }
            authService.registerMultipart(usernameBody, emailBody, passwordBody, dateOfBirthBody, imagePart)
                    .enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful()) {
                                result.setValue(response.body().getToken());
                            } else {
                                try {
                                    String errorBody = response.errorBody().string();
                                    result.setValue("fail: " + errorBody);
                                } catch (IOException e) {
                                    result.setValue("fail: error parsing error body");
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            result.setValue("fail: " + t.getMessage());
                        }
                    });


        } catch (Exception e) {
            e.printStackTrace();
            result.setValue(null);
        }

        return result;
    }
}
