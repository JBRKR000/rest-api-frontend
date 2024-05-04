package com.project.controller;

import java.net.URI;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.model.Projekt;
import com.project.service.ProjektService;

@RestController
@RequestMapping("/api/projekty")
public class ProjektWebController {

    private static ProjektService projektService = null;

    @Autowired
    public ProjektWebController(ProjektService projektService) {
        ProjektWebController.projektService = projektService;
    }

    @GetMapping("/{projektId}")
    public ResponseEntity<Projekt> getProjekt(@PathVariable Integer projektId) {
        Optional<Projekt> projekt = projektService.getProjekt(projektId);
        return ResponseEntity.of(projekt);
    }

    @GetMapping
    public static Page<Projekt> getProjekty(Pageable pageable) {
        return projektService.getProjekty(pageable);
    }

    @GetMapping(params = "nazwa")
    public Page<Projekt> getProjektyByNazwa(@RequestParam String nazwa, Pageable pageable) {
        return projektService.searchByNazwa(nazwa, pageable);
    }

    @PostMapping
    public ResponseEntity<Void> createProjekt(@Valid @RequestBody Projekt projekt) {
        Projekt createdProjekt = projektService.setProjekt(projekt);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{projektId}").buildAndExpand(createdProjekt.getProjektId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{projektId}")
    public ResponseEntity<Void> updateProjekt(@PathVariable Integer projektId, @Valid @RequestBody Projekt projekt) {
        Optional<Projekt> existingProjekt = projektService.getProjekt(projektId);
        if (existingProjekt.isPresent()) {
            projektService.setProjekt(projekt);
            return ResponseEntity.ok().build(); // 200
        } else {
            return ResponseEntity.notFound().build(); // 404 - Not found
        }
    }

    @DeleteMapping("/{projektId}")
    public ResponseEntity<Void> deleteProjekt(@PathVariable Integer projektId) {
        Optional<Projekt> existingProjekt = projektService.getProjekt(projektId);
        if (existingProjekt.isPresent()) {
            projektService.deleteProjekt(projektId);
            return ResponseEntity.ok().build(); // 200
        } else {
            return ResponseEntity.notFound().build(); // 404 - Not found
        }
    }
}