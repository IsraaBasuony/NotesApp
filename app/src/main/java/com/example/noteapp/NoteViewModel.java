package com.example.noteapp;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private final MutableLiveData<List<Note>> allNotes = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        fetchAllNotes();
    }
    public void insert(Note note, Runnable onSuccess) {
        disposable.add(repository.insert(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(()-> {
                    fetchAllNotes();
                    onSuccess.run();
                })
                .subscribe());
    }
    private void fetchAllNotes() {
        disposable.add(repository.getAllNotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        allNotes::setValue,
                        Throwable::printStackTrace
                ));

    }
    public void delete(Note note,Runnable onSuccess, Runnable onFailure) {
        disposable.add(repository.delete(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(()-> {
                    fetchAllNotes();
                    onSuccess.run();
                })
                .doOnError(error -> onFailure.run())
                .subscribe());
    }
    public void deleteAllNotes(Runnable onSuccess, Runnable onFailure) {
        disposable.add(repository.deleteAllNotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(()-> {
                    fetchAllNotes();
                    onSuccess.run();
                })
                .subscribe());
    }
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public void update(Note note, Runnable onSuccess, Runnable onFailure) {
        disposable.add(repository.update(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(()-> {
                    fetchAllNotes();
                    onSuccess.run();
                })
                .doOnError(error -> onFailure.run())
                .subscribe());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}



