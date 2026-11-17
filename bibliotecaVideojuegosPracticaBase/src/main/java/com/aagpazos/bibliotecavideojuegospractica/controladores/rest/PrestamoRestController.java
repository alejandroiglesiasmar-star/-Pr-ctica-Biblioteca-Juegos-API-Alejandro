package com.aagpazos.bibliotecavideojuegospractica.controladores.rest;

import com.aagpazos.bibliotecavideojuegospractica.dto.PrestamoDTO;
import com.aagpazos.bibliotecavideojuegospractica.service.PrestamoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prestamos")
@Tag(name = "Préstamos", description = "Gestión de préstamos y devoluciones")
public class PrestamoRestController {

    private final PrestamoService prestamoService;

    public PrestamoRestController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    // RF-09 - Listar / buscar préstamos
    @GetMapping
    @Operation(summary = "Listar préstamos", description = "Filtros: socio, titulo, estado (EN_PLAZO, VENCIDO, DEVUELTO) y fechaDesde")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de préstamos"),
            @ApiResponse(responseCode = "400", description = "Estado no válido")
    })
    public ResponseEntity<List<PrestamoDTO>> listar(
            @RequestParam(required = false) String socio,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde) {

        PrestamoDTO filtro = new PrestamoDTO(socio, titulo, estado, fechaDesde);
        return ResponseEntity.ok(prestamoService.listar(filtro));
    }

    // RF-10 - Detalle de un préstamo
    @GetMapping("/{id}")
    @Operation(summary = "Obtener un préstamo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Préstamo encontrado"),
            @ApiResponse(responseCode = "404", description = "No existe el préstamo")
    })
    public ResponseEntity<PrestamoDTO> obtener(@PathVariable int id) {
        return ResponseEntity.ok(prestamoService.obtener(id));
    }

    // RF-11 - Registrar un préstamo
    @PostMapping
    @Operation(summary = "Registrar un nuevo préstamo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Préstamo registrado"),
            @ApiResponse(responseCode = "404", description = "Socio o copia no existe"),
            @ApiResponse(responseCode = "422", description = "No se cumple alguna regla de negocio (RN-1 a RN-5)")
    })
    public ResponseEntity<PrestamoDTO> registrar(@RequestBody PrestamoDTO dto) {
        PrestamoDTO creado = prestamoService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // RF-12 - Devolver un préstamo
    @PutMapping("/{id}/devolucion")
    @Operation(summary = "Devolver un préstamo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Préstamo devuelto"),
            @ApiResponse(responseCode = "404", description = "No existe el préstamo"),
            @ApiResponse(responseCode = "409", description = "El préstamo ya estaba devuelto")
    })
    public ResponseEntity<PrestamoDTO> devolver(
            @PathVariable int id,
            @RequestBody(required = false) Map<String, Boolean> body,
            @RequestParam(required = false) Boolean copiaDañada) {

        Boolean danada = null;

        // Prioridad: body > query param
        if (body != null && body.containsKey("copiaDañada")) {
            danada = body.get("copiaDañada");
        } else if (copiaDañada != null) {
            danada = copiaDañada;
        }

        return ResponseEntity.ok(prestamoService.devolver(id, danada));
    }
}