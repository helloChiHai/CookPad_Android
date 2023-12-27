package com.example.myapplication.adapter;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.model.MonAn;
import com.example.myapplication.model.MonAn_1;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MonAnAdapter_2 extends RecyclerView.Adapter<MonAnViewHolder> {
    private List<MonAn> monans;
    private Context context;
    private LayoutInflater mLayoutInflater;

    private ItemClickMonAnListener itemClickListener;

    public MonAnAdapter_2(Context context, List<MonAn> monAnList, MonAnAdapter_2.ItemClickMonAnListener itemClickListener) {
        this.context = context;
        this.monans = monAnList;
        this.itemClickListener = itemClickListener;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public interface ItemClickMonAnListener {
        void onItemClickFood(MonAn food);
    }

    @Override
    public MonAnViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate view from recyclerview_item_layout.xml
        View recyclerViewItem = mLayoutInflater.inflate(R.layout.layout_monan_2, parent, false);

//        recyclerViewItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                handleRecyclerItemClick( (RecyclerView)parent, v);
//            }
//        });
        return new MonAnViewHolder(recyclerViewItem);
    }


    @Override
    public void onBindViewHolder(@NonNull MonAnViewHolder holder, int position) {
        MonAn monAn = monans.get(position);

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
        return this.monans.size();
    }

//    private void handleRecyclerItemClick(RecyclerView recyclerView, View itemView) {
//        int itemPosition = recyclerView.getChildLayoutPosition(itemView);
//        MonAn monan  = this.monans.get(itemPosition);
//
//        Toast.makeText(this.context, monan.getTen_MA(), Toast.LENGTH_LONG).show();
//    }
    public class MonViewHolderforMonAn2 extends RecyclerView.ViewHolder {
        TextView tenMonTextView;
        ImageView img_hinhMon;

        public MonViewHolderforMonAn2(@NonNull View itemView) {
            super(itemView);
            this.tenMonTextView = itemView.findViewById(R.id.txt_tenMon);
            this.img_hinhMon = itemView.findViewById(R.id.img_hinhMonAn);
        }
    }
}
