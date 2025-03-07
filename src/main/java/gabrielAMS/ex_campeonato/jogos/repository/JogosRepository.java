package gabrielAMS.ex_campeonato.jogos.repository;

import gabrielAMS.ex_campeonato.jogos.domain.DomainJogos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface JogosRepository extends JpaRepository<DomainJogos, Long> {

    @Query(nativeQuery = true,
            value = "SELECT COUNT(*) > 0 " +
    "FROM jogos_tb j " +
    "   WHERE j.id_campeonato = :id_campeonato " +
    "   AND j.id_time_mandante = :id_time_mandante" +
    "   AND j.id_time_visitante = :id_time_visitante")
    boolean jogoExiste(@Param("id_campeonato") long id_campeonato,
                          @Param("id_time_mandante") long id_time_mandante,
                          @Param("id_time_visitante") long id_time_visitante);

    @Query(nativeQuery = true,
            value = "SELECT COUNT(*) > 0" +
                "       FROM jogos_tb j " +
                "           WHERE j.data_jogo = :dataJogo " +
                "           AND (j.id_time_mandante = :id_time_mandante) " +
                "           OR j.data_jogo = :dataJogo " +
                "           AND j.id_time_visitante = :id_time_visitante " +
                "           OR j.data_jogo = :dataJogo " +
                "           AND j.id_time_visitante = :id_time_mandante " +
                "           OR j.data_jogo = :dataJogo " +
                "           AND j.id_time_mandante = :id_time_visitante ")
    boolean findDomainJogosByDataJogo(@Param("dataJogo")Date dataJogo,
                                       @Param("id_time_mandante") long id_time_mandante,
                                       @Param("id_time_visitante") long id_time_visitante);

    @Query(nativeQuery = true,
            value = "SELECT nome_time " +
                    "FROM times_tb tt " +
                    "JOIN jogos_tb jt on tt.id_time = jt.id_time_mandante " +
                        "WHERE id_time_mandante = :id_time_mandante")
    String getNome_timeByIdTimeMandante(@Param("id_time_mandante") long id_time_mandante);

}
