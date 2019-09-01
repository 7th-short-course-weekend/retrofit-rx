package com.rathana.retrofit_rxandroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rathana.retrofit_rxandroid.R;
import com.rathana.retrofit_rxandroid.model.Article;
import com.rathana.retrofit_rxandroid.model.Author;
import com.rathana.retrofit_rxandroid.model.Category;
import com.rathana.retrofit_rxandroid.uitls.DateFormatter;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ItemViewHolder> {


    List<Article> articles;
    Context context;

    public ArticleAdapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @Override
    public int getItemCount() { return articles.size(); }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        Article article= articles.get(i);
        Author user=article.getAuthor();
        Category cat=article.getCategory();
        itemViewHolder.title.setText(article.getTitle() != null ? article.getTitle() : "");
        itemViewHolder.author.setText(user.getName() != null ? user.getName() : "");
        itemViewHolder.date.setText(DateFormatter.format(article.getCreatedDate()));
        itemViewHolder.category.setText(cat.getName() != null ? cat.getName() : "");
        //image
        //todo bind image to image View
        if (article.getImage() != null) {
            Glide.with(context)
                    .load(article.getImage())
                    .override(250, 160)
                    .error(R.drawable.ic_picture)
                    .placeholder(R.drawable.ic_picture)
                    .into(itemViewHolder.thumb);
        } else {
            itemViewHolder.thumb.setImageResource(R.drawable.ic_picture);
        }
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(
                R.layout.article_item_layout,
                viewGroup,
                false
        );
        return new ItemViewHolder(view);
    }

    public void addMOreItems(List<Article> articles) {
        int previousSize= this.articles.size();
        this.articles.addAll(articles);
        notifyItemRangeInserted(previousSize-1,articles.size());
    }

    class  ItemViewHolder extends RecyclerView.ViewHolder {
        TextView title, date, author, category;
        ImageView thumb, btnFavorite, btnDel, btnEdit;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            author = itemView.findViewById(R.id.author);
            category = itemView.findViewById(R.id.category);
            thumb = itemView.findViewById(R.id.thumb);
            btnFavorite = itemView.findViewById(R.id.favorite);

            btnDel = itemView.findViewById(R.id.btnDel);
            btnEdit = itemView.findViewById(R.id.btnedit);
        }
    }
}
