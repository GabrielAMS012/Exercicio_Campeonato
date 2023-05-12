package gabrielAMS.ex_campeonato.campeonato.dto;

import gabrielAMS.ex_campeonato.jogos.dto.DtoJogos;
import gabrielAMS.ex_campeonato.tabela_pont.dto.DtoTabela;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "campeonatos_tb")
public class DtoCampeonato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_campeonato")
    private int id_campeonato;

    @NotBlank
    @Column(name = "nome_campeonato")
    private String nome_camp;

    @NotBlank
    @Column(name = "ano_campeonato")
    private int ano;

    @NotBlank
    @Column(name = "Status")
    private boolean iniciado;

}
