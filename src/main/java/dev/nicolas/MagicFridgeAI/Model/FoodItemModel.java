package dev.nicolas.MagicFridgeAI.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "food_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemModel {

    private Long id;
    private String name;
    private String category;
    private Integer quantity;
    private String expirationDate;

}
