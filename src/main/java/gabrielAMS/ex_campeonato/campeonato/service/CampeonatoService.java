package gabrielAMS.ex_campeonato.campeonato.service;

import gabrielAMS.ex_campeonato.campeonato.domain.DomainCampeonato;
import gabrielAMS.ex_campeonato.campeonato.dto.DtoCampeonato;

import gabrielAMS.ex_campeonato.campeonato.repository.CampeonatoRepository;
import gabrielAMS.ex_campeonato.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CampeonatoService {

    private final CampeonatoRepository campeonatoRepository;

    public List<DomainCampeonato> listAll(){
        return campeonatoRepository.findAll();
    }

    public DomainCampeonato findCampByIdOrThrowBadRequest(long id){
        return campeonatoRepository.findById(id).orElseThrow(()-> new BadRequestException("Campeonato não encontrado"));
    }

    public DomainCampeonato save(DomainCampeonato domainCampeonato){
        validateNewCampeonato(domainCampeonato);
        return campeonatoRepository.save(domainCampeonato);
    }

    public void delete(long id){
        campeonatoRepository.delete(findCampByIdOrThrowBadRequest(id));
    }

    public DomainCampeonato replace(long id, DomainCampeonato domainCampeonato){
        validateNewCampeonato(domainCampeonato);
        DomainCampeonato campeonatoSalvo = this.campeonatoRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST));
        campeonatoSalvo.setNomeCamp(domainCampeonato.getNomeCamp());
        campeonatoSalvo.setAno(domainCampeonato.getAno());
        return this.campeonatoRepository.save(campeonatoSalvo);


    }
    public void validateNewCampeonato(DomainCampeonato domainCampeonato){
        validateIfCampeonatoExists(domainCampeonato);
        validateAno(domainCampeonato);
        validateStatusCampeonato(domainCampeonato);
    }
    public void validateIfCampeonatoExists(DomainCampeonato domainCampeonato){
        if(campeonatoRepository.existsByNomeCampAndAno(domainCampeonato.getNomeCamp(),
                domainCampeonato.getAno())){
            throw new RuntimeException("Campeonato já existe");
        }
    }

    public void validateAno(DomainCampeonato domainCampeonato){
        LocalDateTime now = LocalDateTime.now();

        if(domainCampeonato.getAno() < now.getYear()){
            throw new RuntimeException("Não é possível criar um campeonato com a data anterior a de hoje");
        }
    }

    public void validateStatusCampeonato(DomainCampeonato domainCampeonato){
        if(domainCampeonato.isCampeonatoIniciado() || domainCampeonato.isCampeonatoFinalizado()){
            throw new RuntimeException("O campeonato não deve ter sido iniciado ou finalizado nenhuma vez!");
        }
    }

    public void validateCampeonatoIniciadoOuFinalizado(DtoCampeonato dtoCampeonato){
        if(campeonatoRepository.campeonatoIniciado(dtoCampeonato.getId_campeonato()) ||
        campeonatoRepository.campeonatoFinalizado(dtoCampeonato.getId_campeonato())){
            throw new RuntimeException("Campeonato já iniciado ou finalizado");
        }
    }
    public void validateCampeonatoFinalizadoOuNaoIniciado(long id){
        if(campeonatoRepository.campeonatoFinalizado(id) ||
                (campeonatoRepository.campeonatoFinalizado(id) && !campeonatoRepository.campeonatoIniciado(id))){
            throw new RuntimeException("Campeonato ja foi finalizado ou não foi inicializado");
        }
    }

    public void iniciateCampeonato(DtoCampeonato dtoCampeonato){
        validateCampeonatoIniciadoOuFinalizado(dtoCampeonato);
        DomainCampeonato domainCampeonato = this.findCampByIdOrThrowBadRequest(dtoCampeonato.getId_campeonato());
        domainCampeonato.setCampeonatoIniciado(true);
        this.campeonatoRepository.save(domainCampeonato);
    }

    public void finishCampeonato(long id){
        validateCampeonatoFinalizadoOuNaoIniciado(id);
        DomainCampeonato domainCampeonato = this.findCampByIdOrThrowBadRequest(id);
        domainCampeonato.setCampeonatoFinalizado(true);
        domainCampeonato.setCampeonatoIniciado(false);
        this.campeonatoRepository.save(domainCampeonato);
    }
}
