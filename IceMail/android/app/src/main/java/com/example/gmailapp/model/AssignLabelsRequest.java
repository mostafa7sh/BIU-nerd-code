package com.example.gmailapp.model;

import java.util.List;

public class AssignLabelsRequest {
    private List<String> labelIds;

    public AssignLabelsRequest(List<String> labelIds) {
        this.labelIds = labelIds;
    }

    public List<String> getLabelIds() {
        return labelIds;
    }

    public void setLabelIds(List<String> labelIds) {
        this.labelIds = labelIds;
    }
}
