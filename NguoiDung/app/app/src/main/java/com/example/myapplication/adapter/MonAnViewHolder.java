package com.example.myapplication.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.MonAn;
import com.squareup.picasso.Picasso;

public class MonAnViewHolder extends RecyclerView.ViewHolder{
    ImageView hinhMonAn;
    TextView tenMonAn;
    Button btn_luuMon;

    public void bindData(MonAn food, MonAnAdapter_5.ItemClickFoodListener listener) {
        Picasso.get().load(food.getHinhAnh_MA()).into(hinhMonAn);
        tenMonAn.setText(food.getTen_MA());

        // Xử lý sự kiện click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClickFood(food);
                }
            }
        });
    }

    public MonAnViewHolder(@NonNull View itemView) {
        super(itemView);

        this.hinhMonAn = (ImageView) itemView.findViewById(R.id.img_hinhMonAn);
        this.tenMonAn = (TextView) itemView.findViewById(R.id.txt_tenMon);
        this.btn_luuMon = (Button) itemView.findViewById(R.id.button);
    }
}
