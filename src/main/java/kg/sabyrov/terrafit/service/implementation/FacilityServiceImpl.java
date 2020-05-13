package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.entity.Facility;
import kg.sabyrov.terrafit.repository.FacilityRepository;
import kg.sabyrov.terrafit.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacilityServiceImpl implements FacilityService {
    @Autowired
    private FacilityRepository facilityRepository;

    @Override
    public Facility save(Facility facility) {
        return facilityRepository.save(facility);
    }

    @Override
    public Facility getById(Long id) {
        Optional<Facility> facilityOptional = facilityRepository.findById(id);
        return facilityOptional.orElse(null);
    }

    @Override
    public List<Facility> getAll() {
        return facilityRepository.findAll();
    }
}
