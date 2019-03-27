package com.example.johnluu.duan1;


import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johnluu.duan1.adapter.ChuongAdapter;
import com.example.johnluu.duan1.database.ChuongDAO;
import com.example.johnluu.duan1.model.Chuong;
import com.example.johnluu.duan1.model.Sach;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChuongFragment extends Fragment {
    Chuong ch;
    ChuongDAO chuongDAO;
    ChuongAdapter chuongAdapter;
    ListView lv_chuong;
    ArrayList<Chuong> dsschuong;
    ArrayList<Sach>dssach = new ArrayList<Sach>();
    TextView tv_duongdan, tv_audio;

    public ChuongFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chuong, container, false);

        FloatingActionButton fab_chuong = v.findViewById(R.id.fab_themchuong);
        lv_chuong = v.findViewById(R.id.lv_chuong);


        fab_chuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inf = getLayoutInflater();
                final View vi = inf.inflate(R.layout.dialog_themchuong, null);
                final EditText et_tenchuong = vi.findViewById(R.id.et_tenchuong);
                Button bt_them_chuong = vi.findViewById(R.id.bt_them_chuong);
                Button bt_huy = vi.findViewById(R.id.bt_huy);
                Button bt_duongdan = vi.findViewById(R.id.bt_dulieu);
                Button bt_audio = vi.findViewById(R.id.bt_audio);
                tv_duongdan = vi.findViewById(R.id.tv_duongdan);
                tv_audio = vi.findViewById(R.id.tv_audio);
              final   Spinner sp = vi.findViewById(R.id.sp_sach);



                builder.setView(vi);
                final AlertDialog a = builder.create();

                dssach = new ArrayList<Sach>();
                dssach.add(new Sach(1,1,1,"Bao cong",""));
                dssach.add(new Sach(2,1,1,"Thieu lam tu",""));
                dssach.add(new Sach(3,1,1,"sach lon",""));


                ArrayAdapter adapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,dssach);
                sp.setAdapter(adapter);




                bt_audio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        browaudio();
                    }
                });


                bt_duongdan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        browSach();
                    }
                });


                bt_them_chuong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int indexsach = sp.getSelectedItemPosition();
                        int _idsach = dssach.get(indexsach)._idsach;


                        String tenchuong = et_tenchuong.getText().toString();
                        String duongdan = tv_duongdan.getText().toString();
                        String audio =  tv_audio.getText().toString();


                        if (tenchuong.isEmpty()) {
                            Toast.makeText(getContext(), "Tên Chuong không du?c r?ng!", Toast.LENGTH_SHORT).show();
                        } else {
                            ch = new Chuong(_idsach,tenchuong,duongdan,audio);
                            chuongDAO = new ChuongDAO(getContext());
                            chuongDAO.ThemChuong(ch);
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


    final int PICKFILE_RESULT_CODE_SACH = 1;
    final int PICKFILE_RESULT_CODE_AUDIO = 2;

    public void browSach() {
        Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
        fileintent.setType("*/*");
        try {
            startActivityForResult(fileintent, PICKFILE_RESULT_CODE_SACH);
        } catch (ActivityNotFoundException e) {
            Log.e("tag", "No activity can handle picking a file. Showing alternatives.");
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Fix no activity available
        if (data == null)
            return;
        switch (requestCode) {
            case PICKFILE_RESULT_CODE_SACH:
                if (resultCode == RESULT_OK) {
                    String FilePath = data.getData().getPath();

                    //FilePath is your file as a string
//                    tv_audio.setText(FilePath);
                    tv_duongdan.setText(FilePath);
                    Log.i("dulieu", "onActivityResult: sach " + tv_duongdan);
                    // Toast.makeText(getContext(), FilePath+"", Toast.LENGTH_SHORT).show();
                }
                break;
            case PICKFILE_RESULT_CODE_AUDIO:
                if (resultCode == RESULT_OK) {
                    String FilePath = data.getData().getPath();

                    //FilePath is your file as a string
                    Log.i("dulieu", "onActivityResult: audio" + tv_duongdan);
                    tv_audio.setText(FilePath);
                    //    tv_duongdan.setText(FilePath);
                    // Toast.makeText(getContext(), FilePath+"", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    public void browaudio() {
        Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
        fileintent.setType("*/*");
        try {
            startActivityForResult(fileintent, PICKFILE_RESULT_CODE_AUDIO);
        } catch (ActivityNotFoundException e) {
            Log.e("tag", "No activity can handle picking a file. Showing alternatives.");
        }
    }

    public void capnhatgiaodien_chuong(){
        chuongDAO = new ChuongDAO(getActivity());
        dsschuong=chuongDAO.xemDSChuong();
        chuongAdapter  = new ChuongAdapter(getActivity(),dsschuong,lv_chuong);
        lv_chuong.setAdapter(chuongAdapter);
    }


}




