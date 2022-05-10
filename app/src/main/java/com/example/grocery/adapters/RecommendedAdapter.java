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
import com.example.grocery.models.RecommendedModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.VH> {
    private Context context;
    private List<RecommendedModel> recommendedModelList;

    public RecommendedAdapter(Context context, List<RecommendedModel> recommendedModelList) {
        this.context = context;
        this.recommendedModelList = recommendedModelList;
    }

    @NonNull
    @NotNull
    @Override
    public VH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VH holder, int position) {
        Glide.with(context).load(recommendedModelList.get(position).getImg_url()).into(holder.recImg);
        holder.name.setText(recommendedModelList.get(position).getName());
        holder.rating.setText(recommendedModelList.get(position).getRating());
        holder.description.setText(recommendedModelList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return recommendedModelList.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        ImageView recImg;
        TextView name,description,rating;
        public VH(@NonNull @NotNull View itemView) {
            super(itemView);
            recImg=itemView.findViewById(R.id.rec_img);
            name=itemView.findViewById(R.id.rec_name);
            description=itemView.findViewById(R.id.rec_des);
            rating=itemView.findViewById(R.id.rec_rating);
        }
    }
}
