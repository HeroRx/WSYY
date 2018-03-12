package com.gzucm.wsyy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gzucm.wsyy.R;
import com.gzucm.wsyy.utils.Dateutil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * DayDoctorAdapter
 * Created by Administrator on 2017/11/21 0021.
 */

public class WeekDayAdapter extends RecyclerView.Adapter<WeekDayAdapter.ViewHolder> {

    private List<String> mDates;
    private List<String> mDatehaos;
    private Context mContext;
    private ImageLoader imageLoader;

    public WeekDayAdapter(Context context, List<String> dates, List<String> datehaos) {
        this.mDates = dates;
        this.mDatehaos = datehaos;
        this.mContext = context;
    }
    public WeekDayAdapter(List<String> dates, List<String> datehaos) {
        this.mDates = dates;
        this.mDatehaos = datehaos;
    }

    /**
     * 判断是否为四点后
     */
    private boolean isInTime() {
        return Dateutil.isCurrentInTimeScope(16,0,0,0);
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_doctor_hao,viewGroup,false);
        ViewHolder vh = new ViewHolder(view,mItemClickListener);
        return vh;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if(position == 0 && isInTime()){
            viewHolder.ll.setBackgroundResource(R.color.main_gray);
            viewHolder.ll.setOnClickListener(null);
            viewHolder.chaoshi.setVisibility(View.VISIBLE);
        }
        viewHolder.date.setText(mDates.get(position));
        viewHolder.datehao.setText(mDatehaos.get(position));

    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return mDatehaos.size();
    }



    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MyItemClickListener mListener;
        public TextView date;
        public TextView datehao;
        public TextView chaoshi;
        LinearLayout ll;
        public ViewHolder(View view,MyItemClickListener listener){
            super(view);
            this.mListener = listener;
            view.setOnClickListener(this);
            date = (TextView) view.findViewById(R.id.date);
            datehao = (TextView) view.findViewById(R.id.date_hao);
            chaoshi = (TextView) view.findViewById(R.id.chaoshi);
            ll = (LinearLayout)view.findViewById(R.id.ll);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getLayoutPosition());
            }
        }

    }

    public interface MyItemClickListener {
        void onItemClick(View view, int postion);
    }
    private MyItemClickListener mItemClickListener = null;
    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }
}