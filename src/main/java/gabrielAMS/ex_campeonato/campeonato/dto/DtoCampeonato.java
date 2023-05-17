package gabrielAMS.ex_campeonato.campeonato.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DtoCampeonato {
    private long id_campeonato;

    private List<Long> idsTimes;
}
