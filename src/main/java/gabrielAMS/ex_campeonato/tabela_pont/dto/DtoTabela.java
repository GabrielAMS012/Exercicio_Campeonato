package gabrielAMS.ex_campeonato.tabela_pont.dto;

import gabrielAMS.ex_campeonato.campeonato.dto.DtoCampeonato;
import gabrielAMS.ex_campeonato.time.dto.DtoTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tabela_pont_tb")
public class DtoTabela {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_tabela")
    private long id_tabela;

    @OneToMany
    @JoinColumn(name = "cod_camp")
    private List<DtoCampeonato> campeonatos;

    @OneToMany
    @JoinColumn(name = "cod_time")
    private List<DtoTime> times;

    @Column(name = "qntd_pontos")
    private int pontuacao;
    @Column(name = "vitorias")
    private int qntd_vitorias;
    @Column(name = "derrotas")
    private int qntd_derrotas;
    @Column(name = "gols_marcados")
    private int gols_marcados;
    @Column(name = "gols_sofridos")
    private int gols_sofridos;
}
