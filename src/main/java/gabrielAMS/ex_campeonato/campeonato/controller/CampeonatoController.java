package gabrielAMS.ex_campeonato.campeonato.controller;

import gabrielAMS.ex_campeonato.campeonato.dto.DtoCampeonato;
import gabrielAMS.ex_campeonato.campeonato.requests.CampeonatoPostRequestBody;
import gabrielAMS.ex_campeonato.campeonato.requests.CampeonatoPutRequestBody;
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

    @GetMapping(path = "/campeonato/all")
    public ResponseEntity<List<DtoCampeonato>> listAll(){
        return ResponseEntity.ok(campeonatoService.listAll());
    }

    @GetMapping(path = "campeonato/{id}")
    public ResponseEntity<DtoCampeonato> findById(@PathVariable int id){
        return ResponseEntity.ok(campeonatoService.findCampByIdOrThrowBadRequest(id));
    }

    @PostMapping
    public ResponseEntity<DtoCampeonato> save(@RequestBody @Valid CampeonatoPostRequestBody campeonatoPostRequestBody){
        return new ResponseEntity<>(campeonatoService.save(campeonatoPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/campeonato/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        campeonatoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody CampeonatoPutRequestBody campeonatoPutRequestBody){
        campeonatoService.replace(campeonatoPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
