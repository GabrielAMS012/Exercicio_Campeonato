package gabrielAMS.ex_campeonato.time.service;

import gabrielAMS.ex_campeonato.time.domain.DomainTime;
import gabrielAMS.ex_campeonato.time.repository.TimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeService {
    private final TimeRepository timeRepository;

    @Transactional(readOnly = true)
    public List<DomainTime> listAll(){
        return timeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public DomainTime findTimeByIdOrThrowBadRequest(long id){
        return timeRepository.findById(id).orElseThrow(()-> new RuntimeException("Time " + id + " não encontrado"));
    }

    @Transactional
    public DomainTime saveTime(DomainTime domainTime){
        return timeRepository.save(domainTime);
    }

    @Transactional
    public void delete(long id){
        timeRepository.delete(findTimeByIdOrThrowBadRequest(id));
    }

    @Transactional
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
