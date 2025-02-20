package com.example.projectprm392.java;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectprm392.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter extends BaseAdapter {

    private Context context;
    private List<Product> list;

    public HomeAdapter(Context context, List<Product> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.itemviewhome, parent, false);
            holder = new ViewHolder();

            // can tao layout
            holder.img = convertView.findViewById(R.id.itemview_img);
            holder.tvMa = convertView.findViewById(R.id.itemview_tvMasp);
            holder.tvTen = convertView.findViewById(R.id.itemview_tvTensp);
            holder.tvSl = convertView.findViewById(R.id.itemview_tvSl);
//            holder.btSua = convertView.findViewById(R.id.itemview_btnView);
//            holder.btXoa = convertView.findViewById(R.id.itemview_btnAddToCart);

            convertView.setTag(holder);

        }else {
            holder =(ViewHolder) convertView.getTag();
        }

        //gan du lieu cho View
        Product p = list.get(i);
        if(p!=null){
            Picasso.get().load(p.getSearch_image()).into(holder.img);
            holder.tvMa.setText(p.getId());
            holder.tvTen.setText(p.getName());
            holder.tvSl.setText(String.valueOf(p.getQuanlity()));
        }

        convertView.setOnClickListener(v->{
            Product product = list.get(i);
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("Product",  product);
            context.startActivity(intent);
        });
        return convertView;
    }

    static class ViewHolder{
        ImageView img;
        TextView tvMa, tvTen, tvSl;
        Button btSua, btXoa;

    }
}
