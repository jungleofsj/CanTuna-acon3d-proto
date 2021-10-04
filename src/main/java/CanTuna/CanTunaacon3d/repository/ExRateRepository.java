package CanTuna.CanTunaacon3d.repository;

import CanTuna.CanTunaacon3d.domain.ExRate;

import java.util.List;
import java.util.Optional;

public interface ExRateRepository {

    ExRate exRate = new ExRate();

    Optional<ExRate> getRatebyCountry(Integer currencyType);

}
