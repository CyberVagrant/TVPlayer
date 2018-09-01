package cn.codedoge.tvplayer.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.codedoge.tvplayer.R;
import cn.codedoge.tvplayer.adapter.LiveAdapter;
import cn.codedoge.tvplayer.common.MyApplication;
import cn.codedoge.tvplayer.entity.Live;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LiveFragment extends Fragment {

    private static final String TAG = "LiveFragment";

    private List<Live> mLiveList = new ArrayList<>();

    private LiveAdapter adapter;

    private static final String DataURL = "http://115.159.145.38/TVPlayer/test.json";

    public static final int REFRESH_VIEW = 1;

    public LiveFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.live_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyApplication.getContext());
        recyclerView.setLayoutManager(layoutManager);
        //sendRequest();
        fuck();
        adapter = new LiveAdapter(mLiveList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void fuck() {
        mLiveList.add(new Live("美国-NASA-NTV1","https://nasa-i.akamaihd.net/hls/live/253565/nasa-NTV1-Public/master.m3u8",
                "/TVPlayer/image/NASA.png"));
        mLiveList.add(new Live("美国-NASA-NTV2","https://nasa-i.akamaihd.net/hls/live/253566/nasa-NTV2-Media/master.m3u8",
                "/TVPlayer/image/NASA.png"));
        mLiveList.add(new Live("凤凰中文HD","http://223.110.245.139:80/PLTV/3/224/3221226977/index.m3u8",
                "/TVPlayer/image/FengHuang.jpg"));
        mLiveList.add(new Live("CCTV风云音乐","http://cg01.hrtn.net:9090/live/fyyy.m3u8",
                "/TVPlayer/image/CCTVMusic.jpg"));
        mLiveList.add(new Live("CCTV怀旧剧场", "http://cg01.hrtn.net:9090/live/hjjc.m3u8",
                "/TVPlayer/image/CCTVOld.jpg"));
        mLiveList.add(new Live("CCTV世界地理","http://cg01.hrtn.net:9090/live/sjdl.m3u8",
                "/TVPlayer/image/CCTVGeo.jpg"));
        mLiveList.add(new Live("CHC动作电影","http://hbmc.chinashadt.com:2739/live/dzdy.stream_360p/playlist.m3u8",
                "/TVPlayer/image/CHC.jpg"));
        mLiveList.add(new Live("国家地理","http://adtv.ercdn.net/adnatgeo/adnatgeo_720p.m3u8",
                "/TVPlayer/image/NationalGeographic.png"));
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_VIEW:
                    adapter.notifyDataSetChanged();
                    Log.d(TAG, "notifyDataSetChanged()");
                    break;
                default:
                    break;
            }
        }
    };

    private void sendRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(DataURL)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    initList(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void initList(String jsonData) {
        Log.d(TAG, "receive data:");
        Log.d(TAG, "jsonData:\n" + jsonData);
        mLiveList.clear();
        Gson gson = new Gson();
        mLiveList = gson.fromJson(jsonData, new TypeToken<List<Live>>(){}.getType());
        Message message = new Message();
        message.what = REFRESH_VIEW;
        handler.sendMessage(message);
    }

}
