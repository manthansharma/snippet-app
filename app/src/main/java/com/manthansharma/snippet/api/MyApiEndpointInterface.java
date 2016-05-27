package com.manthansharma.snippet.api;

import com.manthansharma.snippet.api.model.snippetList;
import com.manthansharma.snippet.api.model.Snippet;
import com.manthansharma.snippet.api.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface MyApiEndpointInterface {
	// Request method and URL specified in the annotation
	// Callback for the parsed response is the last parameter

	@POST("api-token-auth/")
	Call<User> get_token(@Body User user);

	@POST("users/")
	Call<User> create_user(@Body User user);

	@POST("snippet/")
	Call<Snippet> create_snippet(@Body Snippet snippet);

	@GET()
	Call<snippetList> list_snippet(@Url String url);
}