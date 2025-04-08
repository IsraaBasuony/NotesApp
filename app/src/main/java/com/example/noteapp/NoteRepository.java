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
        return Completable.fromAction(() -> {
            note.save();
        });
    }

    public Single<List<Note>> getAllNotes() {
        return Single.fromCallable(() -> {
            return LitePal.findAll(Note.class);
        });
    }

    public Completable delete(Note note) {
        return Completable.fromAction(() -> {
            LitePal.delete(Note.class, note.getId());
        });
    }

    public Completable deleteAllNotes() {
        return Completable.fromAction(() -> {
            LitePal.deleteAll(Note.class);
        });
    }

    public Completable update(Note note) {

        return Completable.fromAction(() -> {
            updateExistingNote(note);
        });
    }

    private static void updateExistingNote(Note note) {
        Note noteToUpdate = LitePal.find(Note.class, note.getId());
        noteToUpdate.setTitle(note.getTitle());
        noteToUpdate.setDescription(note.getDescription());
        noteToUpdate.setPriority(note.getPriority());
        noteToUpdate.save();
    }

}






