package gabrielAMS.ex_campeonato.tabela_pont.repository;

import gabrielAMS.ex_campeonato.tabela_pont.dto.DtoTabela;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TabelaPontRepository extends JpaRepository<DtoTabela, Integer> {

}
