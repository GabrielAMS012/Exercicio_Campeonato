package gabrielAMS.ex_campeonato.tabela_pont.mapper;

import gabrielAMS.ex_campeonato.tabela_pont.dto.DtoTabela;
import gabrielAMS.ex_campeonato.tabela_pont.requests.TabelaPontPostRequestBody;
import gabrielAMS.ex_campeonato.tabela_pont.requests.TabelaPontPutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import javax.persistence.Column;
@Mapper
public abstract class TabelaPontMapper {
    public static final TabelaPontMapper INSTANCE = Mappers.getMapper(TabelaPontMapper.class);
    public abstract DtoTabela toTabela(TabelaPontPostRequestBody tabelaPontPostRequestBody);
    public abstract DtoTabela toTabela(TabelaPontPutRequestBody tabelaPontPutRequestBody);
}
