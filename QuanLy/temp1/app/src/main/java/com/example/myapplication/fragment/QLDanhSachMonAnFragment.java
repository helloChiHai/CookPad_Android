package com.example.myapplication.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.example.myapplication.R;
import com.example.myapplication.database.DatabaseHanler;
import com.example.myapplication.model.MonAn;
import com.example.myapplication.model.NguoiDung;

import java.util.List;

public class QLDanhSachMonAnFragment extends Fragment {

    private ListView fragment_ds_ma_lv;
    private ArrayAdapter<MonAn> fragment_ds_ma_myadapter;

    private List<MonAn> monAnList;

    private DatabaseHanler databaseHanler;

    public QLDanhSachMonAnFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_q_l_danh_sach_mon_an, container, false);

        databaseHanler = new DatabaseHanler(getContext());

        // Tạo ListView
        fragment_ds_ma_lv = view.findViewById(R.id.lv_ql_dsMonAn);
        fragment_ds_ma_myadapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);

        // set adapter
        fragment_ds_ma_lv.setAdapter(fragment_ds_ma_myadapter);

        monAnList = databaseHanler.getAllMonAns();

        fragment_ds_ma_myadapter.clear();
        fragment_ds_ma_myadapter.addAll(monAnList);


        fragment_ds_ma_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MonAn monAnClick = monAnList.get(position);
                showPopupMenu(view, monAnClick);
            }
        });
        return view;
    }

    private void showPopupMenu(View view, MonAn selectedFood) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.food_options_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.menu_chiTietMonAn) {
                    // Chuyển qua Fragment Xóa và truyền thông tin người dùng
                    ChiTietMonAnFragment chiTietMonAnFragment = new ChiTietMonAnFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("idMA", selectedFood.getId_MA());
                    bundle.putString("idUS", selectedFood.getId_User());
                    bundle.putString("tenMA", selectedFood.getTen_MA());
                    bundle.putString("hinhAnhMA", selectedFood.getHinhAnh_MA());
                    bundle.putString("loaiMA", selectedFood.getLoai_MA());
                    bundle.putString("huongDanMA", selectedFood.getHuongDanNau_MA());
                    bundle.putString("nguyenLieuMA", selectedFood.getNguyenLieu_MA());
                    bundle.putString("thoiGianNauMA", selectedFood.getThoiGianNau_MA());
                    bundle.putString("dungCuMA", selectedFood.getDungCu_MA());

                    chiTietMonAnFragment.setArguments(bundle);

                    FragmentTransaction transactionCTMonAnh = getParentFragmentManager().beginTransaction();
                    transactionCTMonAnh.replace(R.id.fragment_container, chiTietMonAnFragment);
                    transactionCTMonAnh.addToBackStack("ChiTietMonAnFragment");
                    transactionCTMonAnh.commit();
                    return true;
                }
                return false;
            }
        });

        popupMenu.show();
    }
}