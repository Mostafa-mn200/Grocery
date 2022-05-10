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
import com.example.grocery.models.HomeCategory;
import com.example.grocery.models.PopulerModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.VH> {
    private Context context;
    private List<HomeCategory> homeCategoryList;

    public HomeAdapter(Context context, List<HomeCategory> homeCategoryList) {
        this.context = context;
        this.homeCategoryList = homeCategoryList;
    }

    @NonNull
    @NotNull
    @Override
    public VH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_cat_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VH holder, int position) {
        Glide.with(context).load(homeCategoryList.get(position).getImg_url()).into(holder.ImgCat);
        holder.name.setText(homeCategoryList.get(position).getName());

        holder.itemView.setOnClickListener(view -> {
            Intent intent=new Intent(context, ViewAllActivity.class);
            intent.putExtra("type",homeCategoryList.get(position).getType());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return homeCategoryList.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        ImageView ImgCat;
        TextView name;
        public VH(@NonNull @NotNull View itemView) {
            super(itemView);
            ImgCat=itemView.findViewById(R.id.home_cat_img);
            name=itemView.findViewById(R.id.home_cat_name);
        }
    }
}
