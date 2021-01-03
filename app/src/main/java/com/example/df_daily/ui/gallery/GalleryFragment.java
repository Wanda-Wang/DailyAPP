package com.example.df_daily.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.df_daily.Adapter.TraceListAdapter;
import com.example.df_daily.Helper.DailyDbContorller;
import com.example.df_daily.R;
import com.example.df_daily.ReadDailyActivity;
import com.example.df_daily.bean.DailyInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GalleryFragment extends Fragment {

    private ListView lvTrace;
    TextView textView;
    private List<DailyInfo> traceList = new ArrayList<>(10);
    private TraceListAdapter adapter;
    private DailyDbContorller dailyDbContorller;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        dailyDbContorller=DailyDbContorller.getInstance(getContext());
        lvTrace = root.findViewById(R.id.lvTrace);
        textView=root.findViewById(R.id.text_no);
        initData();

        return root;
    }

    private void initData() {
        // 模拟一些假的数据
//        traceList.add(new Trace("2016-05-25 17:48:00", "[沈阳市] [沈阳和平五部]的派件已签收 感谢使用中通快递,期待再次为您服务!"));
//        traceList.add(new Trace("2016-05-25 14:13:00", "[沈阳市] [沈阳和平五部]的东北大学代理点正在派件 电话:18040xxxxxx 请保持电话畅通、耐心等待"));
//        traceList.add(new Trace("2016-05-25 13:01:04", "[沈阳市] 快件到达 [沈阳和平五部]"));
//        traceList.add(new Trace("2016-05-25 12:19:47", "[沈阳市] 快件离开 [沈阳中转]已发往[沈阳和平五部]"));
//        traceList.add(new Trace("2016-05-25 11:12:44", "[沈阳市] 快件到达 [沈阳中转]"));
//        traceList.add(new Trace("2016-05-24 03:12:12", "[嘉兴市] 快件离开 [杭州中转部]已发往[沈阳中转]"));
//        traceList.add(new Trace("2016-05-23 21:06:46", "[杭州市] 快件到达 [杭州汽运部]"));
//        traceList.add(new Trace("2016-05-23 18:59:41", "[杭州市] 快件离开 [杭州乔司区]已发往[沈阳]"));
//        traceList.add(new Trace("2016-05-23 18:35:32", "[杭州市] [杭州乔司区]的市场部已收件 电话:18358xxxxxx"));
        traceList.addAll(dailyDbContorller.searchAll());
        if(traceList.size()!=0){
            textView.setText("");
        }
        Collections.reverse(traceList);
        adapter = new TraceListAdapter(getContext(), traceList);
        lvTrace.setAdapter(adapter);
        lvTrace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), ReadDailyActivity.class);
                intent.putExtra("title",traceList.get(position).getDailyTitle());
                startActivity(intent);
            }
        });
    }
}