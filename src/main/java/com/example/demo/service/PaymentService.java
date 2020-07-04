package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final CourseService courseService;

    private final UserService userService;

    private final EnrollmentService enrollmentService;

    private final TransactionService transactionService;

    public PaymentService(PaymentRepository paymentRepository, CourseService courseService, UserService userService, EnrollmentService enrollmentService, TransactionService transactionService) {
        this.paymentRepository = paymentRepository;
        this.courseService = courseService;
        this.userService = userService;
        this.enrollmentService = enrollmentService;
        this.transactionService = transactionService;
    }

    public void delete(Long id) {
        this.paymentRepository.deleteById(id);
    }

    public List<Payment> searchPayment(String username) {
        return this.paymentRepository.findAllByUser(this.userService.findByUsername(username));
    }

    public List<Payment> findUnpaidByCourse(Long courseId) {
        return this.paymentRepository.
                findAllByCourse_Id(courseId).
                stream().
                filter(x -> x.getPaid() == 0).
                filter(this::isPaid).
                collect(Collectors.toList());
    }

    public List<Statistics> findStatistics(User user) {
        return this.courseService.
                findByStudent(user).
                stream().
                map(x -> this.getStatistics(x, user)).
                filter(Objects::nonNull).
                collect(Collectors.toList());
    }

    @Transactional
    public void save(Transaction transaction) {
        var payment = this.paymentRepository.findById(transaction.getPaymentId());

        Consumer<Payment> paymentConsumer = p -> {
            var amountToPay = p.getCourse().getFeePerMonth().multiply(BigDecimal.valueOf(p.getCourse().getDuration()));
            if (p.getPaid() != 1) {
                transaction.setPayment(p);
                p.setPaidAmount(p.getPaidAmount().add(transaction.getAmount()));
                if (p.getPaidAmount().compareTo(amountToPay) == 0) {
                    p.setPaid((short) 1);
                }
                this.paymentRepository.save(p);
                this.transactionService.save(transaction);
            }

        };
        payment.ifPresent(paymentConsumer);
    }

    private boolean isPaid(Payment payment) {
        return calculateAmountToPay(payment).compareTo(payment.getPaidAmount()) > 0;
    }

    private BigDecimal calculateAmountToPay(Payment payment) {
        var monthStudied = this.findMonthsStudied(payment);
        return payment.getCourse().getFeePerMonth().multiply(monthStudied);
    }

    private LocalDate findEnrollmentDate(Payment payment) {
        return this.enrollmentService.findOne(payment);
    }

    private BigDecimal findMonthsStudied(Payment payment) {
        return BigDecimal.
                valueOf(this.findEnrollmentDate(payment).until(LocalDate.now()).getMonths() + 1);
    }

    private BigDecimal decideDebt(Payment payment) {
        var amountToPay = this.calculateAmountToPay(payment);
        if (amountToPay.compareTo(payment.getPaidAmount()) <= 0) {
            return BigDecimal.valueOf(0);
        }
        return amountToPay.subtract(payment.getPaidAmount());
    }

    private Statistics getStatistics(Course course, User user) {
        var optionalPayment = this.paymentRepository.findByUserAndCourse(user, course);
        if (optionalPayment.isPresent()) {
            var payment = optionalPayment.get();
            return new Statistics(
                    course.getName(),
                    findEnrollmentDate(payment),
                    this.decideDebt(payment),
                    payment.getPaidAmount(),
                    this.findMonthsStudied(payment)
            );
        }
        return null;
    }


}


