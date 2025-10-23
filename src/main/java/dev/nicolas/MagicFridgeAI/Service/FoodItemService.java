package dev.nicolas.MagicFridgeAI.Service;

import dev.nicolas.MagicFridgeAI.Model.FoodItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class FoodItemService extends JpaRepository<FoodItemModel, Long> {
}
