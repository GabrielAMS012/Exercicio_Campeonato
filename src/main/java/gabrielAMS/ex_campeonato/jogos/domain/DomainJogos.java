package gabrielAMS.ex_campeonato.jogos.domain;

import gabrielAMS.ex_campeonato.campeonato.domain.DomainCampeonato;
import gabrielAMS.ex_campeonato.time.domain.DomainTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "jogos_tb")
public class DomainJogos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jogo")
    private long id_jogo;

    @ManyToOne
    @JoinColumn(name ="id_campeonato")
    private DomainCampeonato idCampeonato;

    @ManyToOne
    @JoinColumn(name = "id_time_mandante")
    private DomainTime time_mandante;

    @ManyToOne
    @JoinColumn(name = "id_time_visitante")
    private DomainTime time_visitante;

    @Column(name = "gols_mandante")
    private int gols_mandante;
    @Column(name = "gols_visitante")
    private int gols_visitante;

    @Column(name = "data_jogo")
    private Date dataJogo;
}
