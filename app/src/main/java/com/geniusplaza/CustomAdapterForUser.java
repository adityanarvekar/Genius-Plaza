package com.geniusplaza;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.List;
import java.util.Map;

public class CustomAdapterForUser extends BaseAdapter {

    private Context context;
    private List<Map<String, String>> prolist;
    String compname = "";
    String indCode = "";

    public CustomAdapterForUser(Context context, List<Map<String, String>> data) {
        this.prolist = data;
        this.context = context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return prolist.get(position);
    }

    @Override
    public int getCount() {
        return prolist.size();
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        view = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.usertemplate, null);
            holder.avatar = (ImageView) view.findViewById(R.id.avatar);
            holder.email = (TextView) view.findViewById(R.id.email);
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.email.setText(prolist.get(position).get("A"));
        holder.name.setText(prolist.get(position).get("B") + " " + prolist.get(position).get("C"));
        Ion.with(context).load(prolist.get(position).get("D")).withBitmap().intoImageView(holder.avatar);
        return view;

    }

    class ViewHolder {
        TextView email, name;
        ImageView avatar;
    }
}
