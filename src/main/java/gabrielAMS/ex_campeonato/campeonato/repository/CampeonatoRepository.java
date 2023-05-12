package gabrielAMS.ex_campeonato.campeonato.repository;

import gabrielAMS.ex_campeonato.campeonato.dto.DtoCampeonato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampeonatoRepository extends JpaRepository<DtoCampeonato, Integer> {

}
