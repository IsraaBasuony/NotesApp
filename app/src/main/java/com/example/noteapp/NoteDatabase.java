package com.example.noteapp;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Database(entities = {Note.class}, version = 3)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public synchronized static NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            populateDB(instance);

        }
    };

    private static void populateDB(NoteDatabase db) {
        NoteDao noteDao = db.noteDao();
        Completable.create(emitter -> {
                            try {
                                noteDao.insert(new Note("Title 1", "Description 1", 1));
                                noteDao.insert(new Note("Title 2", "Description 2", 2));
                                noteDao.insert(new Note("Title 3", "Description 3", 3));
                                emitter.onComplete();

                            } catch (Exception e) {
                                emitter.onError(e);
                            }
                        }

                ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

}
