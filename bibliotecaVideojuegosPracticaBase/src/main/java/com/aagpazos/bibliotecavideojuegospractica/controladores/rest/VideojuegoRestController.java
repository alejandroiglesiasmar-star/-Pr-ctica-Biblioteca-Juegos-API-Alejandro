package com.aagpazos.bibliotecavideojuegospractica.controladores.rest;

import com.aagpazos.bibliotecavideojuegospractica.dto.VideojuegoDTO;
import com.aagpazos.bibliotecavideojuegospractica.service.VideojuegoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videojuegos")
@Tag(name = "Videojuegos", description = "Gestión del catálogo de videojuegos")
public class VideojuegoRestController {

    private final VideojuegoService videojuegoService;

    public VideojuegoRestController(VideojuegoService videojuegoService) {
        this.videojuegoService = videojuegoService;
    }

    // RF-01 - Listar / buscar videojuegos
    @GetMapping
    @Operation(summary = "Listar videojuegos", description = "Permite filtrar por título, desarrolladora, año, PEGI y disponibilidad")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de videojuegos (puede estar vacía)")
    })
    public ResponseEntity<List<VideojuegoDTO>> listar(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Integer idDesarrolladora,
            @RequestParam(required = false) Integer anioDesde,
            @RequestParam(required = false) Integer pegiMax,
            @RequestParam(required = false) Boolean soloDisponibles) {

        // Usamos el constructor de filtros que ya tienes en el DTO
        VideojuegoDTO filtro = new VideojuegoDTO(
                titulo,
                idDesarrolladora,
                anioDesde,
                pegiMax,
                soloDisponibles != null && soloDisponibles
        );

        return ResponseEntity.ok(videojuegoService.listar(filtro));
    }

    // RF-02 - Detalle de un videojuego
    @GetMapping("/{id}")
    @Operation(summary = "Obtener un videojuego por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Videojuego encontrado"),
            @ApiResponse(responseCode = "404", description = "No existe el videojuego")
    })
    public ResponseEntity<VideojuegoDTO> obtener(@PathVariable int id) {
        return ResponseEntity.ok(videojuegoService.obtener(id));
    }

    // RF-03 - Crear videojuego
    @PostMapping
    @Operation(summary = "Crear un nuevo videojuego")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Videojuego creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos no válidos"),
            @ApiResponse(responseCode = "404", description = "La desarrolladora no existe")
    })
    public ResponseEntity<VideojuegoDTO> crear(@RequestBody VideojuegoDTO dto) {
        VideojuegoDTO creado = videojuegoService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
}