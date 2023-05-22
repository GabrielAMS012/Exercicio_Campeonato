package gabrielAMS.ex_campeonato.jogos.service;

import gabrielAMS.ex_campeonato.campeonato.domain.DomainCampeonato;
import gabrielAMS.ex_campeonato.campeonato.repository.CampeonatoRepository;
import gabrielAMS.ex_campeonato.campeonato.service.CampeonatoService;
import gabrielAMS.ex_campeonato.exception.BadRequestException;
import gabrielAMS.ex_campeonato.jogos.domain.DomainJogos;
import gabrielAMS.ex_campeonato.jogos.repository.JogosRepository;
import gabrielAMS.ex_campeonato.jogos.dto.DtoJogos;

import gabrielAMS.ex_campeonato.tabela_pont.domain.DomainTabelaPont;
import gabrielAMS.ex_campeonato.tabela_pont.repository.TabelaPontRepository;
import gabrielAMS.ex_campeonato.tabela_pont.service.TabelaService;
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
    final TabelaService tabelaService;
    final TabelaPontRepository tabelaPontRepository;

    @Transactional
    public DomainJogos saveJogo(DtoJogos dtoJogos){
        this.validateNovoJogo(dtoJogos);
        DomainJogos novoJogo = new DomainJogos();
        DomainCampeonato domainCampeonato = this.campeonatoService.findCampByIdOrThrowBadRequest(dtoJogos.getId_campeonato());
        novoJogo.setIdCampeonato(domainCampeonato);

        novoJogo.setTime_mandante(this.timeService.findTimeByIdOrThrowBadRequest(dtoJogos.getTime_mandante()));
        novoJogo.setTime_visitante(this.timeService.findTimeByIdOrThrowBadRequest(dtoJogos.getTime_visitante()));
        novoJogo.setGols_mandante(dtoJogos.getGols_mandante());
        novoJogo.setGols_visitante(dtoJogos.getGols_visitante());
        novoJogo.setDataJogo(dtoJogos.getDataJogo());
        if(Objects.nonNull(dtoJogos.getId_campeonato())) {
            validaVencedor(dtoJogos);
        }
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

    private void validateCampeonato(DtoJogos dtoJogos){
        validateStatusCampeonato(dtoJogos);
        validateTimeNoCamp(dtoJogos);
    }
    private void validateNovoJogo(DtoJogos dtoJogos){
        if(Objects.nonNull(dtoJogos.getId_campeonato())) {
            validateCampeonato(dtoJogos);
        }
        jogoExiste(dtoJogos);
        validateData(dtoJogos);
        validateOponente(dtoJogos);
        validateTimeDisponivel(dtoJogos);
    }

    public void validateTimeNoCamp(DtoJogos dtoJogos){
        if(!tabelaPontRepository.getIdCampByIdTabela(dtoJogos.getId_campeonato(), tabelaPontRepository.getIdTabelaByIdTime(dtoJogos.getTime_mandante()))
        || !tabelaPontRepository.getIdCampByIdTabela(dtoJogos.getId_campeonato(), tabelaPontRepository.getIdTabelaByIdTime(dtoJogos.getTime_visitante()))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O time não está nesse campeonato!");
        }
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Adversários precisam ser diferentes");
        }
    }

    private void validaVencedor(DtoJogos dtoJogos){
        DomainTabelaPont timeMandante = tabelaPontRepository.getTimeByIdTimes(dtoJogos.getTime_mandante());
        DomainTabelaPont timeVisitante = tabelaPontRepository.getTimeByIdTimes(dtoJogos.getTime_visitante());

        if(dtoJogos.getGols_mandante() > dtoJogos.getGols_visitante()){
            timeMandante.setPontuacao(timeMandante.getPontuacao() + 3);
            timeMandante.setGols_marcados(timeMandante.getGols_marcados() + dtoJogos.getGols_mandante());
            timeMandante.setGols_sofridos(timeMandante.getGols_sofridos() + dtoJogos.getGols_visitante());
            timeMandante.setQntd_vitorias(timeMandante.getQntd_vitorias() + 1);
            timeMandante.setQntd_empates(timeMandante.getQntd_empates());
            timeMandante.setQntd_derrotas(timeMandante.getQntd_derrotas());

            timeVisitante.setPontuacao(timeVisitante.getPontuacao());
            timeVisitante.setGols_marcados(timeVisitante.getGols_marcados() + dtoJogos.getGols_visitante());
            timeVisitante.setGols_sofridos(timeVisitante.getGols_sofridos() + dtoJogos.getGols_mandante());
            timeVisitante.setQntd_vitorias(timeVisitante.getQntd_vitorias());
            timeVisitante.setQntd_derrotas(timeVisitante.getQntd_derrotas() + 1);
            timeVisitante.setQntd_empates(timeVisitante.getQntd_empates());

            this.tabelaService.save(timeMandante);
            this.tabelaService.save(timeVisitante);
        }else if(dtoJogos.getGols_mandante() == dtoJogos.getGols_visitante()){

            timeMandante.setPontuacao(timeMandante.getPontuacao() + 1);
            timeMandante.setGols_marcados(timeMandante.getGols_marcados() + dtoJogos.getGols_mandante());
            timeMandante.setGols_sofridos(timeMandante.getGols_sofridos() + dtoJogos.getGols_visitante());
            timeMandante.setQntd_vitorias(timeMandante.getQntd_vitorias());
            timeMandante.setQntd_derrotas(timeMandante.getQntd_derrotas());
            timeMandante.setQntd_empates(timeMandante.getQntd_empates() + 1);


            timeVisitante.setPontuacao(timeVisitante.getPontuacao() + 1);
            timeVisitante.setGols_marcados(timeVisitante.getGols_marcados() + dtoJogos.getGols_visitante());
            timeVisitante.setGols_sofridos(timeVisitante.getGols_sofridos() + dtoJogos.getGols_mandante());
            timeVisitante.setQntd_vitorias(timeVisitante.getQntd_vitorias());
            timeVisitante.setQntd_empates(timeVisitante.getQntd_empates());
            timeVisitante.setQntd_empates(timeVisitante.getQntd_empates() + 1);

            this.tabelaService.save(timeMandante);
            this.tabelaService.save(timeVisitante);
        }else {
            timeMandante.setPontuacao(timeMandante.getPontuacao());
            timeMandante.setGols_marcados(timeMandante.getGols_marcados() + dtoJogos.getGols_mandante());
            timeMandante.setGols_sofridos(timeMandante.getGols_sofridos() + dtoJogos.getGols_visitante());
            timeMandante.setQntd_vitorias(timeMandante.getQntd_vitorias());
            timeMandante.setQntd_derrotas(timeMandante.getQntd_derrotas() + 1);
            timeMandante.setQntd_empates(timeMandante.getQntd_empates());

            timeVisitante.setPontuacao(timeVisitante.getPontuacao() + 3);
            timeVisitante.setGols_marcados(timeVisitante.getGols_marcados() + dtoJogos.getGols_visitante());
            timeVisitante.setGols_sofridos(timeVisitante.getGols_sofridos() + dtoJogos.getGols_mandante());
            timeVisitante.setQntd_vitorias(timeVisitante.getQntd_vitorias() + 1);
            timeVisitante.setQntd_derrotas(timeVisitante.getQntd_derrotas());
            timeVisitante.setQntd_empates(timeVisitante.getQntd_empates());

            this.tabelaService.save(timeMandante);
            this.tabelaService.save(timeVisitante);
        }
    }
}
