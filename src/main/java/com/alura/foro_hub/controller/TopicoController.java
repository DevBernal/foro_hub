package com.alura.foro_hub.controller;

import com.alura.foro_hub.domain.topico.*;
import jakarta.validation.Valid;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoRepository repository;

    public TopicoController(TopicoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<?> registrarTopico(@RequestBody @Valid DatosRegistroTopico datos) {

        var existente = repository.findByTituloAndMensaje(datos.titulo(), datos.mensaje());
        if (existente.isPresent()) {
            return ResponseEntity.status(409).body("Ya existe un tópico con el mismo título y mensaje.");
        }

        var topico = Topico.builder()
                .titulo(datos.titulo())
                .mensaje(datos.mensaje())
                .fechaCreacion(LocalDateTime.now())
                .status(StatusTopico.NO_RESPONDIDO)
                .autor(datos.autor())
                .curso(datos.curso())
                .build();

        repository.save(topico);

        return ResponseEntity.ok("Tópico registrado correctamente.");
    }

    @GetMapping
    public ResponseEntity<?> listarTopicos(@PageableDefault(size = 5, sort = "fechaCreacion") Pageable paginacion) {
        var pagina = repository.findAll(paginacion).map(DatosRespuestaTopico::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerTopicoPorId(@PathVariable Long id) {
        var topicoOptional = repository.findById(id);

        if (topicoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var datos = new DatosRespuestaTopico(topicoOptional.get());
        return ResponseEntity.ok(datos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datos) {
        var topicoOptional = repository.findById(id);

        if (topicoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var duplicado = repository.findByTituloAndMensaje(datos.titulo(), datos.mensaje());
        if (duplicado.isPresent() && !duplicado.get().getId().equals(id)) {
            return ResponseEntity.status(409).body("Ya existe otro tópico con el mismo título y mensaje.");
        }

        var topico = topicoOptional.get();
        topico.setTitulo(datos.titulo());
        topico.setMensaje(datos.mensaje());
        topico.setAutor(datos.autor());
        topico.setCurso(datos.curso());

        repository.save(topico);

        return ResponseEntity.ok(new DatosRespuestaTopico(topico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id) {
        var topicoOptional = repository.findById(id);

        if (topicoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
