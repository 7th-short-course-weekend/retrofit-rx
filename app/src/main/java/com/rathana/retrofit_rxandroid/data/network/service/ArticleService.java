package com.rathana.retrofit_rxandroid.data.network.service;


import com.rathana.retrofit_rxandroid.model.ArticleResponse;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ArticleService  {

    // Observable Flowable, Single

    @GET("/v1/api/articles")
    Flowable<ArticleResponse> getArticles(
            @Query("page")int page,
            @Query("limit")int limit
    );

    @GET("/v1/api/articles")
    Observable<ArticleResponse> articlesObservable(
            @Query("page")int page,
            @Query("limit")int limit
    );
}
