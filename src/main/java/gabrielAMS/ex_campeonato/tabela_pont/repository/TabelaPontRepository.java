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
                                    "WHERE tt.id_time = :id_time " +
                                    "AND tpt.id_camp = :id_camp")
    DomainTabelaPont getTimeByIdTimes(@Param("id_time") long id_time, @Param("id_camp") long id_camp);

    @Query(nativeQuery = true,
            value = "SELECT count(*) > 0 " +
                        "FROM tabela_pont_tb tpt " +
                            "WHERE tpt.id_camp = :id_camp " +
                                "AND tpt.time_id = :time_id ")
    boolean getIdCampByIdTabela(@Param("id_camp") long id_camp, @Param("time_id") long time_id);
    @Query(nativeQuery = true,
            value = "SELECT count(*) " +
                        "FROM tabela_pont_tb tpt " +
                            "WHERE id_camp = :id_camp")
    long numeroTimes(@Param("id_camp") long id_camp);

}
