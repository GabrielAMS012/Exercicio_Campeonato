package gabrielAMS.ex_campeonato.tabela_pont.repository;

import gabrielAMS.ex_campeonato.tabela_pont.domain.DtoTabela;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TabelaPontRepository extends JpaRepository<DtoTabela, Long> {

}
