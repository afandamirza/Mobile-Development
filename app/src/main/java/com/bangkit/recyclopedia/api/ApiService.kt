package com.bangkit.recyclopedia.api


interface ApiService {
//    // Register New User
//    @POST("register")
//    fun signupUser(
//        @Body user: UserSignUpModel
//    ): Call<FileUploadResponse>
//
//    // User Login
//    @POST("login")
//    fun loginUser(
//        @Body user: UserLoginModel
//    ): Call<LoginAppResponse>
//
//    // Get All Stories With Paging
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
//
//    // Add New Story Without Location
//    @Multipart
//    @POST("stories")
//    fun uploadStory(
//        @Header("Authorization") token: String,
//        @Part file: MultipartBody.Part,
//        @Part("description") description: RequestBody,
//    ): Call<FileUploadResponse>
//
//    @Multipart
//    @POST("stories")
//    fun uploadStoryWithLocation(
//        @Header("Authorization") token: String,
//        @Part file: MultipartBody.Part,
//        @Part("description") description: RequestBody,
//        @Part("lat") lat: Float,
//        @Part("lon") lon: Float
//    ): Call<FileUploadResponse>
}