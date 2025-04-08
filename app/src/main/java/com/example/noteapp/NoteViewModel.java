package com.example.noteapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private CompositeDisposable disposable = new CompositeDisposable();

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
    }

    public void insert(Note note, NoteOperationResultListener listener) {
        disposable.add(repository.insert(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {
                    listener.onSuccess();
                })
                .doOnError(error -> listener.onFailure(error))
                .subscribe());
    }


    public void delete(Note note, NoteOperationResultListener listener) {
        disposable.add(repository.delete(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {
                    listener.onSuccess();
                })
                .doOnError(error -> listener.onFailure(error))
                .subscribe());
    }

    public void deleteAllNotes(NoteOperationResultListener listener) {
        disposable.add(repository.deleteAllNotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {
                    listener.onSuccess();
                })
                .doOnError(error -> listener.onFailure(error))
                .subscribe());
    }

    public Single<List<Note>> getAllNotes() {
        return repository.getAllNotes().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void update(Note note, NoteOperationResultListener listener) {
        disposable.add(repository.update(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {
                    listener.onSuccess();
                })
                .doOnError(error -> listener.onFailure(error))
                .subscribe());
    }
}



