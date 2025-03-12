package com.example.projectprm392.java.Adap;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectprm392.R;
import com.example.projectprm392.java.Activities.DetailActivity;
import com.example.projectprm392.java.Entities.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private Context context;
    private List<Product> list;

    public HomeAdapter(Context context, List<Product> list) {
        this.context = context;
        this.list = (list != null) ? list : new ArrayList<>(); // Kiểm tra null
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemviewhome, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product p = list.get(position);
        if (p != null) {
            Picasso.get().load(p.getImageURL()).into(holder.img);
            holder.tvMa.setText("Price: " + String.valueOf(p.getPrice()) + "$");
            holder.tvTen.setText(p.getProductName());
            holder.tvSl.setText("Quanlity: "+String.valueOf(p.getStockQuantity()));
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("Product", p);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0; // Tránh NullPointerException
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvMa, tvTen, tvSl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.itemview_img);
            tvMa = itemView.findViewById(R.id.itemview_priceMasp);
            tvTen = itemView.findViewById(R.id.itemview_tvTensp);
            tvSl = itemView.findViewById(R.id.itemview_tvSl);
        }
    }
}
