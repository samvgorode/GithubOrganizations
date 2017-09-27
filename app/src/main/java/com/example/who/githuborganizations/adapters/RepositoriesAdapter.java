package com.example.who.githuborganizations.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.who.githuborganizations.R;
import com.example.who.githuborganizations.pojo.Repository;

import com.example.who.githuborganizations.views.RepositoriesItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by who on 27.09.2017.
 */

public class RepositoriesAdapter extends RecyclerView.Adapter<RepositoriesAdapter.MyViewHolder> {
    private Context context;
    private List<Repository> data;

    public RepositoriesAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Repository> datas) {
        data = new ArrayList<>();
        if(datas == null || datas.size()==0)
            return;
        if (data != null && data.size()>0)
            data.clear();
        for(int i=0; i<datas.size(); i++){
            data.add(i, datas.get(i));
        }
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RepositoriesItemView itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = (RepositoriesItemView) view;
        }
    }

    @Override
    public void onBindViewHolder(RepositoriesAdapter.MyViewHolder holder, int position) {
        updateView(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public RepositoriesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RepositoriesItemView itemView = (RepositoriesItemView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.org_item, parent, false);
        return new RepositoriesAdapter.MyViewHolder(itemView);
    }

    private void updateView(RepositoriesItemView itemView, int position) {
        Repository model = getItem(position);
        itemView.setItem(model);
    }

    public Repository getItem(int position) {
        return data.get(position);
    }
}
