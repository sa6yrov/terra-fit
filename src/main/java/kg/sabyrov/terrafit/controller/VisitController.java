package kg.sabyrov.terrafit.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kg.sabyrov.terrafit.dto.visitDto.VisitRequestTimeDto;
import kg.sabyrov.terrafit.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/visit")
@Api(tags = "'Visit' queries", value = "VisitQueries", description = "Controller for Visit queries")
public class VisitController {
    @Autowired
    private VisitService visitService;

    @ApiOperation(value = "FOR 'ADMIN' get all models")
    @GetMapping
    public ResponseEntity<?> getAllModels(){
        try {
            return new ResponseEntity<>(visitService.getAllModels(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "FOR 'ADMIN' get visit models in a time interval")
    @PostMapping
    public ResponseEntity<?> getAllByTwoTime(@RequestBody VisitRequestTimeDto visitRequestTimeDto){
        try {
            return new ResponseEntity<>(visitService.getAllVisitsBetweenTime(visitRequestTimeDto), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "FOR 'ADMIN' get visit models by subscription id")
    @GetMapping("/subscription/{id}")
    public ResponseEntity<?> getVisitsBySubscriptionId(@PathVariable Long id){
        try {
            return new ResponseEntity<>(visitService.findAllBySubscription(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "FOR 'ADMIN' get all visit models by training group in time intervals")
    @PostMapping("/training-group/{id}")
    public ResponseEntity<?> getVisitsByTrainingGroupAndTwoTimes(@PathVariable Long id, VisitRequestTimeDto visitRequestTimeDto){
        try {
            return new ResponseEntity<>(visitService.findAllByTrainingGroupAndBetweenTime(id, visitRequestTimeDto), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
