package kg.sabyrov.terrafit.controller;

import kg.sabyrov.terrafit.dto.walletDto.WalletReplenishDto;
import kg.sabyrov.terrafit.dto.walletDto.WalletResponseDto;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;
import kg.sabyrov.terrafit.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @GetMapping("/my")
    public ResponseEntity<?> getWallet(){
        try {
            return new ResponseEntity<>(walletService.getWalletModelByUser(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/my/replenish")
    public ResponseEntity<?> replenish(@RequestBody WalletReplenishDto walletReplenishDto){
        try {
            return new ResponseEntity<>(walletService.replenish(walletReplenishDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
