package br.dududarochadev.consultasbrasilapi.controllers;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dududarochadev.consultasbrasilapi.dados.projecoes.ProjecaoDeModelo;
import br.dududarochadev.consultasbrasilapi.servicos.ServicoDeModelo;

@RestController
@RequestMapping("/modelos")
public class ModeloController {
    @Autowired
    private ServicoDeModelo servicoDeModelo;

    @GetMapping
    public ResponseEntity<List<ProjecaoDeModelo>> ListarModelos() {
        var modelos = servicoDeModelo.ListarModelos();

        if (modelos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(modelos);
    }

    @GetMapping(path = "/marca/{idMarca}")
    public ResponseEntity<List<ProjecaoDeModelo>> ObterModeloPorMarca(@PathVariable String idMarca) {
        var modelos = servicoDeModelo.ObterModeloPorMarca(idMarca);

        if (modelos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(modelos);
    }

    @GetMapping(path = "/ano/{idAno}")
    public ResponseEntity<List<ProjecaoDeModelo>> ObterModeloPorAno(@PathVariable String idAno) {
        var modelos = servicoDeModelo.ObterModeloPorAno(idAno);

        if (modelos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(modelos);
    }

    @GetMapping(path = "/data/{data}")
    public ResponseEntity<List<ProjecaoDeModelo>> ObterModeloPorData(@PathVariable String data) throws ParseException {
        var modelos = servicoDeModelo.ObterModeloPorData(data);

        if (modelos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(modelos);
    }
}
