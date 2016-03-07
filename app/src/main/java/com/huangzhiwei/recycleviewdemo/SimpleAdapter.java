package com.huangzhiwei.recycleviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzhiwei on 16/3/4.
 */
public class SimpleAdapter extends RecyclerView.Adapter<MyViewHolder> {


    private LayoutInflater mInflater;
    private Context mContext;
    private List<String> mDatas;

    public interface OnItenClickListener
    {
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    private OnItenClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItenClickListener listener)
    {
        mOnItemClickListener = listener;
    }
    private List<Integer> mHeights;

    public SimpleAdapter(Context context, List<String> datas) {
        this.mContext = context;
        this.mDatas = datas;
        this.mInflater = LayoutInflater.from(context);
        mHeights = new ArrayList<Integer>();
        for (int i = 0; i < mDatas.size(); i++) {
            mHeights.add((int) (100+Math.random()*300));
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_single_textview,parent,false);

        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
        lp.height = mHeights.get(position);
        holder.itemView.setLayoutParams(lp);
        holder.tv.setText(mDatas.get(position));

        if(mOnItemClickListener!=null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPosition = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView,layoutPosition);

                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int layoutPosition = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView,layoutPosition);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addData(int pos)
    {
        mDatas.add(pos,"Insert One");
        notifyItemInserted(pos);
    }

    public void deleteData(int pos)
    {
        mDatas.remove(pos);
        notifyItemRemoved(pos);
    }


}

class MyViewHolder extends RecyclerView.ViewHolder
{
    TextView tv;
    public MyViewHolder(View itemView) {
        super(itemView);

        tv = (TextView) itemView.findViewById(R.id.id_tv);
    }
}

