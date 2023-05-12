package gabrielAMS.ex_campeonato.jogos.dto;

import gabrielAMS.ex_campeonato.campeonato.dto.DtoCampeonato;
import gabrielAMS.ex_campeonato.time.dto.DtoTime;
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
@Table(name = "jogos_tb")
public class DtoJogos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jogo")
    private int id;

    @ManyToOne
    @JoinColumn(name ="id_campeonato")
    private DtoCampeonato campeonato;

    @ManyToOne
    @JoinColumn(name = "id_time_mandante")
    private DtoTime time_mandante;

    @ManyToOne
    @JoinColumn(name = "id_time_visitante")
    private DtoTime time_visitante;

    @Column(name = "gols_mandante")
    private int gols_mandante;
    @Column(name = "gols_visitante")
    private int gols_visitante;

}
