/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsw.Desafio03Spring.Desafio03Spring;

import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author aparicio da silva
 */
public class VeiculoResource {
        
    @Autowired
    private VeiculoRepositorio vr;
    

    
    @GetMapping(value = "/lista")
    public @ResponseBody
    Iterable<Veiculo> listaVeiculos() {
        Iterable<Veiculo> listaVeiculos = vr.findAll();
        return listaVeiculos;
    }
    
    
    @GetMapping(value = "/{id}")
    public Veiculo buscaId(@PathVariable Long id) throws Exception {
        Optional<Veiculo> verifica = vr.findById(id);
        
        if (!verifica.isPresent()) {
            throw new Exception("Id - " + id + " Inexistente!");
        }
        
        return verifica.get();
    }
    
    @PostMapping(value = "/adiciona")
    public Veiculo cadastraVeiculo(@RequestBody @Valid Veiculo oVeiculo) {
        return vr.save(oVeiculo);
    }
    
    @PutMapping(value = "/modifica/{id}")
    public ResponseEntity<Veiculo> modificaVeiculo(@PathVariable("id") Long id, @RequestBody Veiculo oVeiculo) {
        Optional<Veiculo> veiculoInfo = vr.findById(id);
        
        if (veiculoInfo.isPresent()) {
            Veiculo veiculoAtual = veiculoInfo.get();
            veiculoAtual.setId(oVeiculo.getId());
            veiculoAtual.setMontadora(oVeiculo.getMontadora());
            veiculoAtual.setModelo(oVeiculo.getModelo());
            veiculoAtual.setCor(oVeiculo.getCor());
            veiculoAtual.setKm(oVeiculo.getKm());
            veiculoAtual.setMotor(oVeiculo.getMotor());
            veiculoAtual.setTipo(oVeiculo.getTipo());

            //altera os dados
            vr.save(veiculoAtual);
            return ResponseEntity.ok(veiculoAtual);
        } else {
            return ResponseEntity.notFound().build();
        }
        
    }
    
    @DeleteMapping(value = "/deleta")
    public Veiculo deletaVeiculo(@RequestBody Veiculo oVeiculo) {
        vr.delete(oVeiculo);
        return oVeiculo;
    }
}

