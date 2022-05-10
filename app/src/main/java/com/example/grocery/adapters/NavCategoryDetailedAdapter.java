package com.example.grocery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocery.R;
import com.example.grocery.models.HomeCategory;
import com.example.grocery.models.NavCategoryDetailedModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NavCategoryDetailedAdapter extends RecyclerView.Adapter<NavCategoryDetailedAdapter.VH> {
    private Context context;
    private List<NavCategoryDetailedModel> navCategoryDetailedModelList;

    public NavCategoryDetailedAdapter(Context context, List<NavCategoryDetailedModel> navCategoryDetailedModelList) {
        this.context = context;
        this.navCategoryDetailedModelList = navCategoryDetailedModelList;
    }

    @NonNull
    @NotNull
    @Override
    public VH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_category_detailed_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VH holder, int position) {
        Glide.with(context).load(navCategoryDetailedModelList.get(position).getImg_url()).into(holder.img);
        holder.name.setText(navCategoryDetailedModelList.get(position).getName());
        holder.price.setText(navCategoryDetailedModelList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return navCategoryDetailedModelList.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name,price;
        public VH(@NonNull @NotNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.cat_nav_img);
            name=itemView.findViewById(R.id.nav_cat_name);
            price=itemView.findViewById(R.id.price);
        }
    }
}
