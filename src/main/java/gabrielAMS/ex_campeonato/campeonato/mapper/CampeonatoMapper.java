package gabrielAMS.ex_campeonato.campeonato.mapper;

import gabrielAMS.ex_campeonato.campeonato.dto.DtoCampeonato;
import gabrielAMS.ex_campeonato.campeonato.requests.CampeonatoPostRequestBody;
import gabrielAMS.ex_campeonato.campeonato.requests.CampeonatoPutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class CampeonatoMapper {
    public static final CampeonatoMapper INSTANCE = Mappers.getMapper(CampeonatoMapper.class);
    public abstract DtoCampeonato toCampeonato(CampeonatoPostRequestBody campeonatoPostRequestBody);
    public abstract DtoCampeonato toCampeonato(CampeonatoPutRequestBody campeonatoPutRequestBody);
}
