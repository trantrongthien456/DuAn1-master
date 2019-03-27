package com.example.johnluu.duan1.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.johnluu.duan1.R;
import com.example.johnluu.duan1.database.ChuongDAO;
import com.example.johnluu.duan1.model.Chuong;

import java.util.ArrayList;

public class ChuongAdapter extends BaseAdapter {

    Context c;
    ArrayList<Chuong> dsschuong = new ArrayList<Chuong>();
    ListView lv;

    public ChuongAdapter(Context c, ArrayList<Chuong> dsschuong, ListView lv) {
        this.c = c;
        this.dsschuong = dsschuong;
        this.lv = lv;


    }

    @Override
    public int getCount() {
        return dsschuong.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater lnf = ((Activity) c).getLayoutInflater();
        view = lnf.inflate(R.layout.one_item_chuong, null);
        TextView tv_chuong_one_item = view.findViewById(R.id.tv_idchuong_one_item);
        TextView tv_tenchuong_one_item = view.findViewById(R.id.tv_tenchuong_one_item);
        ImageView iv_option = view.findViewById(R.id.iv_option_one_item_chuong);


        Chuong chuong = dsschuong.get(i);
        tv_chuong_one_item.setText(chuong._idchuong+"");
        tv_tenchuong_one_item.setText(chuong.tenchuong);

        iv_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMultiPopup(view, i);
            }
        });

        return view;

    }

    public void showMultiPopup(View view, final int position) {
        PopupMenu popup = new PopupMenu(c, view);
        popup.inflate(R.menu.del_edit_function);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem Item) {

                switch (Item.getItemId()) {
                    case R.id.xoa_item:
                        final AlertDialog.Builder dialog = new AlertDialog.Builder(c);
                        dialog.setTitle("Thông báo");
                        dialog.setMessage("B?n có mu?n xóa th? lo?i này không?");
                        dialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Chuong chuong = dsschuong.get(position);
                                int _id = chuong._idchuong;

                                ChuongDAO chuongDAO = new ChuongDAO(c);
                                chuongDAO.XoaChuong(_id);
                                capnhatgiaodien_chuong();


                            }
                        });

                        dialog.setPositiveButton("Huy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();

                            }
                        });
                        dialog.show();
                        break;
                    case R.id.sua_item:
                        final AlertDialog.Builder builder = new AlertDialog.Builder(c);
                        LayoutInflater inf = ((Activity) c).getLayoutInflater();

                        final View view = inf.inflate(R.layout.dialog_sua_ten_chuong, null);
                        builder.setView(view);

                        final AlertDialog a = builder.create();
                        final Chuong chuong = dsschuong.get(position);
                        final TextView tv_id_chuong = view.findViewById(R.id.tv_id);
                        final EditText et_sua_ten_chuong = view.findViewById(R.id.et_dialog_suatenchuong);
                        Button bt_sua_ten_chuong = view.findViewById(R.id.bt_sua_them_chuong);
                        Button bt_huy_chuong = view.findViewById(R.id.bt_huy);
                        Button bt_sua_duongdan = view.findViewById(R.id.bt_sua_dulieu);
                        Button bt_sua_audio = view.findViewById(R.id.bt_sua_audio);
                        TextView tv_sua_duong_dan = view.findViewById(R.id.tv_sua_duongdan);
                        TextView tv_sua_audio = view.findViewById(R.id.tv_sua_audio);

                        tv_id_chuong.setText(chuong._idchuong + "");
                        et_sua_ten_chuong.setText(chuong.tenchuong);


                        bt_sua_ten_chuong.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int _id = Integer.parseInt(tv_id_chuong.getText().toString());
                                String et_ten = et_sua_ten_chuong.getText().toString();

                                ChuongDAO chuongDAO = new ChuongDAO(c);
                                dsschuong = chuongDAO.xemDSChuong();

                                Chuong chuongupdate = new Chuong(_id, et_ten);
                                chuongDAO.suaChuonh(chuongupdate);
                                capnhatgiaodien_chuong();


                            }
                        });


                        bt_huy_chuong.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                a.cancel();
                            }
                        });


                        a.show();

                    default:

                        return true;
                }
                return false;
            }

        });
        popup.show();
    }

    public void capnhatgiaodien_chuong() {
        ChuongDAO chuongDAO = new ChuongDAO(c);
        dsschuong = chuongDAO.xemDSChuong();
        ChuongAdapter chuongAdapter = new ChuongAdapter(c, dsschuong, lv);
        lv.setAdapter(chuongAdapter);
    }

}

