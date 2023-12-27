package com.example.myapplication.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.model.MonAn;
import com.example.myapplication.model.MonAn_1;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class MonAnAdapter extends RecyclerView.Adapter<MonAnViewHolder> {
    private List<MonAn> monans;
    private Context context;
    private LayoutInflater mLayoutInflater;

    public MonAnAdapter(Context context, List<MonAn> datas ) {
        this.context = context;
        this.monans = datas;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MonAnViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View recyclerViewItem = mLayoutInflater.inflate(R.layout.layout_monan_1, parent, false);

        recyclerViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRecyclerItemClick( (RecyclerView)parent, v);
            }
        });
        return new MonAnViewHolder(recyclerViewItem);
    }


    @Override
    public void onBindViewHolder(MonAnViewHolder holder, int position) {
        MonAn monan = this.monans.get(position);

        Picasso.get().load(monan.getHinhAnh_MA()).into(holder.hinhMonAn);
        holder.tenMonAn.setText(monan.getTen_MA());
    }

    @Override
    public int getItemCount() {
        return this.monans.size();
    }

    public int getDrawableResIdByName(String resName)  {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName , "drawable", pkgName);
        Log.i(MainActivity.LOG_TAG, "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }

    // Click on RecyclerView Item.
    private void handleRecyclerItemClick(RecyclerView recyclerView, View itemView) {
        int itemPosition = recyclerView.getChildLayoutPosition(itemView);
        MonAn monan  = this.monans.get(itemPosition);

        Toast.makeText(this.context, monan.getTen_MA(), Toast.LENGTH_LONG).show();
    }
}
