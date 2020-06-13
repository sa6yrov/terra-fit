package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.entity.PromoCode;
import kg.sabyrov.terrafit.repository.PromoCodeRepository;
import kg.sabyrov.terrafit.service.PromoCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromoCodeServiceImpl implements PromoCodeService {
    @Autowired
    private PromoCodeRepository promoCodeRepository;

    @Override
    public PromoCode save(PromoCode promoCode) {
        return promoCodeRepository.save(promoCode);
    }

    @Override
    public PromoCode getById(Long id) {
        Optional<PromoCode> optionalPromoCode = promoCodeRepository.findById(id);
        return optionalPromoCode.orElse(null);
    }

    @Override
    public List<PromoCode> getAll() {
        return promoCodeRepository.findAll();
    }

    @Override
    public PromoCode findByName(String promoCode) {
        return promoCodeRepository.findByName(promoCode);
    }
}
