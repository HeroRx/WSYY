package com.gzucm.wsyy.view;

/**
 * https://www.cnblogs.com/JczmDeveloper/p/4425010.html
 * Created by Administrator on 2017/12/7 0007.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.gzucm.wsyy.R;

import java.util.ArrayList;

@SuppressLint("NewApi")
/**
 * 下拉列表框控件
 * @author caizhiming
 *
 */
public class XCDropDownListView extends LinearLayout{

    private TextView editText;
    private ImageView imageView;
    private PopupWindow popupWindow = null;
    private ArrayList<String> dataList =  new ArrayList<String>();

    //初始化属性
    public XCDropDownListView(Context context) {
        this(context,null);
        // TODO Auto-generated constructor stub
    }
    public XCDropDownListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        // TODO Auto-generated constructor stub
    }
    public XCDropDownListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        initView();
    }

    public void initView(){
        String infServie = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater;
        layoutInflater =  (LayoutInflater) getContext().getSystemService(infServie);
        View view  = layoutInflater.inflate(R.layout.dropdownlist_view, this,true);
        editText= (TextView)findViewById(R.id.text);
        imageView = (ImageView)findViewById(R.id.btn);
        //点击时收起或者展开PopupWindow
        this.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(popupWindow == null ){
                    showPopWindow();
                }else{
                    closePopWindow();
                }
            }
        });
    }
    /**
     * 打开下拉列表弹窗
     */
    private void showPopWindow() {

        // 加载popupWindow的布局文件
        String infServie = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater;
        layoutInflater =  (LayoutInflater) getContext().getSystemService(infServie);
        View contentView  = layoutInflater.inflate(R.layout.dropdownlist_popupwindow, null,false);
        ListView listView = (ListView)contentView.findViewById(R.id.listView);
        final XCDropDownListAdapter adapter = new XCDropDownListAdapter(getContext(), dataList,mItemClickListener);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String text = dataList.get(position);
                    editText.setText(text);
                    if(mItemClickListener != null){
                        mItemClickListener.onItemClick(view,position);
                    }else {
                        mItemClickListener.onItemClick(view,0);
                    }
                    closePopWindow();
            }
        });
        listView.setAdapter(adapter);
        popupWindow = new PopupWindow(contentView,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.orange02));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(imageView);

    }


    /**
     * 关闭下拉列表弹窗
     */
    private void closePopWindow(){
        popupWindow.dismiss();
        popupWindow = null;
    }
    /**
     * 设置数据
     * @param list
     */
    public void setItemsData(ArrayList<String> list){
        dataList = list;
        editText.setText(list.get(0).toString());
    }

    /**
     * 数据适配器
     * @author caizhiming
     *
     */
    public class XCDropDownListAdapter extends BaseAdapter{

        Context mContext;
        ArrayList<String> mData;
        LayoutInflater inflater;
        private MyItemClickListener mListener;
        public XCDropDownListAdapter(Context ctx,ArrayList<String> data,MyItemClickListener listener){
            mContext  = ctx;
            mData = data;
            inflater = LayoutInflater.from(mContext);
            mListener = listener;
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            // 自定义视图
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.dropdown_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.tv = (TextView) convertView
                        .findViewById(R.id.tv);
                // 设置控件集到convertView
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            // 设置数据

            final String text = mData.get(position).toString();
            viewHolder.tv.setText(text);
//            viewHolder.tv.setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    // TODO Auto-generated method stub
//                    editText.setText(text);
//                    if(mListener != null){
//                        mListener.onItemClick(v,position);
//                    }
//                    closePopWindow();
//                }
//            });
            return convertView;
        }

    }
    class ViewHolder{
        public TextView tv;
    }
    public interface MyItemClickListener {
        void onItemClick(View view, int postion);
    }
    private MyItemClickListener mItemClickListener = null;
    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }
}
