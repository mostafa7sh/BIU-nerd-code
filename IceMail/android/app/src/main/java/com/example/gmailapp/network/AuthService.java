package com.example.gmailapp.network;

import com.example.gmailapp.model.AssignLabelsRequest;
import com.example.gmailapp.model.Label;
import com.example.gmailapp.model.LoginRequest;
import com.example.gmailapp.model.LoginResponse;
import com.example.gmailapp.model.Mail;
import com.example.gmailapp.model.MailRequest;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PATCH;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface AuthService {

    @POST("/api/tokens")
    Call<LoginResponse> login(@Body LoginRequest request);

    @Multipart
    @POST("/api/users")
    Call<LoginResponse> registerMultipart(
            @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("dateOfBirth") RequestBody dateOfBirth,
            @Part MultipartBody.Part profilePic
    );

    @GET("/api/mails/important")
    Call<List<Mail>> getImportantMails(@Header("Authorization") String token);


    @GET("/api/mails")
    Call<List<Mail>> getMails(@Header("Authorization") String token);

    @GET("/api/mails/{id}")
    Call<Mail> getMailById(@Header("Authorization") String token, @Path("id") String mailId);

    @POST("/api/mails")
    Call<Void> sendMail(@Header("Authorization") String token, @Body MailRequest request);

    @POST("/api/mails/{id}/important")
    Call<Void> markAsImportant(
            @Header("Authorization") String token,
            @Path("id") String mailId,
            @Body Map<String, Boolean> body
    );


    @GET("/api/mails/search/{query}")
    Call<List<Mail>> searchMails(@Header("Authorization") String token, @Path("query") String query);

    @POST("/api/mails/draft")
    Call<Void> createDraft(@Header("Authorization") String token, @Body MailRequest request);

    @DELETE("/api/mails/{id}")
    Call<Void> deleteMail(@Header("Authorization") String token, @Path("id") String mailId);

    @PATCH("/api/mails/{id}")
    Call<Void> updateMail(@Header("Authorization") String token, @Path("id") String mailId, @Body MailRequest request);

    @PATCH("/api/mails/bulk-read")
    Call<Void> markBulkRead(@Header("Authorization") String token, @Body List<String> mailIds);

    @GET("/api/labels")
    Call<List<Label>> getLabels(@Header("Authorization") String token);

    @POST("/api/labels")
    Call<Label> createLabel(@Header("Authorization") String token, @Body Label label);

    @GET("/api/labels/{id}/mails")
    Call<List<Mail>> getMailsByLabel(@Header("Authorization") String token, @Path("id") String labelId);

    @PUT("/api/labels/{id}")
    Call<Void> updateLabel(@Header("Authorization") String token, @Path("id") String labelId, @Body Label label);

    @DELETE("/api/labels/{id}")
    Call<Void> deleteLabel(@Header("Authorization") String token, @Path("id") String labelId);

    @POST("/api/mails/{id}/labels")
    Call<Void> assignLabelsToMail(@Header("Authorization") String token, @Path("id") String mailId, @Body AssignLabelsRequest request);

    @POST("/api/blacklist")
    Call<Void> addToBlacklist(@Header("Authorization") String token, @Body MailRequest request); // רק אם יש מודל מתאים

    @DELETE("/api/blacklist/{id}")
    Call<Void> removeFromBlacklist(@Header("Authorization") String token, @Path("id") String blacklistId);
}
