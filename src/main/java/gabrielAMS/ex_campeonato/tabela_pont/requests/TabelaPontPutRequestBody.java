package gabrielAMS.ex_campeonato.tabela_pont.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TabelaPontPutRequestBody {
    private long id_tabela;
    private int pontuacao;
    private int qntd_vitorias;
    private int qntd_derrotas;
    private int gols_marcados;
    private int gols_sofridos;
}
