package com.example.noteapp;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import io.reactivex.rxjava3.core.Completable;

public class NoteRepository {
    private NoteDao noteDao;
    private  LiveData<List<Note>> allNotes;

    public  NoteRepository(Application application){
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        noteDao = noteDatabase.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public Completable insert(Note note){
        return Completable.create(emitter -> {
            try{
                noteDao.insert(note);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }
    public Completable update(Note note){

       return Completable.create(emitter -> {
           try{
            noteDao.update(note);
               emitter.onComplete();
           } catch (Exception e) {
               emitter.onError(e);
           }
        });
    }
    public Completable delete(Note note){
        return Completable.create(emitter -> {
                    try{
                        noteDao.delete(note);
                        emitter.onComplete();
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                });
    }

    public Completable deleteAllNotes(){
        return Completable.create(emitter -> {
                    try{
                        noteDao.deleteAllNotes();
                        emitter.onComplete();
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                });
    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

}






