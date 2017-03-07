package com.felipeteixeira.supergallerylib.model;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;

import com.felipeteixeira.supergallerylib.R;

/**
 * Created by felipe teixeira on 07/03/17.
 */
public class Album implements Parcelable
{
    public static final Parcelable.Creator<Album> CREATOR = new Creator<Album>() {
        @Nullable
        @Override
        public Album createFromParcel(Parcel source) {
            return new Album(source);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    public static final String ALBUM_ID_ALL = String.valueOf(-1);
    public static final String ALBUM_NAME_ALL = "All";
    public static final String ALBUM_NAME_CAMERA = "Camera";
    public static final String ALBUM_NAME_DOWNLOAD = "Download";
    public static final String ALBUM_NAME_SCREEN_SHOT = "Screenshots";
    private final String mId;
    private final long mCoverId;
    private final String mDisplayName;

    Album(String id, long coverId, String albumName) {
        mId = id;
        mCoverId = coverId;
        mDisplayName = albumName;
    }

     Album(Parcel source) {
        mId = source.readString();
        mCoverId = source.readLong();
        mDisplayName = source.readString();
    }

    public static Album valueOf(Cursor cursor) {
        return new Album(
                cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID)),
                cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID)),
                cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeLong(mCoverId);
        dest.writeString(mDisplayName);
    }

    public String getId() {
        return mId;
    }

    public long getCoverId() {
        return mCoverId;
    }

    public String getDisplayName(Context context) {
        if (isAll()) {
            return context.getString(R.string.l_album_name_all);
        }
        if (isCamera()) {
            return context.getString(R.string.l_album_name_camera);
        }
        if (ALBUM_NAME_DOWNLOAD.equals(mDisplayName)) {
            return context.getString(R.string.l_album_name_download);
        }
        if (ALBUM_NAME_SCREEN_SHOT.equals(mDisplayName)) {
            return context.getString(R.string.l_album_name_screen_shot);
        }
        return mDisplayName;
    }

    public boolean isAll() {
        return ALBUM_ID_ALL.equals(mId);
    }

    public boolean isCamera() {
        return ALBUM_NAME_CAMERA.equals(mDisplayName);
    }
}