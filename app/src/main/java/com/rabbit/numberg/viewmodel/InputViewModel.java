package com.rabbit.numberg.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.rabbit.numberg.repository.InputRepository;

/**
 * Created by DongZF on 2022/5/17
 */
public class InputViewModel extends AndroidViewModel {

    @NonNull
    private final InputRepository repository;

    private InputViewModel(@NonNull Application application, @NonNull InputRepository repository) {
        super(application);
        this.repository = repository;
    }

    public static final class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final InputRepository repository;
        @NonNull
        private final Application application;

        public Factory(@NonNull Application application) {
            this.application = application;
            this.repository = InputRepository.getInstance();
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(InputViewModel.class)) {
                return (T) new InputViewModel(application, repository);
            }
            throw new IllegalArgumentException();
        }
    }
}
