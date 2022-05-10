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
import com.example.grocery.activits.NavCategoryActivity;
import com.example.grocery.models.NavCategoryModel;
import com.example.grocery.models.PopulerModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NavCategoryAdapter extends RecyclerView.Adapter<NavCategoryAdapter.VH> {
    private Context context;
    private List<NavCategoryModel> navCategoryModelList;

    public NavCategoryAdapter(Context context, List<NavCategoryModel> navCategoryModelList) {
        this.context = context;
        this.navCategoryModelList = navCategoryModelList;
    }

    @NonNull
    @NotNull
    @Override
    public VH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_cat_item,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VH holder, int position) {
        Glide.with(context).load(navCategoryModelList.get(position).getImg_url()).into(holder.Img);
        holder.name.setText(navCategoryModelList.get(position).getName());
        holder.description.setText(navCategoryModelList.get(position).getDescription());
        holder.discount.setText(navCategoryModelList.get(position).getDiscount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, NavCategoryActivity.class);
                intent.putExtra("type",navCategoryModelList.get(position).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return navCategoryModelList.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        ImageView Img;
        TextView name,description,discount;
        public VH(@NonNull @NotNull View itemView) {
            super(itemView);
            Img=itemView.findViewById(R.id.cat_nav_img);
            name=itemView.findViewById(R.id.nav_cat_name);
            description=itemView.findViewById(R.id.nav_cat_description);
            discount=itemView.findViewById(R.id.nav_cat_discount);
        }
    }
}
