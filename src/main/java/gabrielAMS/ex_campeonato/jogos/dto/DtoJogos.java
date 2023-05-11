package gabrielAMS.ex_campeonato.jogos.dto;

import gabrielAMS.ex_campeonato.campeonato.dto.DtoCampeonato;
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
    @OneToOne
    @JoinColumn(name ="cod_jogo")
    private DtoCampeonato campeonato;

    @Column(name = "time_mandante")
    private int time_mandante;
    @Column(name = "time_visitante")
    private int time_visitante;
    @Column(name = "gols_mandante")
    private int gols_mandante;
    @Column(name = "gols_visitante")
    private int gols_visitante;

}
