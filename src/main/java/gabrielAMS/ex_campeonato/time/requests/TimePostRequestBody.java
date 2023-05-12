package gabrielAMS.ex_campeonato.time.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimePostRequestBody {
    private String nome_time;
}
