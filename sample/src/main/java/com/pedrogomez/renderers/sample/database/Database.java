package com.pedrogomez.renderers.sample.database;

import android.arch.persistence.room.RoomDatabase;

import com.pedrogomez.renderers.sample.model.Video;

@android.arch.persistence.room.Database(entities = {Video.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    public abstract VideoDao videoDao();
}
