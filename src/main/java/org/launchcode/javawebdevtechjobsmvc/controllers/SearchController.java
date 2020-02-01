package org.launchcode.javawebdevtechjobsmvc.controllers;

import org.launchcode.javawebdevtechjobsmvc.models.Job;
import org.launchcode.javawebdevtechjobsmvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

import static org.launchcode.javawebdevtechjobsmvc.controllers.ListController.columnChoices;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    // TODO #3 - Create a handler to process a search request and render the updated search view.

    @PostMapping("/results")
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm ) {
        ArrayList<Job> jobs = new ArrayList<>();

        if (searchTerm == "all" || searchTerm.isEmpty()) {
            ArrayList<Job> results = JobData.findAll();
            for (Job result : results) {
                jobs.add(result);
            }
        } else {
            ArrayList<Job> results = JobData.findByColumnAndValue(searchType, searchTerm);
            for (Job result : results) {
                jobs.add(result);
            }
        }

        model.addAttribute("jobs", jobs);
        model.addAttribute("title", "Jobs with " + columnChoices.get(searchType) + ": " + searchTerm);
        model.addAttribute("columns", ListController.columnChoices);
        return "search";
    }

}
