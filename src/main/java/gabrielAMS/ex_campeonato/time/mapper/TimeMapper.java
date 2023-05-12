package gabrielAMS.ex_campeonato.time.mapper;

import gabrielAMS.ex_campeonato.time.dto.DtoTime;
import gabrielAMS.ex_campeonato.time.requests.TimePostRequestBody;
import gabrielAMS.ex_campeonato.time.requests.TimePutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class TimeMapper {

    public static final TimeMapper INSTANCE = Mappers.getMapper(TimeMapper.class);
    public abstract DtoTime toTime(TimePostRequestBody timePostRequestBody);
    public abstract DtoTime toTime(TimePutRequestBody timePutRequestBody);

}
