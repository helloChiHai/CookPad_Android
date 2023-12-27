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
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.database.DatabaseHanler;
import com.example.myapplication.model.NguoiDung;

import java.util.List;

public class QLDanhSachNguoiDungFragment extends Fragment {
    public QLDanhSachNguoiDungFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private ListView fragment_ds_nd_lv;
    private ArrayAdapter<NguoiDung> fragment_ds_nd_myadapter;

    List<NguoiDung> nguoiDungList;

    private DatabaseHanler fragment_ds_nd_mydatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_q_l_danh_sach_nguoi_dung, container, false);

        fragment_ds_nd_mydatabase = new DatabaseHanler(getContext());

        // Tạo ListView
        fragment_ds_nd_lv = view.findViewById(R.id.lv_dsNguoiDung);
        fragment_ds_nd_myadapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);

        // set adapter
        fragment_ds_nd_lv.setAdapter(fragment_ds_nd_myadapter);

        nguoiDungList = fragment_ds_nd_mydatabase.getAllNguoiDungs();

        fragment_ds_nd_myadapter.clear();
        fragment_ds_nd_myadapter.addAll(nguoiDungList);


        // Xử lý sự kiện khi nhấn vào một phần tử trong ListView
        fragment_ds_nd_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NguoiDung nguoiDungClick = nguoiDungList.get(position);
                showPopupMenu(view, nguoiDungClick);
            }
        });

        return view;
    }

    private void showPopupMenu(View view, NguoiDung selectedUser) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.user_options_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.menu_chiTietNguoiDung) {
                    // Chuyển qua Fragment Xóa và truyền thông tin người dùng
                    ChiTietNguoiDungFragment chiTietNguoiDungFragment = new ChiTietNguoiDungFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("id", selectedUser.getId_user());
                    bundle.putString("ten", selectedUser.getTen_user());
                    bundle.putString("hinhAnh", selectedUser.getHinhAnh_user());
                    bundle.putString("taiKhoan", selectedUser.getTaiKhoan_user());
                    bundle.putString("matKhau", selectedUser.hashPassUser());
                    bundle.putString("email", selectedUser.getEmail_user());
                    bundle.putInt("maQuyen", selectedUser.getMaQuyen_user());

                    chiTietNguoiDungFragment.setArguments(bundle);

                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, chiTietNguoiDungFragment);
                    transaction.addToBackStack("ChiTietNguoiDungFragment");
                    transaction.commit();
                    return true;
                }
                return false;
            }
        });

        popupMenu.show();
    }
}