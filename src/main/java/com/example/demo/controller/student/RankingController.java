package com.example.demo.controller.student;

import com.example.demo.service.RankingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/student")
public class RankingController {

    private RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping("/ranking")
    public String ranking(Model model) {
        model.addAttribute("rankings", this.rankingService.findTotalRankings());
        return "ranking";
    }

    @GetMapping("/rankingByCourse")
    public String rankingByCourse(Model model, @RequestParam Long courseId) {
        model.addAttribute("rankings", this.rankingService.findRankingsByCourse(courseId));
        return "ranking";
    }

    @GetMapping("/rankingByUsername")
    public String rankingByUsername(Model model, @RequestParam String username) {
        model.addAttribute("rankings", this.rankingService.findIndividualRanking(username));
        return "ranking";
    }
}
