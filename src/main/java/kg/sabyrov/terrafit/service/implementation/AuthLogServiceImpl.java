package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.entity.AuthLog;
import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.enums.Status;
import kg.sabyrov.terrafit.repository.AuthLogRepository;
import kg.sabyrov.terrafit.service.AuthLogService;
import kg.sabyrov.terrafit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthLogServiceImpl implements AuthLogService {

    private final AuthLogRepository authLogRepository;
    private final UserService userService;

    @Autowired
    public AuthLogServiceImpl(AuthLogRepository authLogRepository, UserService userService) {
        this.authLogRepository = authLogRepository;
        this.userService = userService;
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

    @Override
    public AuthLog create(String email, Status status) {
        User user = userService.findByEmail(email);
        AuthLog authLog = AuthLog.builder()
                .user(user)
                .authorizationDate(new Date())
                .status(status)
                .build();

        return save(authLog);
    }
}
