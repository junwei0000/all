package com.longcheng.lifecareplan.modular.mine.emergencys;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.VHolder> {

    private int[] num;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private int pos;

    public GridAdapter(int[] num, Context context) {
        this.num = num;
        this.context = context;
    }


    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.grid_item, parent, false);
        VHolder holder = new VHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VHolder holder, int position) {
        holder.tv_number.setTextColor(context.getResources().getColor(R.color.black));
        holder.tv_number.setBackground(context.getResources().getDrawable(R.drawable.shape_corner11));

        if (pos == position) {
            holder.tv_number.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_number.setBackground(context.getResources().getDrawable(R.drawable.shape_corner2));
        } else {
            holder.tv_number.setTextColor(context.getResources().getColor(R.color.black));
            holder.tv_number.setBackground(context.getResources().getDrawable(R.drawable.shape_corner11));


        }
        holder.tv_number.setText(num[position] + "");
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(position);
                }
            });


        }

    }

    @Override
    public int getItemCount() {
        return num.length;
    }

    public void setData(int pos) {
        this.pos = pos;
        notifyDataSetChanged();
    }

    class VHolder extends RecyclerView.ViewHolder {
        private TextView tv_number;

        public VHolder(View itemView) {
            super(itemView);
            tv_number = itemView.findViewById(R.id.tv_number);
        }


    }


    public interface OnItemClickListener {
        void onClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}