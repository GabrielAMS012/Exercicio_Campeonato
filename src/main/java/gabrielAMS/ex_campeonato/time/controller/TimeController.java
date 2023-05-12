package gabrielAMS.ex_campeonato.time.controller;

import gabrielAMS.ex_campeonato.time.dto.DtoTime;
import gabrielAMS.ex_campeonato.time.mapper.TimeMapper;
import gabrielAMS.ex_campeonato.time.requests.TimePostRequestBody;
import gabrielAMS.ex_campeonato.time.requests.TimePutRequestBody;
import gabrielAMS.ex_campeonato.time.service.TimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Path;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(path = "time")
@Log4j2
@RequiredArgsConstructor
public class TimeController {
    private final TimeService timeService;

    @GetMapping
    public ResponseEntity<List<DtoTime>> listAll(){
        return ResponseEntity.ok(timeService.listAll());
    }

    @PostMapping
    public ResponseEntity<DtoTime> save(@RequestBody @Valid TimePostRequestBody timePostRequestBody){
        return new ResponseEntity<>(timeService.save(timePostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        timeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Void> replace(@RequestBody TimePutRequestBody timePutRequestBody){
        timeService.replace(timePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
