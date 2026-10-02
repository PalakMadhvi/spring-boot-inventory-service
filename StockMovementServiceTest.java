package com.example.inventory;

import com.example.inventory.dto.StockMovementRequest;
import com.example.inventory.entity.MovementType;
import com.example.inventory.exception.BusinessException;
import com.example.inventory.service.StockMovementService;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import com.example.inventory.entity.*;
import com.example.inventory.repository.StockMovementRepository;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockMovementServiceTest {

    @Test
    void stockOutCannotMakeQuantityNegative() {
        StockMovementRepository movementRepo = mock(StockMovementRepository.class);
        var inventoryService = mock(com.example.inventory.service.InventoryService.class);
        StockMovementService service = new StockMovementService(movementRepo, inventoryService);

        Product p = new Product();
        p.setId(1L);
        p.setSku("SKU-1");
        Warehouse w = new Warehouse();
        w.setId(1L);
        Inventory i = new Inventory();
        i.setProduct(p);
        i.setWarehouse(w);
        i.setQuantity(5);

        when(inventoryService.getOrCreateForUpdate(1L, 1L)).thenReturn(i);

        var request = new StockMovementRequest(1L, 1L, MovementType.OUT, 6, "REF-1", "Test");

        assertThrows(BusinessException.class, () -> service.move(request));
        verify(movementRepo, never()).save(any());
    }
}
