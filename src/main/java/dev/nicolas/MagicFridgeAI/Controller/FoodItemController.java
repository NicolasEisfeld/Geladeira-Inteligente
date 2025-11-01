package dev.nicolas.MagicFridgeAI.Controller;

import dev.nicolas.MagicFridgeAI.Model.FoodItemModel;
import dev.nicolas.MagicFridgeAI.Service.FoodItemService;
import dev.nicolas.MagicFridgeAI.Service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodItemController {

    private final FoodItemService service;
    private final ChatService chatService;

    // Injetado via construtor
    public FoodItemController(FoodItemService foodItemService, ChatService chatService) {
        this.service = foodItemService;
        this.chatService = chatService;
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

    // Get by id
    @GetMapping("/{id}")
    public ResponseEntity<FoodItemModel> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Gerar receita usando ChatService a partir dos alimentos cadastrados
    @GetMapping("/recipe")
    public ResponseEntity<String> gerarReceita() {
        List<FoodItemModel> itens = service.listar();
        if (itens.isEmpty()) {
            return ResponseEntity.ok("Nenhum ingrediente cadastrado para gerar uma receita.");
        }

        StringBuilder prompt = new StringBuilder();
        prompt.append("Crie uma receita simples em português usando apenas os seguintes ingredientes:\n");
        for (FoodItemModel it : itens) {
            prompt.append("- ").append(it.getName() == null ? "" : it.getName());
            if (it.getQuantity() != null) prompt.append(" (quantidade: ").append(it.getQuantity()).append(")");
            if (it.getCategory() != null && !it.getCategory().isBlank()) prompt.append(" - categoria: ").append(it.getCategory());
            if (it.getExpirationDate() != null && !it.getExpirationDate().isBlank()) prompt.append(" - validade: ").append(it.getExpirationDate());
            prompt.append("\n");
        }
        prompt.append("\nResponda com título da receita, lista de ingredientes e modo de preparo. Mantenha a receita curta e prática.");

        try {
            String resposta = chatService.enviarMensagem(prompt.toString());
            if (resposta == null) resposta = "Nenhuma resposta recebida do serviço de geração.";
            return ResponseEntity.ok(resposta);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao gerar receita: " + ex.getMessage());
        }
    }
}
