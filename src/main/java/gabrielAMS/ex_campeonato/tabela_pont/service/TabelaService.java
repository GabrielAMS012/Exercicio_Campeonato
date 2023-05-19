package gabrielAMS.ex_campeonato.tabela_pont.service;


import gabrielAMS.ex_campeonato.tabela_pont.domain.DomainTabelaPont;
import gabrielAMS.ex_campeonato.tabela_pont.repository.TabelaPontRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TabelaService {
    final TabelaPontRepository tabelaPontRepository;
    public DomainTabelaPont save(DomainTabelaPont domainTabelaPont){
        return tabelaPontRepository.save(domainTabelaPont);
    }
}
