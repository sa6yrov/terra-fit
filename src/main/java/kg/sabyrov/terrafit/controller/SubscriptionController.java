package kg.sabyrov.terrafit.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kg.sabyrov.terrafit.dto.subscriptionDto.SubscriptionRequestDto;
import kg.sabyrov.terrafit.dto.subscriptionDto.TotalAmountByGroupDto;
import kg.sabyrov.terrafit.dto.visitDto.VisitDto;
import kg.sabyrov.terrafit.dto.visitDto.VisitRequestTimeDto;
import kg.sabyrov.terrafit.exceptions.SubscriptionNotFoundException;
import kg.sabyrov.terrafit.service.SubscriptionService;
import kg.sabyrov.terrafit.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscription")
@Api(tags = "'Subscription' queries", value = "SubscriptionQueries", description = "Controller for Subscription queries")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private VisitService visitService;

    @ApiOperation(value = "FOR 'ADMIN' - get all subscription models")
    @GetMapping
    public ResponseEntity<?> getAll(){
        try {
            return new ResponseEntity<>(subscriptionService.getAllModels(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//    @PostMapping
//    public ResponseEntity<?> create(@RequestBody SubscriptionRequestDto subscriptionRequestDto){
//        try {
//            return new ResponseEntity<>(subscriptionService.create(subscriptionRequestDto), HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }

    @ApiOperation(value = "FOR 'ADMIN' - get subscription by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getModelById(@PathVariable Long id){
        try {
            return new ResponseEntity<>(subscriptionService.findModelById(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation("FOR 'ADMIN' - create user's visit by subscription id")
    @PostMapping("/visit")
    public ResponseEntity<?> userVisit(@RequestBody VisitDto visitDto){
        try {
            return new ResponseEntity<>(visitService.create(visitDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation("FOR 'USER' - get user's subscriptions")
    @GetMapping("/my")
    public ResponseEntity<?> getAllByUser(){
        try {
            return new ResponseEntity<>(subscriptionService.getAllByUser(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "FOR 'ADMIN' - get subscription's total amount between two time")
    @PostMapping("/total-amount")
    private ResponseEntity<?> getTotalAmount(@RequestBody VisitRequestTimeDto visitRequestTimeDto){
        try {
            return new ResponseEntity<>(subscriptionService.getTotalAmountByTwoDate(visitRequestTimeDto), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "FOR 'ADMIN' - get subscription's total amount by training group between two time")
    @PostMapping("/training-group/total-amount")
    private ResponseEntity<?> getTotalAmountByGroup(@RequestBody TotalAmountByGroupDto totalAmountByGroupDto){
        try {
            return new ResponseEntity<>(subscriptionService.getSumByTwoDateAndGroup(totalAmountByGroupDto), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
