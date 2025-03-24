package com.example.noteapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;
    private CompositeDisposable disposable = new CompositeDisposable();

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Note note) {
        disposable.add(repository.insert(note)
                .subscribeOn(Schedulers.io())
                .subscribe());
    }

    public void update(Note note) {
        disposable.add(repository.update(note)
                .subscribeOn(Schedulers.io())
                .subscribe());
    }

    public void delete(Note note) {
        disposable.add(repository.delete(note)
                .subscribeOn(Schedulers.io())
                .subscribe());    }

    public void deleteAllNotes() {
        disposable.add(repository.deleteAllNotes()
                .subscribeOn(Schedulers.io())
                .subscribe());    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}



