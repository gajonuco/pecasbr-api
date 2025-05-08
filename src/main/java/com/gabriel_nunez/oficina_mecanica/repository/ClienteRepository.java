package com.gabriel_nunez.oficina_mecanica.repository;

import com.gabriel_nunez.oficina_mecanica.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCpfCnpj(String cpfCnpj);

    Optional<Cliente> findByTelefone(String telefone);
    Optional<Cliente> findByEmail(String email);

}
