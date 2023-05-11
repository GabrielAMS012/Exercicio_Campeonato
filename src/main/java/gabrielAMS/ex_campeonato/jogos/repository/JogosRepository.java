package gabrielAMS.ex_campeonato.jogos.repository;

import gabrielAMS.ex_campeonato.jogos.dto.DtoJogos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JogosRepository extends JpaRepository<DtoJogos, Integer> {

}
