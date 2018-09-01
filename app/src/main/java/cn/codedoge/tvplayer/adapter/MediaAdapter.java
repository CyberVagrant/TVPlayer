package cn.codedoge.tvplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.codedoge.tvplayer.R;
import cn.codedoge.tvplayer.entity.MediaFile;
import cn.codedoge.tvplayer.ui.MediaActivity;

/**
 * Created by codedoge on 2018/1/4.
 */

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder> {

    private Context mContext;

    private List<MediaFile> mMediaList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView mediaImage;
        TextView mediaTitle;
        TextView mediaOther;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            mediaImage = itemView.findViewById(R.id.media_preview);
            mediaTitle = itemView.findViewById(R.id.media_title);
            mediaOther = itemView.findViewById(R.id.media_other);
        }
    }

    public MediaAdapter(List<MediaFile> mediaList) {
        mMediaList = mediaList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.localmedia_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                MediaFile mediaFile = mMediaList.get(position);
                Intent intent = new Intent(mContext, MediaActivity.class);
                intent.putExtra("media_path", mediaFile.getPath());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MediaFile media = mMediaList.get(position);
        Bitmap bitmap = media.getPreview();
        holder.mediaImage.setImageBitmap(bitmap);
        holder.mediaTitle.setText(media.getTitle());
        String other = media.getDuration() + "  " + media.getResolution() + "  " + media.getSize();
        holder.mediaOther.setText(other);
    }

    @Override
    public int getItemCount() {
        return mMediaList.size();
    }


}
