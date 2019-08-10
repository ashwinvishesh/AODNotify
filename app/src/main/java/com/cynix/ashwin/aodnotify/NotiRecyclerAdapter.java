package com.cynix.ashwin.aodnotify;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Collections;
import java.util.List;

/**
 * Created by ashwi on 30-10-2017.
 */

public class NotiRecyclerAdapter extends RecyclerView.Adapter<NotiRecyclerAdapter.MyViewHolder>
{

    private LayoutInflater inflater;
    List<NotificationItemInformation> data = Collections.emptyList();

    NotiRecyclerAdapter(Context context, List<NotificationItemInformation> data)
    {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }


    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer)
    {
        super.registerAdapterDataObserver(observer);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.custom_recyclerview_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        NotificationItemInformation current = data.get(position);
        holder.recyclerItemIcon.setImageDrawable(current.notiIcon);
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView recyclerItemIcon;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            recyclerItemIcon = itemView.findViewById(R.id.notiRecyclerItemIcon);
        }
    }

}
