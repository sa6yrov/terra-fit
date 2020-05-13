package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.entity.Role;
import kg.sabyrov.terrafit.repository.RoleRepository;
import kg.sabyrov.terrafit.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role getById(Long id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        return roleOptional.orElse(null);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

}
