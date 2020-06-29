package kg.sabyrov.terrafit.repository;

import kg.sabyrov.terrafit.entity.RequestFreeze;
import kg.sabyrov.terrafit.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestFreezeRepository extends JpaRepository<RequestFreeze, Long> {
    List<RequestFreeze> findAllByStatus(Status status);

}
