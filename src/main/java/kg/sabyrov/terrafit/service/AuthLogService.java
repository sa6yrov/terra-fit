package kg.sabyrov.terrafit.service;


import kg.sabyrov.terrafit.dto.authLogDto.AuthLogResponseModel;
import kg.sabyrov.terrafit.dto.authLogDto.RequestTwoTimeAndUserEmail;
import kg.sabyrov.terrafit.dto.visitDto.RequestTwoLocalDateTimeDto;
import kg.sabyrov.terrafit.entity.AuthLog;
import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.enums.Status;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface AuthLogService extends BaseService<AuthLog> {
    AuthLog create(String email, Status status);

//    Integer countAllByStatusAndUserAndRecovery(Status status, String email, boolean isRecovery);

    Integer countByUserAndStatus(Status status, String email, Integer isRecovered);

    List<AuthLog> findAllByUserAndStatus(String email, Status status);

    List<AuthLog> saveAll(List<AuthLog> authLogs);

    List<AuthLogResponseModel> getAllByCreatedDateBetweenAndUser(RequestTwoTimeAndUserEmail requestTwoTimeAndUserEmail);

    List<AuthLogResponseModel> getAllByCreatedDateBetween(RequestTwoLocalDateTimeDto twoLocalDateTimeDto);

    List<AuthLogResponseModel> getAllModels();
}
