package gabrielAMS.ex_campeonato.jogos.service;

import gabrielAMS.ex_campeonato.campeonato.domain.DomainCampeonato;
import gabrielAMS.ex_campeonato.campeonato.repository.CampeonatoRepository;
import gabrielAMS.ex_campeonato.campeonato.service.CampeonatoService;
import gabrielAMS.ex_campeonato.exception.BadRequestException;
import gabrielAMS.ex_campeonato.jogos.domain.DomainJogos;
import gabrielAMS.ex_campeonato.jogos.repository.JogosRepository;
import gabrielAMS.ex_campeonato.jogos.dto.DtoJogos;

import gabrielAMS.ex_campeonato.time.repository.TimeRepository;
import gabrielAMS.ex_campeonato.time.service.TimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JogosService {

    final TimeService timeService;
    final JogosRepository jogosRepository;
    final CampeonatoService campeonatoService;
    final CampeonatoRepository campeonatoRepository;
    final TimeRepository timeRepository;

    @Transactional
    public DomainJogos saveJogo(DtoJogos dtoJogos){
        this.validateNovoJogo(dtoJogos);
        DomainJogos novoJogo = new DomainJogos();
        DomainCampeonato domainCampeonato = this.campeonatoService.findCampByIdOrThrowBadRequest(dtoJogos.getId_campeonato());
        novoJogo.setId_campeonato(domainCampeonato);

        novoJogo.setTime_mandante(this.timeService.findTimeByIdOrThrowBadRequest(dtoJogos.getTime_mandante()));
        novoJogo.setTime_mandante(this.timeService.findTimeByIdOrThrowBadRequest(dtoJogos.getTime_visitante()));
        novoJogo.setGols_mandante(dtoJogos.getGols_mandante());
        novoJogo.setGols_visitante(dtoJogos.getGols_visitante());
        novoJogo.setDataJogo(dtoJogos.getDataJogo());
        return this.jogosRepository.save(novoJogo);
    }

    @Transactional(readOnly = true)
    public DomainJogos findById(Long id){
        return this.jogosRepository.findById(id).orElseThrow(() -> new BadRequestException("Jogo não encontrado"));
    }

    @Transactional(readOnly = true)
    public Page<DomainJogos> findAll(Pageable pageable){
        return this.jogosRepository.findAll(pageable);
    }

    @Transactional
    public DomainJogos replaceJogo(long id, DtoJogos dtoJogos){
        this.validateNovoJogo(dtoJogos);
        DomainJogos jogoAtualizado = this.jogosRepository.findById(id).get();
        jogoAtualizado.setGols_mandante(jogoAtualizado.getGols_mandante());
        jogoAtualizado.setGols_visitante(jogoAtualizado.getGols_visitante());
        return this.jogosRepository.save(jogoAtualizado);
    }

    @Transactional
    public void deleteJogo(Long id){
        this.jogosRepository.deleteById(id);
    }
    private void validateNovoJogo(DtoJogos dtoJogos){
        validateStatusCampeonato(dtoJogos);
        validateTimeDisponivel(dtoJogos);
        jogoExiste(dtoJogos);

        validateData(dtoJogos);
        validateOponente(dtoJogos);
        validateTimeDisponivel(dtoJogos);
    }

    private void validateStatusCampeonato(DtoJogos dtoJogos){
        if(!campeonatoRepository.campeonatoIniciado(dtoJogos.getId_campeonato()) ||
                campeonatoRepository.campeonatoFinalizado(dtoJogos.getId_campeonato())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campeonato não inciado ou já finalizado");
        }
    }

    public void validateData(DtoJogos dtoJogos){
        Date dataAtual = new Date();
        if(dtoJogos.getDataJogo().compareTo(dataAtual) < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data do jogo não deve ser antes da data de hoje");
        }
    }

    public void validateTimeDisponivel(DtoJogos dtoJogos){
        if(jogosRepository.findDomainJogosByDataJogo(dtoJogos.getDataJogo(), dtoJogos.getTime_mandante(),
                dtoJogos.getTime_visitante())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Um dos times ja tem jogo agendado para esse dia");
        }


    }

    public void jogoExiste(DtoJogos dtoJogos){
        if(jogosRepository.jogoExiste(dtoJogos.getId_campeonato(), dtoJogos.getTime_mandante(), dtoJogos.getTime_visitante())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jogo ja realizado com time 1 como mandante");
        }
    }

    private void validateOponente(DtoJogos dtoJogos){
        if(Objects.equals(dtoJogos.getTime_mandante(), dtoJogos.getTime_visitante())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Adversários precisam ser diferentes");        }
    }
}
