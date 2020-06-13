package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.dto.walletDto.WalletReplenishDto;
import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.entity.Wallet;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;
import kg.sabyrov.terrafit.models.ResponseMessage;
import kg.sabyrov.terrafit.repository.WalletRepository;
import kg.sabyrov.terrafit.service.UserService;
import kg.sabyrov.terrafit.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private UserService userService;


    @Override
    public Wallet save(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet getById(Long id) {
        Optional<Wallet> walletOptional = walletRepository.findById(id);
        return walletOptional.orElse(null);
    }

    @Override
    public List<Wallet> getAll() {
        return walletRepository.findAll();
    }


    @Override
    public String generateRequisite() {
        return "" + System.currentTimeMillis();
    }

    @Override
    public Wallet getByUser() throws UserNotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails)principal).getUsername();
        User user = userService.findByEmail(email);

        return walletRepository.findByUser(user);
    }

    @Override
    public ResponseMessage replenish(WalletReplenishDto walletReplenishDto) throws UserNotFoundException {
        Wallet wallet = getByUser();
        wallet.setBalance(wallet.getBalance().add(walletReplenishDto.getAmount()));
        save(wallet);
        return ResponseMessage.builder()
                .message("Your balance was successfully topped up")
                .build();
    }
}
