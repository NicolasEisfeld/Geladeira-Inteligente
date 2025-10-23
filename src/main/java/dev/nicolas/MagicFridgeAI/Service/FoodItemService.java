package dev.nicolas.MagicFridgeAI.Service;

import dev.nicolas.MagicFridgeAI.Model.FoodItemModel;
import dev.nicolas.MagicFridgeAI.Repository.FoodItemRepository;
import org.springframework.stereotype.Service;
import java.util.List

@Service
public class FoodItemService {
    private FoodItemRepository repository;

    public FoodItemService(FoodItemRepository repository) {
        this.repository = repository;
    }

    public FoodItemModel salvar(FoodItemModel foodItemModel) {
        return repository.save(foodItemModel);
    }

    public List<FoodItemModel>listar() {
        return repository.findAll();
    }
}
