package com.example.demo.service;

import com.example.demo.entity.Transaction;
import com.example.demo.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void save(Transaction transaction) {
        this.transactionRepository.save(transaction);
    }

    public List<Transaction> findAllByPayment(Long paymentId) {
        return this.transactionRepository.findAllByPayment_Id(paymentId);
    }

    public Map<String, BigDecimal> findTotalTransactionByMonth() {

        return this.transactionRepository.
                findAll().
                stream().
                collect(Collectors.groupingBy(Transaction::getDate,
                        Collectors.reducing(BigDecimal.valueOf(0),
                                Transaction::getAmount,
                                BigDecimal::add)));

    }
}
