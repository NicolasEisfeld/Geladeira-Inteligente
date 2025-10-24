package dev.nicolas.MagicFridgeAI.Controller;

import dev.nicolas.MagicFridgeAI.Model.FoodItemModel;
import dev.nicolas.MagicFridgeAI.Service.FoodItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodItemController {

    private final FoodItemService service;

    // Injetado via construtor
    public FoodItemController(FoodItemService foodItemService) {
        this.service = foodItemService;
    }

    // Post
    @PostMapping
    public ResponseEntity<FoodItemModel> criar(@RequestBody FoodItemModel foodItemModel) {
        FoodItemModel salvo = service.salvar(foodItemModel);
        return ResponseEntity.ok(salvo);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<FoodItemModel> atualizar(@RequestBody FoodItemModel foodItemModel, @PathVariable Long id) {
        return service.buscarPorId(id)
                .map(itemExistente -> {
                    foodItemModel.setId(itemExistente.getId());
                    FoodItemModel atualizado = service.alterar(foodItemModel);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<FoodItemModel>> listar() {
        List<FoodItemModel> lista = service.listar();
        return ResponseEntity.ok(lista);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
