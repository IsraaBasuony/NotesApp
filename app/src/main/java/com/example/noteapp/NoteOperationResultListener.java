package com.example.noteapp;

public interface NoteOperationResultListener {
    void onSuccess();
    void onFailure(Throwable throwable);
}
