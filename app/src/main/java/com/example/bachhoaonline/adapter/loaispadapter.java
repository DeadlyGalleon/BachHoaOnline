package com.example.bachhoaonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.*;
import com.bumptech.glide.Glide;
import com.example.bachhoaonline.R;
import com.example.bachhoaonline.model.loai;

import java.util.List;

public class loaispadapter extends BaseAdapter {
    List<loai> array;
    Context context;

    public loaispadapter( Context context, List<loai> array) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class ViewHolder{
        TextView tenloai;
        ImageView hianhanhloai;

    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_sanpham, null);
            viewHolder.tenloai = view.findViewById(R.id.tenloai);
            viewHolder.hianhanhloai = view.findViewById(R.id.loai_image);

            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
            viewHolder.tenloai.setText(array.get(position).getTenloai());
            Glide.with(context).load(array.get(position).getHinhanh()).into(viewHolder.hianhanhloai);

        }


        return view;
    }
}
