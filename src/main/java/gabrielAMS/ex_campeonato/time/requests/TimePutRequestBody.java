package gabrielAMS.ex_campeonato.time.requests;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TimePutRequestBody {
    private int id_time;
    private int cod_time;
    private String nome_time;
}