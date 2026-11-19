package com.aagpazos.bibliotecavideojuegospractica.controladores.rest;

import com.aagpazos.bibliotecavideojuegospractica.dto.DesarrolladoraDTO;
import com.aagpazos.bibliotecavideojuegospractica.service.DesarrolladoraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/desarrolladoras")
@Tag(name = "Desarrolladoras", description = "Listado de desarrolladoras")
public class DesarrolladoraRestController {

    private final DesarrolladoraService desarrolladoraService;

    public DesarrolladoraRestController(DesarrolladoraService desarrolladoraService) {
        this.desarrolladoraService = desarrolladoraService;
    }

    // RF-13
    @GetMapping
    @Operation(summary = "Listar todas las desarrolladoras")
    @ApiResponse(responseCode = "200", description = "Lista de desarrolladoras")
    public ResponseEntity<List<DesarrolladoraDTO>> listar() {
        return ResponseEntity.ok(desarrolladoraService.listarDesarrolladoras());
    }
}