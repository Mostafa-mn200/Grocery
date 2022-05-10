package com.example.grocery.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocery.R;
import com.example.grocery.activits.ViewAllActivity;
import com.example.grocery.models.PopulerModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHollder> {
    private Context context;
    private List<PopulerModel>populerModelList;

    public PopularAdapter(Context context, List<PopulerModel> populerModelList) {
        this.context = context;
        this.populerModelList = populerModelList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHollder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHollder(LayoutInflater.from(parent.getContext()).inflate(R.layout.populer_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHollder holder, int position) {
        Glide.with(context).load(populerModelList.get(position).getImg_url()).into(holder.popImg);
        holder.name.setText(populerModelList.get(position).getName());
        holder.rating.setText(populerModelList.get(position).getRating());
        holder.description.setText(populerModelList.get(position).getDescription());
        holder.discount.setText(populerModelList.get(position).getDiscount());

        holder.itemView.setOnClickListener(view -> {
            Intent intent=new Intent(context, ViewAllActivity.class);
            intent.putExtra("type",populerModelList.get(position).getType());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return populerModelList.size();
    }

    public class ViewHollder extends RecyclerView.ViewHolder{
        ImageView popImg;
        TextView name,description,rating,discount;
        public ViewHollder(@NonNull @NotNull View itemView) {
            super(itemView);
            popImg=itemView.findViewById(R.id.pop_img);
            name=itemView.findViewById(R.id.pop_name);
            description=itemView.findViewById(R.id.pop_des);
            rating=itemView.findViewById(R.id.pop_rating);
            discount=itemView.findViewById(R.id.pop_discount);

        }
    }

}
