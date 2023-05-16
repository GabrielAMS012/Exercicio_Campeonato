package gabrielAMS.ex_campeonato.campeonato.controller;

import gabrielAMS.ex_campeonato.campeonato.domain.DomainCampeonato;
import gabrielAMS.ex_campeonato.campeonato.dto.DtoCampeonato;
import gabrielAMS.ex_campeonato.campeonato.service.CampeonatoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("campeonatos")
@Log4j2
@RequiredArgsConstructor
public class CampeonatoController {
    private final CampeonatoService campeonatoService;

    @GetMapping(path = "/all")
    public ResponseEntity<List<DomainCampeonato>> listAll(){
        return ResponseEntity.ok(campeonatoService.listAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DomainCampeonato> findById(@PathVariable long id){
        return ResponseEntity.ok(campeonatoService.findCampByIdOrThrowBadRequest(id));
    }

    @PostMapping
    public ResponseEntity<DomainCampeonato> save(@RequestBody @Valid DomainCampeonato domainCampeonato){
        return new ResponseEntity<>(campeonatoService.save(domainCampeonato), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        campeonatoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> replace(@PathVariable("id") long id, @RequestBody @Valid DomainCampeonato domainCampeonato){
        campeonatoService.replace(id, domainCampeonato);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}/iniciar")
    public ResponseEntity<Object> iniciarCampeonato(@PathVariable("id") long id, @RequestBody @Valid DtoCampeonato dtoCampeonato){
        this.campeonatoService.iniciateCampeonato(dtoCampeonato);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Object> finalizarCampeonato(@PathVariable("id") long id){
        this.campeonatoService.finishCampeonato(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
