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
import com.example.grocery.activits.detailedActivity;
import com.example.grocery.models.PopulerModel;
import com.example.grocery.models.ViewAllModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHollder>{
    private Context context;
    private List<ViewAllModel> viewAllModelList;

    public ViewAllAdapter(Context context, List<ViewAllModel> viewAllModelList) {
        this.context = context;
        this.viewAllModelList = viewAllModelList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHollder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHollder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHollder holder, int position) {
        Glide.with(context).load(viewAllModelList.get(position).getImg_url()).into(holder.Img);
        holder.name.setText(viewAllModelList.get(position).getName());
        holder.rating.setText(viewAllModelList.get(position).getRating());
        holder.description.setText(viewAllModelList.get(position).getDescription());
        holder.price.setText(viewAllModelList.get(position).getPrice()+"");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, detailedActivity.class);
                intent.putExtra("detail",viewAllModelList.get(position));
                context.startActivity(intent);
            }
        });
        
    }

    @Override
    public int getItemCount() {
        return viewAllModelList.size();
    }

    public class ViewHollder extends RecyclerView.ViewHolder{
        ImageView Img;
        TextView name,description,rating,price;
        public ViewHollder(@NonNull @NotNull View itemView) {
            super(itemView);
            Img=itemView.findViewById(R.id.view_all_img);
            name=itemView.findViewById(R.id.view_all_name);
            description=itemView.findViewById(R.id.view_all_description);
            rating=itemView.findViewById(R.id.view_all_rating);
            price=itemView.findViewById(R.id.view_all_price2);
        }
    }
}
