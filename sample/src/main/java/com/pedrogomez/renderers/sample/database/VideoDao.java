package com.pedrogomez.renderers.sample.database;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.pedrogomez.renderers.sample.model.Video;

import java.util.List;


@Dao
public interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertVideos(List<Video> videos);

    @Query("SELECT * FROM videos")
    DataSource.Factory<Integer, Video> videos();

}
