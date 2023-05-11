package gabrielAMS.ex_campeonato.jogos.service;

import gabrielAMS.ex_campeonato.jogos.dto.DtoJogos;
import gabrielAMS.ex_campeonato.jogos.exception.BadRequestException;
import gabrielAMS.ex_campeonato.jogos.mapper.JogoMapper;
import gabrielAMS.ex_campeonato.jogos.repository.JogosRepository;
import gabrielAMS.ex_campeonato.jogos.requests.JogoPostRequestBody;
import gabrielAMS.ex_campeonato.jogos.requests.JogoPutRequestBody;
import gabrielAMS.ex_campeonato.jogos.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JogosService {

    private final Utils utils;
    final JogosRepository jogosRepository;

    public List<DtoJogos> listAll(){
        return jogosRepository.findAll();
    }

    public DtoJogos findById(int id){
        return utils.findJogoOrThrowNotFound(id, jogosRepository);
    }

    public DtoJogos findByIdOrThrowBadRequest(int id){
        return jogosRepository.findById(id).orElseThrow(()-> new BadRequestException("Jogo não encontrado"));
    }

    public DtoJogos save(DtoJogos jogo){
        return jogosRepository.save(jogo);
    }

    public void delete(int id){
        jogosRepository.delete(utils.findJogoOrThrowNotFound(id, jogosRepository));
    }

    public void replace(JogoPutRequestBody jogoPutRequestBody){
        DtoJogos jogoSalvo = findByIdOrThrowBadRequest(jogoPutRequestBody.getId());
        DtoJogos jogo = JogoMapper.INSTANCE.toJogo(jogoPutRequestBody);
        jogo.setId(jogoSalvo.getId());
        jogosRepository.save(jogo);
    }
}
