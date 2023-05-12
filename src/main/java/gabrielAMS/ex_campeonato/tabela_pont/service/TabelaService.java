package gabrielAMS.ex_campeonato.tabela_pont.service;

import gabrielAMS.ex_campeonato.exception.BadRequestException;
import gabrielAMS.ex_campeonato.tabela_pont.dto.DtoTabela;
import gabrielAMS.ex_campeonato.tabela_pont.mapper.TabelaPontMapper;
import gabrielAMS.ex_campeonato.tabela_pont.repository.TabelaPontRepository;
import gabrielAMS.ex_campeonato.tabela_pont.requests.TabelaPontPostRequestBody;
import gabrielAMS.ex_campeonato.tabela_pont.requests.TabelaPontPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TabelaService {
    private final TabelaPontRepository tabelaPontRepository;

    public List<DtoTabela> listAll(){
        return tabelaPontRepository.findAll();
    }

    public DtoTabela findTbByIdOrThrowBadRequest(int id){
        return tabelaPontRepository.findById(id).orElseThrow(()-> new BadRequestException("Tabela não encontrada"));
    }

    public DtoTabela save(TabelaPontPostRequestBody tabela){
        return tabelaPontRepository.save(TabelaPontMapper.INSTANCE.toTabela(tabela));
    }

    public void delete(int id){
        tabelaPontRepository.delete(findTbByIdOrThrowBadRequest(id));
    }

    public void replace(TabelaPontPutRequestBody tabelaPontPutRequestBody){
        DtoTabela tabelaSalva = findTbByIdOrThrowBadRequest((int) tabelaPontPutRequestBody.getId_tabela());
        DtoTabela tabela = TabelaPontMapper.INSTANCE.toTabela(tabelaPontPutRequestBody);
        tabela.setId_tabela(tabelaSalva.getId_tabela());
        tabelaPontRepository.save(tabela);
    }

}
