package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.entity.Wallet;
import kg.sabyrov.terrafit.repository.WalletRepository;
import kg.sabyrov.terrafit.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    private WalletRepository walletRepository;

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
}
