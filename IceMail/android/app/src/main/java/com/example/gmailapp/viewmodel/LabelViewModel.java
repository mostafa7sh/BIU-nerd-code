package com.example.gmailapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.gmailapp.model.Label;
import com.example.gmailapp.repository.LabelRepository;

import java.util.List;

public class LabelViewModel extends ViewModel {
    private final LabelRepository labelRepo = new LabelRepository();
    private LabelViewModel repository;
    private LabelViewModel labelRepository;

    public LiveData<List<Label>> getLabels(String jwt) {
        return labelRepo.getLabels(jwt);
    }

    public LiveData<Boolean> assignLabelsToMail(String jwt, String mailId, List<String> labelIds) {
        return repository.assignLabelsToMail(jwt, mailId, labelIds);
    }

    public LiveData<Boolean> updateLabel(String jwt, Label label) {
        return labelRepository.updateLabel(jwt, label);
    }

    public LiveData<Boolean> deleteLabel(String jwt, String labelId) {
        return labelRepository.deleteLabel(jwt, labelId);
    }


}