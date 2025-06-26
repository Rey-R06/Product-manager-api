package com.rey06.product_manager_api.repository;

import com.rey06.product_manager_api.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemPedido extends JpaRepository<ItemPedido, Integer> {
}
