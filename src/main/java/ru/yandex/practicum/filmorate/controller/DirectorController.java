package ru.yandex.practicum.filmorate.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.service.DirectorService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/directors")
@RequiredArgsConstructor
public class DirectorController {
    private final DirectorService directorService;

    @GetMapping
    public List<Director> getAllDirectors() {
        log.info("Пришел GET запрос /directors");
        List<Director> response = directorService.getAll();
        log.info("Отправлен ответ GET /directors с телом {}", response);
        return response;

    }

    @GetMapping("/{id}")
    public Director getDirector(@PathVariable Integer id) {
        log.info("Пришел GET запрос /directors/{}", id);
        Director response = directorService.getDirector(id);
        log.info("Отправлен GET ответ /directors/{} с телом  {}", id, response);
        return response;
    }

    @PostMapping
    public Director createDirector(@Valid @RequestBody Director director) {
        log.info("Пришел POST запрос /directors с телом {}", director);
        Director response = directorService.createDirector(director);
        log.info("Отправлен POST ответ /directors с телом {}", director);
        return response;
    }

    @PutMapping
    public Director updateDirector(@Valid @RequestBody Director director) {
        log.info("Пришел PUT запрос /directors c телом {}", director);
        Director response = directorService.updateDirector(director);
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
