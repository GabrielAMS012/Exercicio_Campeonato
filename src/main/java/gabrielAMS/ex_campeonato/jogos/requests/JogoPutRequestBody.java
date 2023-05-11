package gabrielAMS.ex_campeonato.jogos.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JogoPutRequestBody {
    private int id;
    private int time_mandante;
    private int time_visitante;
    private int gols_mandante;
    private int gols_visitante;
}
