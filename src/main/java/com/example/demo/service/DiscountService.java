package com.example.demo.service;

import com.example.demo.entity.Discount;
import com.example.demo.repository.DiscountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountService {

    private final DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public double findByCoursesCount(int count) {
        return this.discountRepository.
                findByCoursesCount(count).
                map(Discount::getAmount).orElse(0d);

    }

    public List<Discount> findAll() {
        return this.discountRepository.findAll();
    }

    public Discount findById(Long discountId) {
        return this.discountRepository.
                findById(discountId).
                orElseThrow(() -> new NullPointerException("Discount with id " + discountId + " does not exist!"));
    }

    public void save(Discount discount) {
        this.discountRepository.save(discount);
    }

    public void delete(Long discountId) {
        this.discountRepository.deleteById(discountId);
    }
}
