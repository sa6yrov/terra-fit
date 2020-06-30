package kg.sabyrov.terrafit.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kg.sabyrov.terrafit.dto.requestForFreezeDto.RequestApprovingCancellingDto;
import kg.sabyrov.terrafit.dto.requestForFreezeDto.RequestFreezeDto;
import kg.sabyrov.terrafit.service.RequestFreezeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscription/freeze-request")
@Api(tags = "'Request for freezing subscription' queries", value = "RequestFreezeQuaries", description = "Controller for RequestFreeze queries")
public class RequestFreezeController {

    @Autowired
    private RequestFreezeService requestFreezeService;

    @ApiOperation(value = "FOR 'USER' - send request for freezing")
    @PostMapping
    public ResponseEntity<?> freezing(@RequestBody RequestFreezeDto requestFreezeDto){
        try {
            return new ResponseEntity<>(requestFreezeService.create(requestFreezeDto), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "FOR 'ADMIN' - get all requestFreeze models by status")
    @GetMapping
    public ResponseEntity<?> getAll(){
        try {
            return new ResponseEntity<>(requestFreezeService.getAllModelByStatusConsideration(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "FOR 'ADMIN' - approving requests")
    @PostMapping("/approving")
    public ResponseEntity<?> approving(@RequestBody RequestApprovingCancellingDto requestApprovingCancellingDto){
        try {
            return new ResponseEntity<>(requestFreezeService.approving(requestApprovingCancellingDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "FOR 'ADMIN' - cancelling requests")
    @PostMapping("/cancelling")
    public ResponseEntity<?> cancelling(@RequestBody RequestApprovingCancellingDto requestApprovingCancellingDto){
        try {
            return new ResponseEntity<>(requestFreezeService.cancelling(requestApprovingCancellingDto), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
