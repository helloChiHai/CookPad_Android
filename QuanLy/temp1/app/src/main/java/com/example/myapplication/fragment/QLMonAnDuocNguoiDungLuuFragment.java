package com.example.myapplication.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.database.DatabaseHanler;
import com.example.myapplication.model.ChiTietMonAnDuocLuu;
import com.example.myapplication.model.MonAn;

import java.util.ArrayList;
import java.util.List;


public class QLMonAnDuocNguoiDungLuuFragment extends Fragment {

    private EditText edtIdMonAn, edtIDNguoiDung;

    private Button btn_themMonLuu, btnqQurry;

    // khai báo ListView
    private ListView lv;
    private List<MonAn> monAnList;
    private ArrayAdapter<MonAn> monAnArrayAdapter;

    private DatabaseHanler databaseHanler;

    private void xl_them(){
        btn_themMonLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idMonAn = edtIdMonAn.getText().toString().trim();
                String idNguoiDung = edtIDNguoiDung.getText().toString();

                if(!ktra_thongTinDauVao(idMonAn, idNguoiDung)){
                    Toast.makeText(getActivity(), "yêu cầu nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean userExists = databaseHanler.checkUserExists(idNguoiDung);
                    boolean monAnExists = databaseHanler.checkMonAnExists(idMonAn);
                    if(!userExists || !monAnExists){
                        Toast.makeText(getContext(), "Không có người dùng hoặc món ăn trong hệ thống", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String stateInsert = "";

                        ChiTietMonAnDuocLuu chiTietMonAnDuocLuu = new ChiTietMonAnDuocLuu(idMonAn, idNguoiDung);

                        if(!databaseHanler.them_MonAnVaoChiTietBoiNguoiDung(chiTietMonAnDuocLuu)){
                            stateInsert = "thêm thất bại";
                            Toast.makeText(getActivity(), stateInsert, Toast.LENGTH_SHORT).show();
                        }
                        else{
                            stateInsert = "thêm thành công";
                            Toast.makeText(getActivity(), stateInsert, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    public void anhXa(View view){
        edtIdMonAn = view.findViewById(R.id.fgMonLuu_edt_id_monAn);
        edtIDNguoiDung = view.findViewById(R.id.fgMonLuu_edt_id_nguoiDung);

        btn_themMonLuu = view.findViewById(R.id.fgMonluu_btn_LuuMon);
        btnqQurry = view.findViewById(R.id.fgMonluu_btn_Querry);
    }

    public void xl_queryMonAn(){
        btnqQurry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<MonAn> monAns = databaseHanler.getCacMonAnDaLuu(edtIDNguoiDung.getText().toString());

                monAnArrayAdapter.clear();

                monAnArrayAdapter.addAll(monAns);
            }
        });
    }

    private boolean ktra_thongTinDauVao(String idNguoiDung, String idMon){
        // neu rong -> tra ve false
        // khong rong -> tra ve true
        if(idNguoiDung.isEmpty() ||
                idMon.isEmpty())
            return false;
        return true;
    }

    public QLMonAnDuocNguoiDungLuuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_q_l_mon_an_duoc_nguoi_dung_luu, container, false);

        anhXa(view);

        databaseHanler = new DatabaseHanler(getContext());

        // Tạo ListView
        lv = view.findViewById(R.id.lv_dsMonAnDuocNguoiDungLuu);
        monAnList = new ArrayList<>();

        monAnArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);

        // set adapter
        lv.setAdapter(monAnArrayAdapter);

        xl_queryMonAn();

        xl_them();

        return view;
    }
}