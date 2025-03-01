package gabrielAMS.ex_campeonato.jogos.controller;

import gabrielAMS.ex_campeonato.jogos.domain.DomainJogos;
import gabrielAMS.ex_campeonato.jogos.dto.DtoJogos;
import gabrielAMS.ex_campeonato.jogos.service.JogosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("jogo")
@Log4j2
@RequiredArgsConstructor
public class JogosController {
    private final JogosService jogosService;

    @GetMapping(path = "/all")
    public ResponseEntity<Page<DomainJogos>> listAll(Pageable pageable){
        return ResponseEntity.ok(jogosService.findAll(pageable));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(jogosService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DomainJogos> save(@RequestBody @Valid DtoJogos dtoJogos){
        return ResponseEntity.ok(jogosService.saveJogo(dtoJogos));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable ("id") long id){
        jogosService.deleteJogo(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> replace(@PathVariable("id") long id,
                                          @RequestBody DtoJogos dtoJogos){
        jogosService.replaceJogo(id,dtoJogos);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
