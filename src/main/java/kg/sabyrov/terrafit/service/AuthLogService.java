package kg.sabyrov.terrafit.service;


import kg.sabyrov.terrafit.entity.AuthLog;
import kg.sabyrov.terrafit.enums.Status;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;

public interface AuthLogService extends BaseService<AuthLog> {
    AuthLog create(String email, Status status) throws UserNotFoundException;
}
