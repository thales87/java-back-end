package com.client.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopDTO {

	private String userIdentifier;
	private Float total;
	private LocalDateTime date;
	private List<ItemDTO> items;

}
