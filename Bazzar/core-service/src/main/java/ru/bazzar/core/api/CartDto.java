package ru.bazzar.core.api;

import ru.bazzar.core.api.CartItemDto;

import java.math.BigDecimal;
import java.util.List;

public class CartDto {
    private List<CartItemDto> items;
    private BigDecimal totalPrice;

    public CartDto() {
    }

    public CartDto(List<CartItemDto> items, BigDecimal totalPrice) {
        this.items = items;
        this.totalPrice = totalPrice;
    }

    public List<CartItemDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "CartDto{" +
                "items=" + items +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
