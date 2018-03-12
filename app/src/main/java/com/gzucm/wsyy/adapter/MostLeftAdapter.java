package com.gzucm.wsyy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gzucm.wsyy.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class MostLeftAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private List<String> mList = new ArrayList<>();

    private int mPosition;
    private static int TYPE_NORMAL = 101;
    private static int TYPE_SELECT = 102;


    public MostLeftAdapter(Context context,List<String> List) {
        mContext = context;
        mPosition = 0;
        mList = List;
    }





    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_SELECT) {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.item_left, parent, false);
            return new ItemViewSelectHolder(view, mItemClickListener);
        } else {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.item_left, parent, false);
            return new ItemViewHolder(view, mItemClickListener);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder item = (ItemViewHolder) holder;
        item.mostLeftText.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mPosition) {
            return TYPE_SELECT;
        } else {
            return TYPE_NORMAL;
        }
    }

    public void setPosition(int position) {
        mPosition = position;
    }



    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MyItemClickListener mListener;

        @BindView(R.id.left_b)
        TextView mostLeftText;
        @BindView(R.id.view)
        TextView mview;

        public ItemViewHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            this.mListener = listener;
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getLayoutPosition());
            }
        }
    }

    private class ItemViewSelectHolder extends ItemViewHolder {
        public ItemViewSelectHolder(View view, MyItemClickListener listener) {
            super(view, listener);

            mostLeftText.setTextColor(mContext.getResources().getColor(R.color.red));
            mview.setVisibility(View.INVISIBLE);
        }
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }
    public interface MyItemClickListener {
        void onItemClick(View view, int postion);
    }
    private MyItemClickListener mItemClickListener;
}
