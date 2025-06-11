package com.belval.crudrest.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CarrinhoDeCompra {

	private Map<Integer, ItemCompra> mapItens = new HashMap<>();
	
	public void addProduto(Produto prod, BigDecimal quantidade) {
		ItemCompra itemCompra = mapItens.get(prod.getId());
		if (itemCompra == null) {
			itemCompra = new ItemCompra();
			itemCompra.setProduto(prod);
			itemCompra.setQuantidade(quantidade);
			itemCompra.setValorItem(prod.getPreco());
			mapItens.put(prod.getId(), itemCompra);
		} else {
			itemCompra.setQuantidade(itemCompra.getQuantidade().add(quantidade));
			if (itemCompra.getQuantidade().compareTo(BigDecimal.ZERO) <= 0) {
				mapItens.remove(prod.getId());
			}
		}
	}
}
