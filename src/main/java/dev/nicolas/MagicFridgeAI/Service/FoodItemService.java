
package dev.nicolas.MagicFridgeAI.Service;

import dev.nicolas.MagicFridgeAI.Model.FoodItemModel;
import dev.nicolas.MagicFridgeAI.Repository.FoodItemRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FoodItemService {
    private final FoodItemRepository repository;

    public FoodItemService(FoodItemRepository repository) {
        this.repository = repository;
    }

    public FoodItemModel salvar(FoodItemModel foodItemModel) {
        return repository.save(foodItemModel);
    }

    public List<FoodItemModel> listar() {
        return repository.findAll();
    }

    public FoodItemModel alterar(FoodItemModel foodItemModel) {
            return repository.save(foodItemModel);
    }

    public boolean deletar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
    public Optional<FoodItemModel> buscarPorId(Long id) {
        return repository.findById(id);
    }

}
