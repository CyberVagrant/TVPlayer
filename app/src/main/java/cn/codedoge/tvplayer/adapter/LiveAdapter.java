package cn.codedoge.tvplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.codedoge.tvplayer.R;
import cn.codedoge.tvplayer.entity.Live;
import cn.codedoge.tvplayer.ui.LiveActivity;
import cn.codedoge.tvplayer.ui.MediaActivity;

/**
 * Created by codedoge on 2018/1/5.
 */

public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.ViewHolder> {

    private static final String TAG = "LiveAdapter";

    private Context mContext;

    private List<Live> mLiveList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView liveIcon;
        TextView liveName;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            liveIcon = itemView.findViewById(R.id.live_icon);
            liveName = itemView.findViewById(R.id.live_name);
        }
    }

    public LiveAdapter(List<Live> liveList) {
        mLiveList = liveList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.live_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Live live = mLiveList.get(position);
                Intent intent = new Intent(mContext, LiveActivity.class);
                intent.putExtra("media_path", live.getUrl());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Live live = mLiveList.get(position);
        String path = "http://115.159.145.38" + live.getPath();
        Log.d(TAG, "liveIcon path:" + path);
        Glide.with(mContext).load(path).into(holder.liveIcon);
        //holder.liveIcon.setImageURI(Uri.parse(path));
        holder.liveName.setText(live.getName());
    }

    @Override
    public int getItemCount() {
        return mLiveList.size();
    }


}
