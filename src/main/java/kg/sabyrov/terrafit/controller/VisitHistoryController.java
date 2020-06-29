package kg.sabyrov.terrafit.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kg.sabyrov.terrafit.dto.visitDto.RequestTwoLocalDateTimeDto;
import kg.sabyrov.terrafit.service.VisitHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/visit-history")
@Api(tags = "'Visit-History' queries", value = "VisitHistoryQueries", description = "Controller for Visit-history queries")
public class VisitHistoryController {

    @Autowired
    private VisitHistoryService visitHistoryService;
    @ApiOperation(value = "FOR 'ADMIN' get all visitHistory models")
    @GetMapping
    public ResponseEntity<?> getAllModels(){
        try {
            return new ResponseEntity<>(visitHistoryService.getAllModels(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "FOR 'ADMIN' get visitHistory models in a time interval")
    @PostMapping
    public ResponseEntity<?> getAllByTwoTime(@RequestBody RequestTwoLocalDateTimeDto requestTwoLocalDateTimeDto){
        try {
            return new ResponseEntity<>(visitHistoryService.getAllVisitsBetweenTime(requestTwoLocalDateTimeDto), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "FOR 'ADMIN' get visit models by subscription id")
    @GetMapping("/subscription/{id}")
    public ResponseEntity<?> getVisitsBySubscriptionId(@PathVariable Long id){
        try {
            return new ResponseEntity<>(visitHistoryService.findAllBySubscription(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "FOR 'ADMIN' get all visit-history models by training group in time intervals")
    @PostMapping("/training-group/{id}")
    public ResponseEntity<?> getVisitsByTrainingGroupAndTwoTimes(@PathVariable Long id, @RequestBody RequestTwoLocalDateTimeDto requestTwoLocalDateTimeDto){
        try {
            return new ResponseEntity<>(visitHistoryService.findAllByTrainingGroupAndBetweenTime(id, requestTwoLocalDateTimeDto), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
