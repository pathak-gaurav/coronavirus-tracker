package ca.gauravpathak.controller;

import ca.gauravpathak.model.LocationStats;
import ca.gauravpathak.service.CoronavirusTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@EnableScheduling
public class CoronavirusController {

    @Autowired
    private CoronavirusTrackerService coronavirusTrackerService;

    @GetMapping("/")
    public String showCoronaData(Model model) {
        List<LocationStats> allStats = coronavirusTrackerService.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(stats -> stats.getLatestTotalCases()).sum();
        model.addAttribute("allStats",allStats);
        model.addAttribute("totalReportedCases",totalReportedCases);
        return "corona_data";
    }
}
