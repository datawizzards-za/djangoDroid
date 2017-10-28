package com.example.docto.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
    ArrayList<String> list = new ArrayList<>();
    Context context;

    public UserAdapter(Context context, ArrayList<String> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int _id) {
        return _id;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.users_view, parent, false);
        }
        TextView txtName = (TextView) view.findViewById(R.id.txtName);
        TextView txtSurname = (TextView) view.findViewById(R.id.txtSurname);
        TextView txtAddress = (TextView) view.findViewById(R.id.txtAddress);

        String object = getItem(position);
        String[] _data = object.split("#");

        object = "Name: "+_data[0];
        txtName.setText(object);

        object = "Surname: "+_data[1];
        txtSurname.setText(object);

        object = "Address: "+_data[2];
        txtAddress.setText(object);

        return view;
    }
}
