package com.example.archvizarena.model.binding;

import com.example.archvizarena.model.entity.enums.PenaltyTypeEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PenaltyAddBindingModel {
    @Size(min = 5)
    @NotNull
    private String penaltyMessage;
    @NotNull
    private PenaltyTypeEnum penaltyType;

    public PenaltyAddBindingModel() {
    }

    public PenaltyTypeEnum getPenaltyType() {
        return penaltyType;
    }

    public void setPenaltyType(PenaltyTypeEnum penaltyType) {
        this.penaltyType = penaltyType;
    }

    public String getPenaltyMessage() {
        return penaltyMessage;
    }

    public void setPenaltyMessage(String penaltyMessage) {
        this.penaltyMessage = penaltyMessage;
    }
}
