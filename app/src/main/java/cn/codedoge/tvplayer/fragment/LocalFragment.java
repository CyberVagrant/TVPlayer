package cn.codedoge.tvplayer.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.codedoge.tvplayer.R;
import cn.codedoge.tvplayer.adapter.MediaAdapter;
import cn.codedoge.tvplayer.entity.MediaFile;
import cn.codedoge.tvplayer.common.MyApplication;
import cn.codedoge.tvplayer.ui.MainActivity;

public class LocalFragment extends Fragment {

    private List<MediaFile> mMediaList = new ArrayList<>();

    private MediaAdapter adapter;

    public LocalFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local, container, false);

        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        } else {
            getLocalMedia();
        }

        RecyclerView recyclerView = view.findViewById(R.id.local_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyApplication.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MediaAdapter(mMediaList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void getLocalMedia() {
        mMediaList.clear();
        Cursor cursor = MyApplication.getContext().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
        try {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)); // id
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)); // 显示名称
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)); // 路径
                long duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)); // 时长
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)); // 大小
                String resolution =cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION));
                Bitmap preview = getVideoThumb(path, 220, 140, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);

                MediaFile mediaFile = new MediaFile(id, title, path, duration, size, resolution, preview);
                mMediaList.add(mediaFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
    }

    private Bitmap getVideoThumb(String filePath, int width_, int height_, int kind) {
        Bitmap bitmap = null;
        try {
            bitmap = ThumbnailUtils.createVideoThumbnail(filePath,kind);
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width_, height_,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bitmap == null) return null;
        return bitmap;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocalMedia();
                } else {
                    Toast.makeText(MyApplication.getContext(), "无法读取本地文件，请授予权限", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
