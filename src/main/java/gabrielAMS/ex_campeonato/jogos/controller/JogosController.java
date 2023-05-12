package gabrielAMS.ex_campeonato.jogos.controller;

import gabrielAMS.ex_campeonato.jogos.dto.DtoJogos;
import gabrielAMS.ex_campeonato.jogos.requests.JogoPostRequestBody;
import gabrielAMS.ex_campeonato.jogos.requests.JogoPutRequestBody;
import gabrielAMS.ex_campeonato.jogos.service.JogosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("jogo")
@Log4j2
@RequiredArgsConstructor
public class JogosController {
    private final JogosService jogosService;

    @GetMapping
    public ResponseEntity<List<DtoJogos>> list(){
        return ResponseEntity.ok(jogosService.listAll());
    }

    @GetMapping(path = "/jogos/all")
    public ResponseEntity<List<DtoJogos>> listAll(){
        return ResponseEntity.ok(jogosService.listAll());
    }

    @GetMapping(path = "/jogos/{id}")
    public ResponseEntity<DtoJogos> findById(@PathVariable int id){
        return ResponseEntity.ok(jogosService.findJogoByIdOrThrowBadRequest(id));
    }

    @PostMapping
    public ResponseEntity<DtoJogos> save(@RequestBody @Valid JogoPostRequestBody jogoPostRequestBody){
        return new ResponseEntity<>(jogosService.save(jogoPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/jogos/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        jogosService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody JogoPutRequestBody jogoPutRequestBody){
        jogosService.replace(jogoPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
