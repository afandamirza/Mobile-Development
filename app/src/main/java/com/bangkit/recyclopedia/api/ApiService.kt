package com.bangkit.recyclopedia.api


import com.bangkit.recyclopedia.api.response.FileUploadResponse
import com.bangkit.recyclopedia.api.response.HistoryResponse
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
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

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

//     Get All Stories With Paging
    @GET("/predict/histories")
    suspend fun getHistory(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): HistoryResponse
}