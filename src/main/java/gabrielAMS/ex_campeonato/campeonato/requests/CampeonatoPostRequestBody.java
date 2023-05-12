package gabrielAMS.ex_campeonato.campeonato.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CampeonatoPostRequestBody {

    private String nome_camp;
    private int ano;
    private boolean iniciado;
}
