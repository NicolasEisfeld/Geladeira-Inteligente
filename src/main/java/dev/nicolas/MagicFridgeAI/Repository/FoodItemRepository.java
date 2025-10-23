package dev.nicolas.MagicFridgeAI.Repository;

import dev.nicolas.MagicFridgeAI.Model.FoodItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItemModel, Long> {
}
