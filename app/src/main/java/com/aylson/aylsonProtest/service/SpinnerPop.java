package com.aylson.aylsonProtest.service;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.aylson.aylsonProtest.R;

import java.util.List;

/**
 * Created by Oshx on 2016/8/20.
 */
public class SpinnerPop extends PopupWindow implements AdapterView.OnItemClickListener {

    private Context mContext;
    private ListView mListView;
    private NormalSpinnerAdapter mAdapter;
    private OnItemSelectListener mItemSelectListener;


    public SpinnerPop(Context context)
    {
        super(context);

        mContext = context;
        init();
    }


    public void setItemListener(OnItemSelectListener listener){
        mItemSelectListener = listener;
    }


    private void init()
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.spiner_window_layout, null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);
        mListView = (ListView) view.findViewById(R.id.listview);
        mAdapter = new NormalSpinnerAdapter(mContext);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }


    public void refreshData(List<String> list, int selIndex)
    {
        if (list != null && selIndex  != -1)
        {
            mAdapter.refreshData(list, selIndex);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dismiss();
        if (mItemSelectListener != null){
            mItemSelectListener.onItemClick(position);
        }
    }


    public interface OnItemSelectListener{
        public void onItemClick(int position);
        public void onBtSelectClick(String result);
    }

}
