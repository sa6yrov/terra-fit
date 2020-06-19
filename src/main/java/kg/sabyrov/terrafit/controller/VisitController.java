package kg.sabyrov.terrafit.controller;

import kg.sabyrov.terrafit.dto.visitDto.VisitDto;
import kg.sabyrov.terrafit.dto.visitDto.VisitRequestTimeDto;
import kg.sabyrov.terrafit.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visit")
public class VisitController {
    @Autowired
    private VisitService visitService;


    @PostMapping
    public ResponseEntity<?> create(@RequestBody VisitDto visitDto){
        try {
            return new ResponseEntity<>(visitService.create(visitDto), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/list/between")
    public ResponseEntity<?> getVisitsBetweenDate(@RequestBody VisitRequestTimeDto visitRequestTimeDto){
        try {
            return new ResponseEntity<>(visitService.getAllVisitsBetweenTime(visitRequestTimeDto), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
