package com.example.demo.controller.admin;

import com.example.demo.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transactionByPayment")
    public String transactionByPayment(@RequestParam Long paymentId, Model model) {
        model.addAttribute("transactions", this.transactionService.findAllByPayment(paymentId));
        return "transaction";
    }

    @GetMapping("/transactionByMonth")
    public String transactionByMonth(Model model) {
        model.addAttribute("transactionsByMonth",this.transactionService.findTotalTransactionByMonth());
        return "transactionByMonth";
    }
}
