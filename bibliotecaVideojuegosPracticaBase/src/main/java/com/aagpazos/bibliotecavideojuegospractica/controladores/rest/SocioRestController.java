package com.aagpazos.bibliotecavideojuegospractica.controladores.rest;

import com.aagpazos.bibliotecavideojuegospractica.dto.SocioDTO;
import com.aagpazos.bibliotecavideojuegospractica.service.SocioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/socios")
@Tag(name = "Socios", description = "Gestión de socios de la biblioteca")
public class SocioRestController {

    private final SocioService socioService;

    public SocioRestController(SocioService socioService) {
        this.socioService = socioService;
    }

    // RF-04 - Listar / buscar socios
    @GetMapping
    @Operation(summary = "Listar socios", description = "Filtros opcionales: nombre, email y activo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de socios (puede estar vacía)")
    })
    public ResponseEntity<List<SocioDTO>> listar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Boolean activo) {

        SocioDTO filtro = new SocioDTO(nombre, email, activo);
        return ResponseEntity.ok(socioService.listar(filtro));
    }

    // RF-05 - Detalle de un socio
    @GetMapping("/{id}")
    @Operation(summary = "Obtener un socio por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Socio encontrado"),
            @ApiResponse(responseCode = "404", description = "No existe el socio")
    })
    public ResponseEntity<SocioDTO> obtener(@PathVariable int id) {
        return ResponseEntity.ok(socioService.obtener(id));
    }

    // RF-06 - Crear socio
    @PostMapping
    @Operation(summary = "Crear un nuevo socio")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Socio creado"),
            @ApiResponse(responseCode = "400", description = "Datos no válidos"),
            @ApiResponse(responseCode = "409", description = "El email ya existe")
    })
    public ResponseEntity<SocioDTO> crear(@RequestBody SocioDTO dto) {
        SocioDTO creado = socioService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // RF-07 - Actualizar socio
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un socio")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Socio actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos no válidos"),
            @ApiResponse(responseCode = "404", description = "No existe el socio"),
            @ApiResponse(responseCode = "409", description = "El email ya pertenece a otro socio")
    })
    public ResponseEntity<SocioDTO> actualizar(@PathVariable int id, @RequestBody SocioDTO dto) {
        return ResponseEntity.ok(socioService.actualizar(id, dto));
    }

    // RF-08 - Borrar socio
    @DeleteMapping("/{id}")
    @Operation(summary = "Borrar un socio")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Socio borrado"),
            @ApiResponse(responseCode = "404", description = "No existe el socio"),
            @ApiResponse(responseCode = "409", description = "El socio tiene préstamos y no se puede borrar")
    })
    public ResponseEntity<Void> borrar(@PathVariable int id) {
        socioService.borrar(id);
        return ResponseEntity.noContent().build(); // 204 sin body
    }
}