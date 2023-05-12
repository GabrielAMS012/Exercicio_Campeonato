package gabrielAMS.ex_campeonato.campeonato.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CampeonatoPutRequestBody {
    private int id_campeonato;
    private String nome_camp;
    private int ano;
    private boolean iniciado;
}
