package org.example.user_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.user_service.dto.UserRequestDto;
import org.example.user_service.dto.UserResponseDto;
import org.example.user_service.service.HateoasService;
import org.example.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "API для управления пользователями")
public class UserController {
    private final UserService userService;
    private final HateoasService hateoasService;

    @Autowired
    public UserController(UserService userService, HateoasService hateoasService) {
        this.userService = userService;
        this.hateoasService = hateoasService;
    }

    @Operation(summary = "Получить всех пользователей", description = "Возвращает список всех пользователей в системе с HATEOAS ссылками")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Список пользователей успешно получен",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = UserResponseDto.class)))
    })
    @GetMapping
    public CollectionModel<UserResponseDto> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return hateoasService.addLinksToCollection(users);
    }

    @Operation(summary = "Получить пользователя по ID", description = "Возвращает пользователя по указанному идентификатору с HATEOAS ссылками")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пользователь найден",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = UserResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @GetMapping("/{id}")
    public UserResponseDto getUserById(
            @Parameter(description = "ID пользователя", example = "1", required = true)
            @PathVariable Long id) {
        UserResponseDto user = userService.getUsersById(id);
        return hateoasService.addLinks(user);
    }

    @Operation(summary = "Создать нового пользователя", description = "Создает нового пользователя в системе и возвращает его с HATEOAS ссылками")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пользователь успешно создан",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = UserResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Некорректные данные пользователя")
    })
    @PostMapping("save")
    public UserResponseDto createUsers(
            @Parameter(description = "Данные пользователя для создания", required = true)
            @Valid @RequestBody UserRequestDto userRequestDto){
        UserResponseDto user = userService.createUser(userRequestDto);
        return hateoasService.addLinks(user);
    }

    @Operation(summary = "Обновить пользователя", description = "Обновляет данные существующего пользователя и возвращает его с HATEOAS ссылками")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пользователь успешно обновлен",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = UserResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
        @ApiResponse(responseCode = "400", description = "Некорректные данные пользователя")
    })
    @PutMapping("/{id}")
    public UserResponseDto updateUsers(
            @Parameter(description = "Новые данные пользователя", required = true)
            @RequestBody UserRequestDto userRequestDto, 
            @Parameter(description = "ID пользователя для обновления", example = "1", required = true)
            @PathVariable Long id){
        UserResponseDto user = userService.updateUser(id, userRequestDto);
        return hateoasService.addLinks(user);
    }

    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя по указанному ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пользователь успешно удален"),
        @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsers(
            @Parameter(description = "ID пользователя для удаления", example = "1", required = true)
            @PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        if (ex.getMessage().contains("not found")) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
