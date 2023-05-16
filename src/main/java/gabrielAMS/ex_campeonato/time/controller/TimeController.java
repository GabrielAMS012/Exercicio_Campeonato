package gabrielAMS.ex_campeonato.time.controller;

import gabrielAMS.ex_campeonato.time.domain.DomainTime;
import gabrielAMS.ex_campeonato.time.service.TimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "time")
@Log4j2
@RequiredArgsConstructor
public class TimeController {
    private final TimeService timeService;

    @GetMapping("/{id}")
    public DomainTime findTimeById(Long id){
        return(timeService.findTimeByIdOrThrowBadRequest(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<DomainTime>> listAll(){
        return ResponseEntity.ok(timeService.listAll());
    }

    @PostMapping
    public ResponseEntity<DomainTime> saveTime(@RequestBody @Valid DomainTime domainTime){
        return new ResponseEntity<>(timeService.saveTime(domainTime), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        timeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> replace(@RequestBody @Valid DomainTime domainTime){
        timeService.replace(domainTime);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
