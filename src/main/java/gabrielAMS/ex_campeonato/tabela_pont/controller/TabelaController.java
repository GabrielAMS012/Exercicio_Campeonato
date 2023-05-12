package gabrielAMS.ex_campeonato.tabela_pont.controller;

import gabrielAMS.ex_campeonato.jogos.dto.DtoJogos;
import gabrielAMS.ex_campeonato.tabela_pont.dto.DtoTabela;
import gabrielAMS.ex_campeonato.tabela_pont.requests.TabelaPontPostRequestBody;
import gabrielAMS.ex_campeonato.tabela_pont.requests.TabelaPontPutRequestBody;
import gabrielAMS.ex_campeonato.tabela_pont.service.TabelaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("tbPont")
@Log4j2
@RequiredArgsConstructor
public class TabelaController {
    private final TabelaService tabelaService;

    @GetMapping
    public ResponseEntity<List<DtoTabela>> list(){
        return ResponseEntity.ok(tabelaService.listAll());
    }

    @GetMapping(path = "/tbPont/all")
    public ResponseEntity<List<DtoTabela>> listAll(){
        return ResponseEntity.ok(tabelaService.listAll());
    }

    @GetMapping(path = "/tbPont/{id}")
    public ResponseEntity<DtoTabela> findById(@PathVariable int id){
        return ResponseEntity.ok(tabelaService.findTbByIdOrThrowBadRequest(id));
    }

    @PostMapping
    public ResponseEntity<DtoTabela> save(@RequestBody @Valid TabelaPontPostRequestBody tabelaPontPostRequestBody){
        return new ResponseEntity<>(tabelaService.save(tabelaPontPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/tbPont/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        tabelaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody TabelaPontPutRequestBody tabelaPontPutRequestBody){
        tabelaService.replace(tabelaPontPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
