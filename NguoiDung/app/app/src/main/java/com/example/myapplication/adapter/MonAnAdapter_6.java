package com.example.myapplication.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.DatabaseHelper;
import com.example.myapplication.fragment.ChiTietMonAnFragment;
import com.example.myapplication.model.MonAn;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MonAnAdapter_6 extends RecyclerView.Adapter<MonAnAdapter_6.MonViewHolder> {
    private Context context;
    private List<MonAn> monAnList;
    private OnItemClickListener onItemClickListener;

    public MonAnAdapter_6(Context context, List<MonAn> monAnList) {
        this.context = context;
        this.monAnList = monAnList;
    }

    public interface OnItemClickListener {
        void onItemClick(MonAn food);
        void onLuuButtonClick(MonAn food);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public MonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_monan_1, parent, false);
        return new MonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonViewHolder holder, int position) {
        MonAn monAn = monAnList.get(position);
        holder.tenMonTextView.setText(monAn.getTen_MA());
        Picasso.get().load(monAn.getHinhAnh_MA()).into(holder.hinhMonAn);

        // Xử lý sự kiện click vào món ăn
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(monAn);
                }
            }
        });

//        holder.tenMonTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (onItemClickListener != null) {
//                    onItemClickListener.onItemClick(monAn);
//                }
//            }
//        });

        holder.luuMonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onLuuButtonClick(monAn);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return monAnList.size();
    }

    public class MonViewHolder extends RecyclerView.ViewHolder {
        ImageView hinhMonAn;
        TextView tenMonTextView;
        Button luuMonButton;

        public MonViewHolder(@NonNull View itemView) {
            super(itemView);
            this.hinhMonAn = (ImageView) itemView.findViewById(R.id.img_hinhMonAn);
            tenMonTextView = itemView.findViewById(R.id.txt_tenMon);
            luuMonButton = itemView.findViewById(R.id.button);
        }
    }
}