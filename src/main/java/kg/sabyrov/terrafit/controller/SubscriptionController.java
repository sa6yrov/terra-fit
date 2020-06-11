package kg.sabyrov.terrafit.controller;

import kg.sabyrov.terrafit.dto.subscriptionDto.SubscriptionRequestDto;
import kg.sabyrov.terrafit.service.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;


    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        try {
            return new ResponseEntity<>(subscriptionService.getAllModels(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody SubscriptionRequestDto subscriptionRequestDto){
        try {
            return new ResponseEntity<>(subscriptionService.create(subscriptionRequestDto), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/find")
    private ResponseEntity<?> getModelById(@RequestBody Long id){
        try {
            return new ResponseEntity<>(subscriptionService.getModelById(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
