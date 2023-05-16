package gabrielAMS.ex_campeonato.tabela_pont.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TabelaPontPostRequestBody {

    private int pontuacao;
    private int qntd_vitorias;
    private int qntd_derrotas;
    private int gols_marcados;
    private int gols_sofridos;
}
