package gabrielAMS.ex_campeonato.jogos.mapper;

import gabrielAMS.ex_campeonato.jogos.dto.DtoJogos;
import gabrielAMS.ex_campeonato.jogos.requests.JogoPostRequestBody;
import gabrielAMS.ex_campeonato.jogos.requests.JogoPutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class JogoMapper {
    public static final JogoMapper INSTANCE = Mappers.getMapper(JogoMapper.class);
    public abstract DtoJogos toJogo(JogoPostRequestBody jogoPostRequestBody);
    public abstract DtoJogos toJogo(JogoPutRequestBody jogoPutRequestBody);
}
