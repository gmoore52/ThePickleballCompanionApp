package edu.missouristate.thepickleballcompanionapp.controller;

import edu.missouristate.thepickleballcompanionapp.domain.dto.UserStatisticsDTO;
import edu.missouristate.thepickleballcompanionapp.domain.dto.UserStatisticsHistoricalDTO;
import edu.missouristate.thepickleballcompanionapp.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/getUserStatsHst")
    public List<UserStatisticsHistoricalDTO> getUserStatisticsHistoric(@RequestParam List<String> username,
                                                                       @RequestParam String stat) {
        return statisticsService.getUserStatisticsHistoric(username, stat);
    }

    @GetMapping("/stageUserStatsHst")
    public boolean stageUserStatisticsHistoric(@RequestParam List<String> username){
         return statisticsService.stageUserStatisticsHistoric(username);
    }

    @GetMapping("/getUserStats")
    public UserStatisticsDTO getUserStatistics(@RequestParam String username){
        return statisticsService.getUserStatistics(username);
    }
}
