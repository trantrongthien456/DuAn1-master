package com.example.johnluu.duan1;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.johnluu.duan1.adapter.ChuongAdapter;
import com.example.johnluu.duan1.adapter.TheLoaiAdapter;
import com.example.johnluu.duan1.database.ChuongDAO;
import com.example.johnluu.duan1.database.TheLoaiDAO;
import com.example.johnluu.duan1.model.Chuong;
import com.example.johnluu.duan1.model.TheLoai;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChuongFragment extends Fragment {
     Chuong chuongg;
     ChuongDAO chuongDAO;
     ChuongAdapter chuongAdapter;
     RecyclerView rv_chuong;
     ArrayList<Chuong>dschuong;



    public ChuongFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView( final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_chuong, container, false);
        FloatingActionButton fv_chuong = v.findViewById(R.id.fab_themchuong);
        rv_chuong=v.findViewById(R.id.rv_chuong);
        capnhatgiaodien_chuong();

        fv_chuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inf = getLayoutInflater();
                final View vi = inf.inflate(R.layout.dialog_themchuong,null);
                final EditText et_tenchuong = vi.findViewById(R.id.et_themchuong);
                Button bt_them_theloai = vi.findViewById(R.id.bt_themchuong);
                Button bt_huy = vi.findViewById(R.id.bt_huy);

                builder.setView(vi);
                final AlertDialog a = builder.create();

                bt_them_theloai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String Tenchuong = et_tenchuong.getText().toString();

                        if(Tenchuong.isEmpty()){
                            Toast.makeText(getContext(), "Tên Thể Loại không được rỗng!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                           chuongg = new Chuong(Tenchuong);
                           chuongDAO = new ChuongDAO(getContext());
                           chuongDAO.ThemChuong(chuongg);
                            capnhatgiaodien_chuong();
                            a.dismiss();
                        }

                    }
                });

                bt_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        a.dismiss();
                    }
                });

                a.show();
            }
        });

        return v;
    }

    public void capnhatgiaodien_chuong(){
        chuongDAO = new ChuongDAO(getActivity());
        dschuong = chuongDAO.XemChuong();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_chuong.setLayoutManager(layoutManager);
        chuongAdapter= new ChuongAdapter(getActivity(),dschuong);
       rv_chuong.setAdapter(chuongAdapter);
    }

}

