package gabrielAMS.ex_campeonato.time.repository;

import gabrielAMS.ex_campeonato.time.domain.DomainTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeRepository extends JpaRepository<DomainTime, Long> {
    boolean existsByNomeTime(String nome_time);
}
