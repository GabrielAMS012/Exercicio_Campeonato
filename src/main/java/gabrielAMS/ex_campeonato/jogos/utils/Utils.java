package gabrielAMS.ex_campeonato.jogos.utils;

import gabrielAMS.ex_campeonato.jogos.dto.DtoJogos;
import gabrielAMS.ex_campeonato.exception.BadRequestException;
import gabrielAMS.ex_campeonato.jogos.repository.JogosRepository;
import org.springframework.stereotype.Component;

@Component
public class Utils {
    public DtoJogos findJogoOrThrowNotFound(int id, JogosRepository jogosRepository){
        return jogosRepository.findById(id).orElseThrow(() -> new BadRequestException("Jogo não encontrado"));
    }
}
