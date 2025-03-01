package gabrielAMS.ex_campeonato.jogos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Builder
public class DtoJogos {
    private Long id_campeonato;
    @NotNull
    private long time_mandante;
    @NotNull
    private long time_visitante;
    @Min(value = 0)
    private int gols_mandante;
    @Min(value = 0)
    private int gols_visitante;
    @NotNull
    private Date dataJogo;
    private boolean jogoIniciado = false;
    private boolean jogoFinalizado = false;
}
