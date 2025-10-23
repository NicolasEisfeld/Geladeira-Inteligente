package dev.nicolas.MagicFridgeAI.Controller;

import dev.nicolas.MagicFridgeAI.Model.FoodItemModel;
import dev.nicolas.MagicFridgeAI.Service.FoodItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Service

@RestController
@RequestMapping("/food")
public class FoodItemController {

    private FoodItemService foodItemService;

    // Insertado via construtor
    public FoodItemController(FoodItemService foodItemService) {
        this.foodItemService = foodItemService;
    }

    // Get

    // Post
    public ResponseEntity<FoodItemModel> criar(@RequestBody FoodItemModel foodItemModel) {
        FoodItemModel salvo = service.salvar(foodItemModel);
        return ResponseEntity.ok(salvo);
    }

    // Update
    public ResponseEntity<FoodItemModel> atualizar(@RequestBody FoodItemModel foodItemModel) {
        FoodItemModel atualizado = service.
    }

    // Delete
}
