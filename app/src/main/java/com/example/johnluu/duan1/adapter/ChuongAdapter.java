package com.example.johnluu.duan1.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johnluu.duan1.R;
import com.example.johnluu.duan1.database.ChuongDAO;
import com.example.johnluu.duan1.database.TheLoaiDAO;
import com.example.johnluu.duan1.model.Chuong;

import java.util.ArrayList;

public class ChuongAdapter extends RecyclerView.Adapter<ChuongAdapter.ChuongViewHolder>{
    Context c;
    ArrayList<Chuong> dschuong;

    public ChuongAdapter(Context c,ArrayList<Chuong>dschuong){
        this.c=c;
        this.dschuong=dschuong;
    }

    @NonNull
    @Override
    public ChuongAdapter.ChuongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(c);
        View v = inf.inflate(R.layout.one_item_chuong,parent,false);
        return new ChuongViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ChuongAdapter.ChuongViewHolder holder, final int position) {
      holder.tv_idchuong.setText(dschuong.get(position)._idchuong+"");
      holder.tv_chuong.setText(dschuong.get(position).tenchuong);
      holder.iv_option_one_item_chuong.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              showMultiPopup(view,position);

          }
      });
    }

    public void showMultiPopup(View view, final int position) {
        PopupMenu popup = new PopupMenu(c, view);
        popup.inflate(R.menu.del_edit_function);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.xoa_item:
                        AlertDialog.Builder dialog = new AlertDialog.Builder(c);
                        dialog.setTitle("Thông báo");
                        dialog.setMessage("Bạn có muốn xóa thể loại này không?");
                        dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                deleteItem(i);
                                Toast.makeText(c, "Chuong đã được xóa", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                        break;

                    case R.id.sua_item:
//                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(c);
//                    LayoutInflater inf = ((Activity)c).getLayoutInflater();
//                    final View vi = inf.inflate(R.layout.dialog_sua_the_loai,null);
//                    TextView tv_id_themTheLoaidialog = vi.findViewById(R.id.tv_id_themTheLoaidialog);
//                    EditText
//                    builder.setView(vi);
//                    final android.app.AlertDialog a = builder.create();
//
//                    TheLoai tl = dstl.get(position);
//
//
//                    a.show();
                }
                return false;
            }

        });
        popup.show();
    }

  //  @Override
  //  public void onBindViewHolder(@NonNull ChuongAdapter holder, int position) {

  //  }

    @Override
    public int getItemCount() {
        return dschuong.size();
    }
    public class ChuongViewHolder extends RecyclerView.ViewHolder {
        TextView tv_idchuong,tv_chuong;
        ImageView iv_option_one_item_chuong;
        public ChuongViewHolder(View itemView) {
            super(itemView);
            tv_idchuong=itemView.findViewById(R.id.tv_idchuong_one_item);
            tv_chuong=itemView.findViewById(R.id.tv_tenchuong_one_item);
            iv_option_one_item_chuong=itemView.findViewById(R.id.iv_option_one_item_chuong);

        }
    }
    public void deleteItem(int position){
        ChuongDAO chuongDAO = new ChuongDAO(c);
        chuongDAO.xoaChuong(dschuong.get(position)._idchuong);
        notifyItemRemoved(position);
       dschuong = chuongDAO.XemChuong();
    }
}


