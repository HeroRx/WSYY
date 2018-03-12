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
import com.gzucm.wsyy.bean.Doctor;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * DayDoctorAdapter
 * Created by Administrator on 2017/11/21 0021.
 */

public class DayDoctorAdapter extends RecyclerView.Adapter<DayDoctorAdapter.ViewHolder> {

    private List<Doctor> doctors;
    private List<Integer> d_counts;
    private Context mContext;
    private ImageLoader imageLoader;

    public DayDoctorAdapter(Context context,List<Doctor> doctors, List<Integer> d_counts) {
        this.doctors = doctors;
        this.d_counts = d_counts;
        this.mContext = context;
        imageLoader=ImageLoader.getInstance();
    }
    public DayDoctorAdapter(List<Doctor> doctors, List<Integer> d_counts) {
        this.doctors = doctors;
        this.d_counts = d_counts;
        imageLoader=ImageLoader.getInstance();
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<Integer> getD_counts() {
        return d_counts;
    }

    public void setD_counts(List<Integer> d_counts) {
        this.d_counts = d_counts;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_doctor_department,viewGroup,false);
        ViewHolder vh = new ViewHolder(view,mItemClickListener);
        return vh;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.d_name.setText(doctors.get(position).getD_name());
        viewHolder.d_title.setText(doctors.get(position).getD_titles());
        int count = d_counts.get(position);
        if(count == 0){
            viewHolder.man.setVisibility(View.VISIBLE);
            viewHolder.ll.setBackgroundResource(R.color.darkgray);
            viewHolder.ll.setOnClickListener(null);

        }
        viewHolder.d_count.setText(d_counts.get(position)+"");
        final String url = doctors.get(position).getD_photo().getFileUrl();

        //创建DisplayImageOptions对象并进行相关选项配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.icon_stub)// 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.icon_empty)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.icon_x)// 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .build();// 创建DisplayImageOptions对象
        //利用ImageView加载图片
        imageLoader.displayImage(url,viewHolder.d_photo,options);

    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return doctors.size();
    }



    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MyItemClickListener mListener;
        public ImageView d_photo;
        public ImageView man;
        public TextView d_name;
        public TextView d_title;
        public TextView d_count;
        public LinearLayout ll;
        public ViewHolder(View view,MyItemClickListener listener){
            super(view);
            this.mListener = listener;
            view.setOnClickListener(this);
            ll = (LinearLayout)view.findViewById(R.id.ll);
            d_photo = (ImageView) view.findViewById(R.id.dd_head);
            man = (ImageView) view.findViewById(R.id.man);
            d_name = (TextView) view.findViewById(R.id.dd_name);
            d_title = (TextView) view.findViewById(R.id.dd_title);
            d_count = (TextView) view.findViewById(R.id.dd_count);
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