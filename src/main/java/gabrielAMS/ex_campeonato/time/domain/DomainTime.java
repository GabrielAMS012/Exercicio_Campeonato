package gabrielAMS.ex_campeonato.time.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "times_tb")
public class DomainTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_time")
    private long id_time;

    @Column(name = "nome_time")
    private String nomeTime;

}
