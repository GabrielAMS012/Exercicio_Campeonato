package gabrielAMS.ex_campeonato.time.repository;

import gabrielAMS.ex_campeonato.time.dto.DtoTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeRepository extends JpaRepository<DtoTime, Integer> {

}
