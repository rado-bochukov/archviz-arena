package com.example.archvizarena.service;

import com.example.archvizarena.model.binding.PenaltyAddBindingModel;

public interface PenaltyService {
    void addPenalty(PenaltyAddBindingModel penaltyAddBindingModel, Long id);
}
