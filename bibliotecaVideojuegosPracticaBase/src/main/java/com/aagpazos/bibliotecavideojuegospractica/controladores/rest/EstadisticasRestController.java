package com.aagpazos.bibliotecavideojuegospractica.controladores.rest;

import com.aagpazos.bibliotecavideojuegospractica.dto.ResumenDTO;
import com.aagpazos.bibliotecavideojuegospractica.service.PrestamoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estadisticas")
@Tag(name = "Estadísticas", description = "Resumen general de la biblioteca")
public class EstadisticasRestController {

    private final PrestamoService prestamoService;

    public EstadisticasRestController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    // RF-14
    @GetMapping
    @Operation(summary = "Obtener estadísticas generales")
    @ApiResponse(responseCode = "200", description = "Contadores de la biblioteca")
    public ResponseEntity<ResumenDTO> obtener() {
        return ResponseEntity.ok(prestamoService.resumen());
    }
}