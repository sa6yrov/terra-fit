package kg.sabyrov.terrafit.service;

import kg.sabyrov.terrafit.entity.PromoCode;

public interface PromoCodeService extends BaseService<PromoCode> {
    PromoCode findByName(String promoCode);
}
