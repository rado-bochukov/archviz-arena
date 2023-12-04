package com.example.archvizarena.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTaskService {

    private final ReportService reportService;
    private final PenaltyService penaltyService;

    public ScheduledTaskService( ReportService reportService, PenaltyService penaltyService) {
        this.reportService = reportService;
        this.penaltyService = penaltyService;
    }

    @Scheduled(cron = "0 0 */6 * * *")
    public void unmuteUsers(){
        penaltyService.unmuteUsersWithExpiredMute();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void clearArchivedReports(){
        reportService.clearArchivedReports();
    }

}
