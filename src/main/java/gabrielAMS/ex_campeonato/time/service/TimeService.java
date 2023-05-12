package gabrielAMS.ex_campeonato.time.service;

import gabrielAMS.ex_campeonato.exception.BadRequestException;
import gabrielAMS.ex_campeonato.time.dto.DtoTime;
import gabrielAMS.ex_campeonato.time.mapper.TimeMapper;
import gabrielAMS.ex_campeonato.time.repository.TimeRepository;
import gabrielAMS.ex_campeonato.time.requests.TimePostRequestBody;
import gabrielAMS.ex_campeonato.time.requests.TimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeService {
    private final TimeRepository timeRepository;

    public List<DtoTime> listAll(){
        return timeRepository.findAll();
    }

    public DtoTime findTimeByIdOrThrowBadRequest(int id){
        return timeRepository.findById(id).orElseThrow(()-> new BadRequestException("Time não encontrado"));
    }


    public DtoTime save(TimePostRequestBody timePostRequestBody){
        return timeRepository.save(TimeMapper.INSTANCE.toTime(timePostRequestBody));
    }

    public void delete(int id){
        timeRepository.delete(findTimeByIdOrThrowBadRequest(id));
    }

    public void replace(TimePutRequestBody timePutRequestBody){
        DtoTime timeSalvo = findTimeByIdOrThrowBadRequest(timePutRequestBody.getId_time());
        DtoTime time = TimeMapper.INSTANCE.toTime(timePutRequestBody);
        time.setId_time(timeSalvo.getId_time());
        timeRepository.save(time);
    }

}
