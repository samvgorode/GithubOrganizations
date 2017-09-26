package com.example.who.githuborganizations.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.who.githuborganizations.R;
import com.example.who.githuborganizations.pojo.Organization;
import com.example.who.githuborganizations.views.OrganizationItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by who on 26.09.2017.
 */

public class OrganizationsAdapter extends RecyclerView.Adapter<OrganizationsAdapter.MyViewHolder> {
    private Context context;
    private List<Organization> data = new ArrayList<>();

    public OrganizationsAdapter(Context context, List<Organization> data) {
        this.context = context;
        this.data = data;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        OrganizationItemView itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = (OrganizationItemView) view;
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        updateView(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OrganizationItemView itemView = (OrganizationItemView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.org_item, parent, false);
        return new MyViewHolder(itemView);
    }

    private void updateView(OrganizationItemView itemView, int position) {
        Organization model = getItem(position);
        itemView.setItem(model);
    }

    public Organization getItem(int position) {
        return data.get(position);
    }
}
