package kg.sabyrov.terrafit.service;

import kg.sabyrov.terrafit.entity.Payment;
import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.exceptions.SubscriptionNotFoundException;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;
import kg.sabyrov.terrafit.exceptions.WrongBalanceException;

import java.math.BigDecimal;

public interface PaymentService extends BaseService<Payment>{

    boolean isPaid(User user, BigDecimal totalAmount) throws UserNotFoundException;
}
