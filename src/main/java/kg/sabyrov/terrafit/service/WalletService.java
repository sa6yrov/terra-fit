package kg.sabyrov.terrafit.service;

import kg.sabyrov.terrafit.dto.walletDto.WalletReplenishDto;
import kg.sabyrov.terrafit.dto.walletDto.WalletResponseDto;
import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.entity.Wallet;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;
import kg.sabyrov.terrafit.models.ResponseMessage;

public interface WalletService extends BaseService<Wallet> {
    String generateRequisite();

    Wallet getByUser(User user) throws UserNotFoundException;

    WalletResponseDto getWalletModelByUser() throws UserNotFoundException;

    String replenish(WalletReplenishDto walletReplenishDto) throws UserNotFoundException;
}
