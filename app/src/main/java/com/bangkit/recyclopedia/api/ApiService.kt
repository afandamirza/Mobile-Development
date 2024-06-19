package com.bangkit.recyclopedia.api


import com.bangkit.recyclopedia.api.response.FileUploadResponse
import com.bangkit.recyclopedia.api.response.ImagePredictionResponse
import com.bangkit.recyclopedia.api.response.LoginResponse
import com.bangkit.recyclopedia.api.response.ResetPasswordResponse
import com.bangkit.recyclopedia.data.model.UserLoginModel
import com.bangkit.recyclopedia.data.model.UserResetPasswordModel
import com.bangkit.recyclopedia.data.model.UserSignUpModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    // Register New User
    @POST("api/register")
    fun signupUser(
        @Body user: UserSignUpModel
    ): Call<FileUploadResponse>

    @POST("api/login")
    fun login(
        @Body loginRequest: UserLoginModel
    ): Call<LoginResponse>

    @Multipart
    @POST("predict")
    fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<ImagePredictionResponse>

    @POST("api/reset-password")
    fun resetPassword(
        @Body resetPasswordRequest: UserResetPasswordModel
    ): Call<ResetPasswordResponse>

    // Get All Stories With Paging
//    @GET("stories")
//    suspend fun getAllStoriesWithPaging(
//        @Header("Authorization") token: String,
//        @Query("page") page: Int,
//        @Query("size") size: Int,
//    ): ListStoriesResponse
//
//    @GET("stories")
//    fun getStoriesWithLocation(
//        @Header("Authorization") token: String,
//        @Query("location") location: Int
//    ): Call<ListStoriesResponse>

    // Add New Story Without Location
    @Multipart
    @POST("stories")
    fun uploadStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<FileUploadResponse>

    @Multipart
    @POST("stories")
    fun uploadStoryWithLocation(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: Float,
        @Part("lon") lon: Float
    ): Call<FileUploadResponse>
}