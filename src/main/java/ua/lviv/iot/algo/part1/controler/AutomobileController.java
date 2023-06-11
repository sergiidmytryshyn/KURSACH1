package ua.lviv.iot.algo.part1.controler;

import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.algo.part1.model.Automobile;
import ua.lviv.iot.algo.part1.modelDTO.AutomobileDTO;
import ua.lviv.iot.algo.part1.service.AutomobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;


import java.util.LinkedList;
import java.util.List;

@RequestMapping("/automobiles")
@RestController
public class AutomobileController {

    @Autowired
    private AutomobileService automobileService;

    private AutomobileDTO formatAutomobile(final Automobile automobile) {
        return new AutomobileDTO(automobile.getId(),
                automobile.getModel(),
                automobile.getAddress(),
                automobile.getEndOfRent(),
                automobile.getProblems());
    }

    public static final ResponseEntity OK = ResponseEntity
            .status(HttpStatusCode.valueOf(200)).build();

    public static final ResponseEntity FAILURE = ResponseEntity
            .status(HttpStatusCode.valueOf(404)).build();

    @GetMapping
    public List<AutomobileDTO> getAutomobiles() {
        List<AutomobileDTO> response = new LinkedList<>();
        for (Automobile stone : automobileService.giveAll()) {
            response.add(formatAutomobile(stone));
        }
        return response;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getAutomobiles(
            final @PathVariable("id") int automobileId) {
        if (!automobileService.hasAutomobileWith(automobileId)) {
            return FAILURE;
        } else {
            return ResponseEntity.ok(formatAutomobile(automobileService
                    .giveAutomobile(automobileId)));
        }
    }

    @PostMapping
    public AutomobileDTO createAutomobile(
            final @RequestBody Automobile automobile) {
        automobileService.addAutomobile(automobile);
        return formatAutomobile(automobileService.giveAutomobile(
                automobile.getId()));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteAutomobile(
            final @PathVariable("id") int automobileId) {
        if (automobileService.deleteAutomobile(automobileId) == null) {
            return FAILURE;
        } else {
            return OK;
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity updateMethStone(
            final @PathVariable("id") int automobileId,
            final @RequestBody Automobile automobile) {
        if (automobileService.hasAutomobileWith(automobileId)) {
            automobileService.replaceAutomobile(automobile, automobileId);
            return OK;
        } else {
            return FAILURE;
        }
    }
}
