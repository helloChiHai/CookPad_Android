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
import com.example.myapplication.model.NguoiDung;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private ArrayList<NguoiDung> userList;
    private Context context;
    private LayoutInflater mLayoutInflater;

    public UserAdapter(Context context, ArrayList<NguoiDung> userList) {
        this.context = context;
        this.userList = userList;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NguoiDung user = userList.get(position);

        // Hiển thị thông tin người dùng trong ViewHolder
        holder.nameTextView.setText(user.getTen_user());
        // Set các thuộc tính khác tương tự

        // Load hình ảnh (sử dụng thư viện Picasso, Glide hoặc bất kỳ thư viện tải ảnh nào khác)
        Picasso.get().load(user.getHinhAnh_user()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        // Khai báo các View khác tương tự

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            // Ánh xạ các View khác tương tự
        }
    }
}
