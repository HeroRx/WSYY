package com.gzucm.wsyy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gzucm.wsyy.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * DayDoctorAdapter
 * Created by Administrator on 2017/11/21 0021.
 */
public class DateAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private List<String> mDateList = new ArrayList<>();
    private List<String> mDayList = new ArrayList<>();

    private int mPosition;
    private static int TYPE_NORMAL = 101;
    private static int TYPE_SELECT = 102;


    public DateAdapter(Context context,List<String> DateList,List<String> DayList) {
        mContext = context;
        mPosition = 0;
        mDateList = DateList;
        mDayList = DayList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_date, parent, false);
        return new ItemViewHolder(view, mItemClickListener);
//        if (viewType == TYPE_SELECT) {
//            final View view = LayoutInflater.from(mContext).inflate(R.layout.item_date, parent, false);
//            return new ItemViewSelectHolder(view, mItemClickListener);
//        } else {
//            final View view = LayoutInflater.from(mContext).inflate(R.layout.item_date, parent, false);
//            return new ItemViewHolder(view, mItemClickListener);
//        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder item = (ItemViewHolder) holder;
        item.date.setText(mDateList.get(position) + "   " + mDayList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDayList.size();
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

        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.ll)
        LinearLayout ll;

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

            date.setTextColor(mContext.getResources().getColor(R.color.main_gray));
            ll.setBackgroundResource(R.color.gray);
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
