package ru.yandex.practicum.filmorate.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.DirectorDto;
import ru.yandex.practicum.filmorate.service.DirectorService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/directors")
@RequiredArgsConstructor
public class DirectorController {
    private final DirectorService directorService;

    @GetMapping
    public List<DirectorDto> getAllDirectors() {
        log.info("Пришел GET запрос /directors");
        List<DirectorDto> response = directorService.getAllDirectors();
        log.info("Отправлен ответ GET /directors с телом {}", response);
        return response;

    }

    @GetMapping("/{id}")
    public DirectorDto getDirector(@PathVariable Integer id) {
        log.info("Пришел GET запрос /directors/{}", id);
        DirectorDto response = directorService.getDirector(id);
        log.info("Отправлен GET ответ /directors/{} с телом  {}", id, response);
        return response;
    }

    @PostMapping
    public DirectorDto createDirector(@Valid @RequestBody DirectorDto directorDto) {
        log.info("Пришел POST запрос /directors с телом {}", directorDto);
        DirectorDto response = directorService.createDirector(directorDto);
        log.info("Отправлен POST ответ /directors с телом {}", response);
        return response;
    }

    @PutMapping
    public DirectorDto updateDirector(@Valid @RequestBody DirectorDto directorDto) {
        log.info("Пришел PUT запрос /directors c телом {}", directorDto);
        DirectorDto response = directorService.updateDirector(directorDto);
        log.info("Отправлен PUT запрос /directors с телом {}", response);
        return response;
    }

    @DeleteMapping("/{id}")
    public void deleteDirector(@PathVariable Integer id) {
        log.info("Пришел DELETE запрос /directors/{}", id);
        directorService.deleteDirector(id);
        log.info("DELETE /directors/{}", id);
    }

}
