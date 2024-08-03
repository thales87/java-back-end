package com.shop.service;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.client.dto.ItemDTO;
import com.client.dto.ProductDTO;
import com.client.dto.ShopDTO;
import com.client.dto.UserDTO;
import com.shop.model.Shop;
import com.shop.repository.ShopRepository;

@ExtendWith(MockitoExtension.class)
public class ShopServiceTest {

	@InjectMocks
	private ShopService shopService;

	@Mock
	private UserService userService;

	@Mock
	private ProductService productService;

	@Mock
	private ShopRepository shopRepository;

	@Test
	public void test_saveShop() {

		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setProductIdentifier("123");
		itemDTO.setPrice(100F);

		ShopDTO shopDTO = new ShopDTO();
		shopDTO.setUserIdentifier("123");
		shopDTO.setItems(new ArrayList<>());
		shopDTO.getItems().add(itemDTO);
		shopDTO.setTotal(100F);

		ProductDTO productDTO = new ProductDTO();
		productDTO.setProductIdentifier("123");
		productDTO.setPreco(100F);

		Mockito.when(userService.getUserByCpf("123", "123")).thenReturn(new UserDTO());
		Mockito.when(productService.getProductByIdentifier("123")).thenReturn(productDTO);
		Mockito.when(shopRepository.save(Mockito.any())).thenReturn(Shop.convert(shopDTO));

		shopDTO = shopService.save(shopDTO, "123");
		Assertions.assertEquals(100F, shopDTO.getTotal());
		Assertions.assertEquals(1, shopDTO.getItems().size());
		Mockito.verify(shopRepository, Mockito.times(1)).save(Mockito.any());
	}
}
