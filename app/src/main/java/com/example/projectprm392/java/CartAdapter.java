package com.example.projectprm392.java;

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
import com.squareup.picasso.Picasso;

import java.util.List;

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
        Picasso.get().load(currentProduct.getSearch_image()).into(holder.imgProduct);
        holder.tvProductName.setText(currentProduct.getName());
        holder.tvProductPrice.setText("55$");
        holder.tvQuantity.setText(String.valueOf(currentProduct.getQuanlity()));

        // Sự kiện tăng số lượng
        holder.btnIncrease.setOnClickListener(v -> {
            currentProduct.setQuanlity(currentProduct.getQuanlity() + 1);
            holder.tvQuantity.setText(String.valueOf(currentProduct.getQuanlity()));
            updateTotalPrice();
        });

        // Sự kiện giảm số lượng
        holder.btnDecrease.setOnClickListener(v -> {
            if (currentProduct.getQuanlity() > 1) {
                currentProduct.setQuanlity(currentProduct.getQuanlity() - 1);
                holder.tvQuantity.setText(String.valueOf(currentProduct.getQuanlity()));
                updateTotalPrice();
            }
        });

        // Xóa sản phẩm khỏi giỏ hàng
        holder.btnDelete.setOnClickListener(v -> {
            products.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, products.size());
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
        for (Product p : CartManager.getInstance().getCartItems()) {
            total += 5 * p.getQuanlity();
        }
        tvTotalPrice.setText("Total: $" + total);
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
