package kg.sabyrov.terrafit.controller;

import kg.sabyrov.terrafit.entity.Facility;
import kg.sabyrov.terrafit.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@RestController
@RequestMapping("/facility")
public class FacilityController {
    @Autowired
    private FacilityService facilityService;

    @PostMapping
    public ResponseEntity<Facility> create(Facility facility){
        return new ResponseEntity<>(facilityService.save(facility), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Facility>> getAll(){
        return new ResponseEntity<>(facilityService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Facility> getById(@PathVariable Long id){
        return new ResponseEntity<>(facilityService.getById(id), HttpStatus.OK);
    }

}
