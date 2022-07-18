package com.example.apiqr.repository;

import com.example.apiqr.entity.Boleto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoletoRepository extends JpaRepository<Boleto,Integer> {
    Optional<Boleto> findByCodigoaleatorio(String codigo);
}
