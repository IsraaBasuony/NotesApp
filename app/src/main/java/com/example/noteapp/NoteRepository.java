package com.example.noteapp;

import android.app.Application;

import org.litepal.LitePal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class NoteRepository {
    public NoteRepository(Application application) {
        LitePal.initialize(application);
    }
    public Completable insert(Note note) {
        return Completable.create(emitter -> {
            try {
                note.save();
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }
    public Single<List<Note>> getAllNotes() {
        return Single.create(emitter -> {
            try {
                List<Note> notes = LitePal.findAll(Note.class);
                emitter.onSuccess(notes);
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }
    public Completable delete(Note note) {
        return Completable.create(emitter -> {
            try {
                LitePal.delete(Note.class, note.getId());
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }
    public Completable deleteAllNotes() {
        return Completable.create(emitter -> {
            try {
                LitePal.deleteAll(Note.class);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    public Completable update(Note note) {

        return Completable.create(emitter -> {
            try {
                Note noteToUpdate = LitePal.find(Note.class, note.getId());
                noteToUpdate.setTitle(note.getTitle());
                noteToUpdate.setDescription(note.getDescription());
                noteToUpdate.setPriority(note.getPriority());
                noteToUpdate.save();
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

}






