package com.example.projectprm392.java.Adap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectprm392.R;
import com.example.projectprm392.java.Entities.Product;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private List<Product> products;
    private TextView tvTotalPrice;

    public CartAdapter(Context context, List<Product> products, TextView tvTotalPrice) {
        this.context = context;
        this.products = products;
        this.tvTotalPrice = tvTotalPrice;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cartitemview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product currentProduct = products.get(position);

        // Hiển thị thông tin sản phẩm
        Picasso.get().load(currentProduct.getImageURL()).into(holder.imgProduct);
        holder.tvProductName.setText(currentProduct.getProductName());
        holder.tvProductPrice.setText(String.format(Locale.getDefault(), "$%.2f", currentProduct.getPrice()));
        holder.tvQuantity.setText(String.valueOf(currentProduct.getStockQuantity()));

        // Sự kiện tăng số lượng
        holder.btnIncrease.setOnClickListener(v -> {
            currentProduct.setStockQuantity(currentProduct.getStockQuantity() + 1);
            holder.tvQuantity.setText(String.valueOf(currentProduct.getStockQuantity()));
            updateTotalPrice();
        });

        // Sự kiện giảm số lượng
        holder.btnDecrease.setOnClickListener(v -> {
            if (currentProduct.getStockQuantity() > 1) {
                currentProduct.setStockQuantity(currentProduct.getStockQuantity() - 1);
                holder.tvQuantity.setText(String.valueOf(currentProduct.getStockQuantity()));
                updateTotalPrice();
            }
        });

        // Xóa sản phẩm khỏi giỏ hàng
        holder.btnDelete.setOnClickListener(v -> {
            products.remove(position);
            notifyDataSetChanged(); // Cập nhật toàn bộ danh sách giỏ hàng
            updateTotalPrice();
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    // Cập nhật tổng tiền
    private void updateTotalPrice() {
        double total = 0;
        for (Product p : products) {
            total += p.getPrice() * p.getStockQuantity(); // Tính đúng giá sản phẩm
        }
        tvTotalPrice.setText(String.format(Locale.getDefault(), "Total: $%.2f", total));
    }

    // ViewHolder để giữ view con
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvProductName, tvProductPrice, tvQuantity;
        Button btnIncrease, btnDecrease;
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.cartitem_img);
            tvProductName = itemView.findViewById(R.id.cartitem_tv_name);
            tvProductPrice = itemView.findViewById(R.id.cartitem_tv_price);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            btnIncrease = itemView.findViewById(R.id.btn_increase);
            btnDecrease = itemView.findViewById(R.id.btn_decrease);
            btnDelete = itemView.findViewById(R.id.btn_remove);
        }
    }
}
