package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.MonAn;
import com.example.myapplication.model.MonAn_1;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MonAnAdapter_5 extends RecyclerView.Adapter<MonAnViewHolder> {
    private Context context;
    private List<MonAn> monAnList;
    private ItemClickFoodListener itemClickListener;

    public MonAnAdapter_5(Context context, List<MonAn> monAnList, ItemClickFoodListener itemClickListener) {
        this.context = context;
        this.monAnList = monAnList;
        this.itemClickListener = itemClickListener;
    }


    public interface ItemClickFoodListener {
        void onItemClickFood(MonAn food);
    }

    @NonNull
    @Override
    public MonAnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_monan_1, parent, false);
        return new MonAnViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonAnViewHolder holder, int position) {
        MonAn monAn = monAnList.get(position);

        // Hiển thị thông tin món ăn
        holder.tenMonAn.setText(monAn.getTen_MA());
        Picasso.get().load(monAn.getHinhAnh_MA()).into(holder.hinhMonAn);

        // Xử lý sự kiện click vào món ăn
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClickFood(monAn);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return monAnList.size();
    }
}