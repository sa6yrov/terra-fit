package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.entity.AuthLog;
import kg.sabyrov.terrafit.repository.AuthLogRepository;
import kg.sabyrov.terrafit.service.AuthLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthLogServiceImpl implements AuthLogService {

    private final AuthLogRepository authLogRepository;

    @Autowired
    public AuthLogServiceImpl(AuthLogRepository authLogRepository) {
        this.authLogRepository = authLogRepository;
    }

    @Override
    public AuthLog save(AuthLog authLog) {
        return authLogRepository.save(authLog);
    }

    @Override
    public AuthLog getById(Long id) {
        Optional<AuthLog> authLogOptional = authLogRepository.findById(id);
        return authLogOptional.orElse(null);
    }

    @Override
    public List<AuthLog> getAll() {
        return authLogRepository.findAll();
    }
}
