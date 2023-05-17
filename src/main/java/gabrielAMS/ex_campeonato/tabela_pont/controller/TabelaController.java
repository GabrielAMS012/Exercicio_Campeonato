package gabrielAMS.ex_campeonato.tabela_pont.controller;

import gabrielAMS.ex_campeonato.tabela_pont.service.TabelaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tbPont")
@Log4j2
public class TabelaController {
    final TabelaService tabelaService;

    public TabelaController(TabelaService tabelaService){
        this.tabelaService = tabelaService;
    }
}
