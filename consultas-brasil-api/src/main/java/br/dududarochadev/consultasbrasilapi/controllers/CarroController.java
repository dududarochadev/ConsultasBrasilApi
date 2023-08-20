package br.dududarochadev.consultasbrasilapi.controllers;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.dududarochadev.consultasbrasilapi.models.projecoes.ProjecaoDeCarro;
import br.dududarochadev.consultasbrasilapi.services.ServicoDeCarro;

@Controller
@RequestMapping("/")
public class CarroController {
    @Autowired
    private ServicoDeCarro servicoDeCarro;

    @GetMapping
    public ResponseEntity<List<ProjecaoDeCarro>> ListarModelos() {
        var carros = servicoDeCarro.ListarModelos();

        if (carros.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(carros);
    }

    @GetMapping(path = "/marca/{marca}")
    public ResponseEntity<List<ProjecaoDeCarro>> ObterModeloPorMarca(@PathVariable String marca) {
        var carros = servicoDeCarro.ObterModeloPorMarca(marca);

        if (carros.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(carros);
    }

    @GetMapping(path = "/ano/{ano}")
    public ResponseEntity<List<ProjecaoDeCarro>> ObterModeloPorAno(@PathVariable int ano) {
        var carros = servicoDeCarro.ObterModeloPorAno(ano);

        if (carros.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(carros);
    }

    @GetMapping(path = "/data/{data}")
    public ResponseEntity<List<ProjecaoDeCarro>> ObterModeloPorData(@PathVariable String data) throws ParseException {
        var carros = servicoDeCarro.ObterModeloPorData(data);

        if (carros.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(carros);
    }
}
