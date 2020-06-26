package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.dto.authLogDto.AuthLogResponseModel;
import kg.sabyrov.terrafit.dto.authLogDto.RequestTwoTimeAndUserEmail;
import kg.sabyrov.terrafit.dto.visitDto.RequestTwoLocalDateTimeDto;
import kg.sabyrov.terrafit.entity.AuthLog;
import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.enums.Status;
import kg.sabyrov.terrafit.repository.AuthLogRepository;
import kg.sabyrov.terrafit.service.AuthLogService;
import kg.sabyrov.terrafit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthLogServiceImpl implements AuthLogService {

    @Autowired
    private AuthLogRepository authLogRepository;
    @Autowired
    private UserService userService;

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
    public AuthLog create(String email, Status status){
        return save(AuthLog.builder()
                .user(getUserFromDbByEmail(email))
                .status(status)
                .isRecovered(0)
                .build());
    }


    @Override
    public Integer countByUserAndStatus(Status status, String email, Integer isRecovered) {
        return authLogRepository.getCount(status, getUserFromDbByEmail(email), isRecovered);
    }

    @Override
    public List<AuthLog> findAllByUserAndStatus(String email, Status status) {
        return authLogRepository.findAllByUserAndStatus(getUserFromDbByEmail(email), status);
    }

    @Override
    public List<AuthLog> saveAll(List<AuthLog> authLogs) {
        return authLogRepository.saveAll(authLogs);
    }

    @Override
    public List<AuthLogResponseModel> getAllByCreatedDateBetweenAndUser(RequestTwoTimeAndUserEmail requestTwoTimeAndUserEmail) {
        return authLogRepository.findAllByCreatedDateBetweenAndUser(
                requestTwoTimeAndUserEmail.getFrom(),
                requestTwoTimeAndUserEmail.getTo(),
                getUserFromDbByEmail(requestTwoTimeAndUserEmail.getEmail())
        ).stream().map(this::mapAuthLogToModel).collect(Collectors.toList());
    }

    @Override
    public List<AuthLogResponseModel> getAllByCreatedDateBetween(RequestTwoLocalDateTimeDto twoLocalDateTimeDto) {
        return authLogRepository.findAllByCreatedDateBetween(
                twoLocalDateTimeDto.getFrom(),
                twoLocalDateTimeDto.getTo()
        ).stream().map(this::mapAuthLogToModel).collect(Collectors.toList());
    }

    @Override
    public List<AuthLogResponseModel> getAllModels() {
        return getAll()
                .stream()
                .map(this::mapAuthLogToModel)
                .collect(Collectors.toList());
    }

    private User getUserFromDbByEmail(String email){
        return userService.findByEmail(email);
    }

    private AuthLogResponseModel mapAuthLogToModel(AuthLog authLog){
        return AuthLogResponseModel.builder()
                .userEmail(authLog.getUser().getEmail())
                .status(authLog.getStatus())
                .createdDate(authLog.getCreatedDate())
                .build();
    }
}
