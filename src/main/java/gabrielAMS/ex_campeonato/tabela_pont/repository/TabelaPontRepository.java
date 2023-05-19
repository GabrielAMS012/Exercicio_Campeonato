package gabrielAMS.ex_campeonato.tabela_pont.repository;

import gabrielAMS.ex_campeonato.tabela_pont.domain.DomainTabelaPont;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface TabelaPontRepository extends JpaRepository<DomainTabelaPont, Long> {
    @Query(nativeQuery = true,
            value = "SELECT * " +
                        "FROM tabela_pont_tb tpt " +
                            "JOIN times_tb tt " +
                                "ON tt.id_time = tpt.time_id " +
                                    "WHERE tt.id_time = :id_time")
    DomainTabelaPont getTimeByIdTimes(@Param("id_time") long id_time);

    @Query(nativeQuery = true,
            value = "SELECT id_tabela " +
                        "FROM tabela_pont_tb tpt " +
                            "WHERE time_id = :time_id ")
    long getIdTabelaByIdTime(@Param("time_id") long time_id);

    @Query(nativeQuery = true,
            value = "SELECT count(*) " +
                        "FROM tabela_pont_tb tpt " +
                            "WHERE tpt.id_camp = :id_camp " +
                                "AND tpt.id_tabela = :id_tabela ")
    boolean getIdCampByIdTabela(@Param("id_camp") long id_camp, @Param("id_tabela" ) long id_tabela);

}
