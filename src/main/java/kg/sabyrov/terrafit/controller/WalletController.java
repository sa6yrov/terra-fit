package kg.sabyrov.terrafit.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kg.sabyrov.terrafit.dto.walletDto.WalletReplenishDto;
import kg.sabyrov.terrafit.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
@Api(tags = "'Wallet' queries", value = "WalletQueries", description = "Controller for Wallet queries")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @ApiOperation(value = "FOR 'USER' - get wallet")
    @GetMapping("/my")
    public ResponseEntity<?> getWallet(){
        try {
            return new ResponseEntity<>(walletService.getWalletModelByUser(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "FOR 'USER' - replenish wallet")
    @PostMapping("/my/replenish")
    public ResponseEntity<?> replenish(@RequestBody WalletReplenishDto walletReplenishDto){
        try {
            return new ResponseEntity<>(walletService.replenish(walletReplenishDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
