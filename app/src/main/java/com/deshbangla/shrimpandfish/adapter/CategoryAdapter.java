package com.deshbangla.shrimpandfish.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deshbangla.shrimpandfish.model.category.Datum;
import com.deshbangla.shrimpandfish.R;
import com.deshbangla.shrimpandfish.activities.CategoryItemsActivity;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    Context context;
    List<Datum> categories;

    public CategoryAdapter(Context context, List<Datum> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(context.getString(R.string.ImgUrl).concat(categories.get(position).getImage())).into(holder.catImage);
        holder.catName.setText(categories.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CategoryItemsActivity.class);
                intent.putExtra("type",categories.get(position).getSlug());
                intent.putExtra("title",categories.get(position).getName());
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size()-1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView catImage;
        TextView catName;
        LinearLayout row_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            catImage = itemView.findViewById(R.id.categoryImage);
            catName = itemView.findViewById(R.id.categoryName);
            row_layout = itemView.findViewById(R.id.row_layout);

        }
    }

}
