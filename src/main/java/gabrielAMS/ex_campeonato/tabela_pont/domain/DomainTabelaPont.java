package gabrielAMS.ex_campeonato.tabela_pont.domain;

import gabrielAMS.ex_campeonato.campeonato.domain.DomainCampeonato;
import gabrielAMS.ex_campeonato.time.domain.DomainTime;
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
@Table(name = "tabela_pont_tb")
public class DomainTabelaPont {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_tabela")
    private long id_tabela;

    @ManyToOne
    @JoinColumn(name = "id_camp")
    private DomainCampeonato campeonato;

    @ManyToOne
    @JoinColumn(name = "time_id")
    private DomainTime times;

    @Column(name = "qntd_pontos")
    private int pontuacao;
    @Column(name = "vitorias")
    private int qntd_vitorias;
    @Column(name = "derrotas")
    private int qntd_derrotas;
    @Column(name = "empates")
    private int qntd_empates;
    @Column(name = "gols_marcados")
    private int gols_marcados;
    @Column(name = "gols_sofridos")
    private int gols_sofridos;
}
