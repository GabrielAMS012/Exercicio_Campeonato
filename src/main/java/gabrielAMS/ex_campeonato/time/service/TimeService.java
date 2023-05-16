package gabrielAMS.ex_campeonato.time.service;

import gabrielAMS.ex_campeonato.exception.BadRequestException;
import gabrielAMS.ex_campeonato.time.domain.DomainTime;
import gabrielAMS.ex_campeonato.time.repository.TimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeService {
    private final TimeRepository timeRepository;

    public List<DomainTime> listAll(){
        return timeRepository.findAll();
    }

    public DomainTime findTimeByIdOrThrowBadRequest(long id){
        return timeRepository.findById(id).orElseThrow(()-> new BadRequestException("Time não encontrado"));
    }

    @Transactional
    public DomainTime saveTime(DomainTime domainTime){
        return timeRepository.save(domainTime);
    }

    public void delete(long id){
        timeRepository.delete(findTimeByIdOrThrowBadRequest(id));
    }

    public DomainTime replace(DomainTime domainTime){
        validateTimeExists(domainTime);
        return timeRepository.save(domainTime);
    }

    public void validateTimeExists(DomainTime domainTime){
        if(timeRepository.existsByNomeTime(domainTime.getNomeTime())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time já cadastrado");
        }
    }

}
