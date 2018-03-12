package com.gzucm.wsyy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gzucm.wsyy.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * DayDoctorAdapter
 * Created by Administrator on 2017/11/21 0021.
 */

public class TimeDuanAdapter extends RecyclerView.Adapter<TimeDuanAdapter.ViewHolder> {

    private List<String> murls;
    private List<String> mmoneys;
    private List<Integer> mcounts;
    private Context mContext;
    private ImageLoader imageLoader;

    public TimeDuanAdapter(Context context, List<String> urls, List<String> moneys,List<Integer> counts) {
        this.mmoneys = moneys;
        this.murls = urls;
        this.mContext = context;
        mcounts = counts;
        imageLoader=ImageLoader.getInstance();
    }
    public TimeDuanAdapter(List<String> urls, List<String> moneys) {
        this.mmoneys = moneys;
        this.murls = urls;
        imageLoader=ImageLoader.getInstance();
    }



    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_timeduan,viewGroup,false);
        ViewHolder vh = new ViewHolder(view,mItemClickListener);
        return vh;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.money.setText(mmoneys.get(position));

        final String url = murls.get(position);

        //创建DisplayImageOptions对象并进行相关选项配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.icon_stub)// 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.icon_empty)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.icon_x)// 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .build();// 创建DisplayImageOptions对象
        //利用ImageView加载图片
        imageLoader.displayImage(url,viewHolder.timeduan,options);
        int count = mcounts.get(position);
        if(count == 0){
            viewHolder.ll.setOnClickListener(null);
            viewHolder.ll.setBackgroundResource(R.color.darkgray);
            viewHolder.guahao.setVisibility(View.GONE);
            viewHolder.man.setVisibility(View.VISIBLE);
        }

    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return murls.size();
    }



    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MyItemClickListener mListener;
        public ImageView timeduan;
        public TextView man;
        public TextView money;
        public TextView guahao;
        LinearLayout ll;

        public ViewHolder(View view,MyItemClickListener listener){
            super(view);
            this.mListener = listener;
            view.setOnClickListener(this);
            timeduan = (ImageView) view.findViewById(R.id.timeduan);
            man = (TextView) view.findViewById(R.id.man);
            money = (TextView) view.findViewById(R.id.money);
            guahao = (TextView) view.findViewById(R.id.guahao);
            ll = (LinearLayout)view.findViewById(R.id.item);
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