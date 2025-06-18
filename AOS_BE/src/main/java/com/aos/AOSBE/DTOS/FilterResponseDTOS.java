package com.aos.AOSBE.DTOS;

import java.util.List;

import lombok.Data;

@Data
public class FilterResponseDTOS {
	private int id;
	private String name;
	private String material;
	private String mainImage;
	private int categoryId;
	private boolean isCustom;
	private int turnBuy;
	private int rating;
	private String description;
	private int safetyStock;
	private boolean isActive;
	private int qty;
	private String listPriceRaw;

	public List<Double> getListPrice() {
		if (listPriceRaw == null || listPriceRaw.isEmpty())
			return List.of();
		return List.of(listPriceRaw.split(",")).stream().map(String::trim).map(Double::parseDouble).toList();
	}

	public FilterResponseDTOS(int id, String name, String material, String mainImage, int categoryId, boolean isCustom,
			int turnBuy, int rating, String description, boolean isActive, int safetyStock, int qty,
			String listPriceRaw) {
		this.id = id;
		this.name = name;
		this.material = material;
		this.mainImage = mainImage;
		this.categoryId = categoryId;
		this.isCustom = isCustom;
		this.turnBuy = turnBuy;
		this.rating = rating;
		this.description = description;
		this.safetyStock = safetyStock;
		this.isActive = isActive;
		this.qty = qty;
		this.listPriceRaw = listPriceRaw;
	}

	public FilterResponseDTOS() {
		// TODO Auto-generated constructor stub
	}
}
