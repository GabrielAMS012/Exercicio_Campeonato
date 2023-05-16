package gabrielAMS.ex_campeonato.campeonato.repository;

import gabrielAMS.ex_campeonato.campeonato.domain.DomainCampeonato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CampeonatoRepository extends JpaRepository<DomainCampeonato, Long> {

    boolean existsByNomeCampAndAno(String nomeCamp, int ano);

    @Query(nativeQuery = true,
    value = "SELECT COUNT(*) > 0 " +
            "from campeonatos_tb ct " +
            "WHERE ct.id_campeonato = :id_campeonato " +
            "   AND ct.iniciado = true")
    boolean campeonatoIniciado(@Param("id_campeonato") long id_campeonato);

    @Query(nativeQuery = true,
            value = "SELECT COUNT(*) > 0 " +
                    "from campeonatos_tb ct " +
                    "WHERE ct.id_campeonato = :id_campeonato " +
                    "   AND ct.finalizado = true")
    boolean campeonatoFinalizado(long id_campeonato);
}
