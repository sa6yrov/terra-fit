package kg.sabyrov.terrafit.service;


import kg.sabyrov.terrafit.entity.AuthLog;
import kg.sabyrov.terrafit.enums.Status;

public interface AuthLogService extends BaseService<AuthLog> {
    AuthLog create(String email, Status status);
}
