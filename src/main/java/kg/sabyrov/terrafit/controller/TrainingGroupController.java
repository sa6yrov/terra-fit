package kg.sabyrov.terrafit.controller;

import kg.sabyrov.terrafit.dto.subscriptionDto.SubscriptionRequestDto;
import kg.sabyrov.terrafit.dto.trainingsectionDto.TrainingGroupRequestDto;
import kg.sabyrov.terrafit.service.SubscriptionService;
import kg.sabyrov.terrafit.service.TrainingGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/training-group")
public class TrainingGroupController {
    @Autowired
    private TrainingGroupService trainingGroupService;

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping
    public ResponseEntity<?> getAllModels(){
        try {
            return new ResponseEntity<>(trainingGroupService.getAllModel(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TrainingGroupRequestDto trainingGroupRequestDto){
        try{
            return new ResponseEntity<>(trainingGroupService.create(trainingGroupRequestDto), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/subscription/buy")
    public ResponseEntity<?> buy (@RequestBody SubscriptionRequestDto subscriptionRequestDto){
        try{
            return new ResponseEntity<>(subscriptionService.create(subscriptionRequestDto), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
