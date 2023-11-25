package com.example.archvizarena.service;


import com.example.archvizarena.model.view.WorkInProgressViewModel;

import java.util.List;

public interface WorkInProgressService {
    void addWorkInProgress(Long jobId, Long applicantId, Long principalId);

    List<WorkInProgressViewModel> getAllArtistWorkInProgress(Long id);

    List<WorkInProgressViewModel> getAllBuyerWorkInProgress(Long id);
}
