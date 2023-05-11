package gabrielAMS.ex_campeonato.jogos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JogoPostRequestBody {
    private int id;
    private int time_mandante;
    private int time_visitante;
    private int gols_mandante;
    private int gols_visitante;
}
