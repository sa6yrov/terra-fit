package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.entity.Payment;
import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.entity.Wallet;
import kg.sabyrov.terrafit.enums.Currency;
import kg.sabyrov.terrafit.enums.Status;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;
import kg.sabyrov.terrafit.repository.PaymentRepository;
import kg.sabyrov.terrafit.service.PaymentService;
import kg.sabyrov.terrafit.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private WalletService walletService;

    @Override
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }


    @Override
    public Payment getById(Long id) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        return optionalPayment.orElse(null);
    }

    @Override
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    @Override
    public boolean isPaid(User user, BigDecimal totalAmount) throws UserNotFoundException {
        Wallet from = walletService.getByUser(user);
        Wallet to = walletService.getById(1L);
        if(from.getBalance().compareTo(BigDecimal.ZERO) <= 0) return false;
        Payment payment = Payment.builder()
                .walletTo(to)
                .walletFrom(from)
                .currency(Currency.KGZ)
                .status(Status.PROCESSING)
                .build();
        return processPayment(payment, totalAmount);
    }

    private boolean processPayment(Payment payment, BigDecimal totalAmount){
        Wallet from = payment.getWalletFrom();
        Wallet to = payment.getWalletTo();
        from.setBalance(from.getBalance().subtract(totalAmount));
        to.setBalance(to.getBalance().add(totalAmount));
        walletService.save(from);
        walletService.save(to);
        payment.setStatus(Status.PAID);
        save(payment);
        return true;
    }
}
