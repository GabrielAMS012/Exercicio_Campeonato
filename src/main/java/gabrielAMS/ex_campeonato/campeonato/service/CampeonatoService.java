package gabrielAMS.ex_campeonato.campeonato.service;

import gabrielAMS.ex_campeonato.campeonato.domain.DomainCampeonato;
import gabrielAMS.ex_campeonato.campeonato.dto.DtoCampeonato;

import gabrielAMS.ex_campeonato.campeonato.repository.CampeonatoRepository;
import gabrielAMS.ex_campeonato.exception.BadRequestException;
import gabrielAMS.ex_campeonato.tabela_pont.domain.DomainTabelaPont;
import gabrielAMS.ex_campeonato.tabela_pont.repository.TabelaPontRepository;
import gabrielAMS.ex_campeonato.time.domain.DomainTime;
import gabrielAMS.ex_campeonato.time.service.TimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CampeonatoService {

    private final TimeService timeService;
    private final CampeonatoRepository campeonatoRepository;
    private final TabelaPontRepository tabelaPontRepository;

    @Transactional(readOnly = true)
    public List<DomainCampeonato> listAll(){
        return campeonatoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public DomainCampeonato findCampByIdOrThrowBadRequest(long id){
        return campeonatoRepository.findById(id).orElseThrow(()-> new BadRequestException("Campeonato não encontrado"));
    }

    @Transactional
    public DomainCampeonato save(DomainCampeonato domainCampeonato){
        validateNewCampeonato(domainCampeonato);
        return campeonatoRepository.save(domainCampeonato);
    }
    @Transactional
    public void delete(long id){
        campeonatoRepository.delete(findCampByIdOrThrowBadRequest(id));
    }

    @Transactional
    public DomainCampeonato replace(long id, DomainCampeonato domainCampeonato){
        validateNewCampeonato(domainCampeonato);
        DomainCampeonato campeonatoSalvo = this.campeonatoRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST));
        campeonatoSalvo.setNomeCamp(domainCampeonato.getNomeCamp());
        campeonatoSalvo.setAno(domainCampeonato.getAno());
        return this.campeonatoRepository.save(campeonatoSalvo);
    }
    @Transactional
    public void iniciateCampeonato(DtoCampeonato dtoCampeonato){
        validateInicioCampeonato(dtoCampeonato);
        DomainCampeonato domainCampeonato = this.findCampByIdOrThrowBadRequest(dtoCampeonato.getId_campeonato());
        domainCampeonato.setCampeonatoIniciado(true);
        this.campeonatoRepository.save(domainCampeonato);
        createTabelaPontForEachTime(dtoCampeonato, domainCampeonato);
    }
    @Transactional
    public void finishCampeonato(long id){
        validateFinishCampeonato(id);
        DomainCampeonato domainCampeonato = this.findCampByIdOrThrowBadRequest(id);
        domainCampeonato.setCampeonatoFinalizado(true);
        domainCampeonato.setCampeonatoIniciado(false);
        this.campeonatoRepository.save(domainCampeonato);
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

    public void validateInicioCampeonato(DtoCampeonato dtoCampeonato){
        validateCampeonatoIniciadoOuFinalizado(dtoCampeonato);
        validateTimesRepetidos(dtoCampeonato);
        validateTimesSuficientes(dtoCampeonato);
    }

    public void validateTimesSuficientes(DtoCampeonato dtoCampeonato){
        if(dtoCampeonato.getIdsTimes().size() < 2){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Não há times suficientes para iniciar um campeonato");
        }
    }

    public void validateTimesRepetidos(DtoCampeonato dtoCampeonato){
        Set<Long> set = new HashSet<>();
        dtoCampeonato.getIdsTimes().forEach(idTime -> {
            if(!set.add(idTime)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, idTime + " foi inserido mais de uma vez");
            }
        });
    }

    public void validateFinishCampeonato(long id){
        validateCampeonatoFinalizadoOuNaoIniciado(id);
        validateNumeroJogos(id);
    }

    public void validateNumeroJogos(long id){
        long nPartidas = tabelaPontRepository.numeroTimes(id) *
                (tabelaPontRepository.numeroTimes(id) - 1);
        if(campeonatoRepository.numeroJogos(id) != nPartidas){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Os times tem que ter jogado pelo menos duas vezes um contra o outro");
        }
    }

    public void createTabelaPontForEachTime(DtoCampeonato dtoCampeonato,
                                            DomainCampeonato domainCampeonato){
        dtoCampeonato.getIdsTimes().forEach(idTime -> {
            DomainTabelaPont domainTabelaPont = createDomainTabela(domainCampeonato,
                    this.timeService.findTimeByIdOrThrowBadRequest(idTime));
            this.tabelaPontRepository.save(domainTabelaPont);
        });
    }

    public DomainTabelaPont createDomainTabela(DomainCampeonato domainCampeonato,
                                               DomainTime domainTime){

        DomainTabelaPont domainTabelaPont = new DomainTabelaPont();
        domainTabelaPont.setCampeonato(domainCampeonato);
        domainTabelaPont.setTimes(domainTime);
        domainTabelaPont.setQntd_vitorias(0);
        domainTabelaPont.setQntd_derrotas(0);
        domainTabelaPont.setPontuacao(0);
        domainTabelaPont.setGols_marcados(0);
        domainTabelaPont.setGols_sofridos(0);
        domainTabelaPont.setQntd_empates(0);
        domainTabelaPont.setQntd_vitorias(0);
        return domainTabelaPont;
    }
}
