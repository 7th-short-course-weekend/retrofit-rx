package com.rathana.retrofit_rxandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.rathana.retrofit_rxandroid.adapter.ArticleAdapter;
import com.rathana.retrofit_rxandroid.data.network.ServiceGenerator;
import com.rathana.retrofit_rxandroid.data.network.service.ArticleService;
import com.rathana.retrofit_rxandroid.model.Article;
import com.rathana.retrofit_rxandroid.model.ArticleResponse;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class MainActivity extends AppCompatActivity {

    ArticleService articleService;
    CompositeDisposable disposable=new CompositeDisposable();

    RecyclerView rvArticle;
    ArticleAdapter articleAdapter;
    List<Article> articles=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        articleService = ServiceGenerator.createService(ArticleService.class);

        setupUI();
        getArticles(1,30);
    }


    void setupUI(){
        rvArticle=findViewById(R.id.rvArticle);
        rvArticle.setLayoutManager(new LinearLayoutManager(this));
        articleAdapter=new ArticleAdapter(articles,this);
        rvArticle.setAdapter(articleAdapter);
    }

    private void getArticles(int page, int limit) {
        Flowable<ArticleResponse> flowable= articleService.getArticles(page,limit);
        disposable.add(
                flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                    data->{
//                    Log.e(TAG, "getArticles: "+data.getArticle() );
//                    },
//                    error->{
//                        Log.e(TAG, "getArticles: "+ error.toString());
//                    },
//                    ()->{
//
//                    }
//                )
                .subscribeWith(new DisposableSubscriber<ArticleResponse>(){
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onNext(ArticleResponse articleResponse) {
                        Log.e(TAG, "getArticles: "+articleResponse.getArticle());
                        articleAdapter.addMOreItems(articleResponse.getArticle());
                    }
                })
        );

    }

    private static final String TAG = "MainActivity";

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}

