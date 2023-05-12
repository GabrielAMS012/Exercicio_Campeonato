package gabrielAMS.ex_campeonato.campeonato.service;

import gabrielAMS.ex_campeonato.campeonato.dto.DtoCampeonato;
import gabrielAMS.ex_campeonato.campeonato.mapper.CampeonatoMapper;
import gabrielAMS.ex_campeonato.campeonato.repository.CampeonatoRepository;
import gabrielAMS.ex_campeonato.campeonato.requests.CampeonatoPostRequestBody;
import gabrielAMS.ex_campeonato.campeonato.requests.CampeonatoPutRequestBody;
import gabrielAMS.ex_campeonato.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CampeonatoService {

    private final CampeonatoRepository campeonatoRepository;

    public List<DtoCampeonato> listAll(){
        return campeonatoRepository.findAll();
    }

    public DtoCampeonato findCampByIdOrThrowBadRequest(int id){
        return campeonatoRepository.findById(id).orElseThrow(()-> new BadRequestException("Campeonato não encontrado"));
    }

    public DtoCampeonato save(CampeonatoPostRequestBody campeonatoPostRequestBody){
        return campeonatoRepository.save(CampeonatoMapper.INSTANCE.toCampeonato(campeonatoPostRequestBody));
    }

    public void delete(int id){
        campeonatoRepository.delete(findCampByIdOrThrowBadRequest(id));
    }

    public void replace(CampeonatoPutRequestBody campeonatoPutRequestBody){
        DtoCampeonato campenatoSalvo = findCampByIdOrThrowBadRequest(campeonatoPutRequestBody.getId_campeonato());
        DtoCampeonato campeonato = CampeonatoMapper.INSTANCE.toCampeonato(campeonatoPutRequestBody);
        campeonato.setId_campeonato(campenatoSalvo.getId_campeonato());
        campeonatoRepository.save(campeonato);
    }
}
