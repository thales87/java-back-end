package com.shop.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.client.dto.ItemDTO;
import com.client.dto.ProductDTO;
import com.client.dto.ShopDTO;
import com.client.dto.ShopReportDTO;
import com.shop.dto.DTOConverter;
import com.shop.model.Shop;
import com.shop.repository.ShopRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopService {

	@Autowired
	private ShopRepository shopRepository;

	private final ProductService productService;

	@Autowired
	private UserService userService;

	public List<ShopDTO> getAll() {
		List<Shop> shops = shopRepository.findAll();
		return shops.stream().map(DTOConverter::convert).collect(Collectors.toList());
	}

	public List<ShopDTO> getByUser(String userIdentifier) {
		List<Shop> shops = shopRepository.findAllByUserIdentifier(userIdentifier);
		return shops.stream().map(DTOConverter::convert).collect(Collectors.toList());
	}

	public List<ShopDTO> getByDate(int days) {
		List<Shop> shops = shopRepository.findAllByDateGreaterThan(LocalDateTime.now().minusDays(days));
		return shops.stream().map(DTOConverter::convert).collect(Collectors.toList());
	}

	public ShopDTO findById(Long productId) {
		Optional<Shop> shop = shopRepository.findById(productId);
		if (shop.isPresent()) {
			return DTOConverter.convert(shop.get());
		}
		return null;
	}

	public ShopDTO save(ShopDTO shopDTO,String key) {
		userService.getUserByCpf(shopDTO.getUserIdentifier(), key);
		validateProducts(shopDTO.getItems());
		
		shopDTO.setTotal(shopDTO.getItems().stream().map(x -> x.getPrice()).reduce((float) 0, Float::sum));
		
		Shop shop = Shop.convert(shopDTO);
		shop.setDate(LocalDateTime.now());
		shop = shopRepository.save(shop);
		return DTOConverter.convert(shop);
	}

	private boolean validateProducts(List<ItemDTO> items) {
		for (ItemDTO item : items) {
			ProductDTO productDTO = productService.getProductByIdentifier(item.getProductIdentifier());
			if (productDTO == null) {
				return false;
			}
			item.setPrice(productDTO.getPreco());
		}
		return true;
	}

	public List<ShopDTO> getShopsByFilter(LocalDate dataInicio, LocalDate dataFim, Float valorMinimo) {
		List<Shop> shops = shopRepository.getShopByFilters(dataInicio, dataFim, valorMinimo);
		return shops.stream().map(DTOConverter::convert).collect(Collectors.toList());
	}

	public ShopReportDTO getReportByDate(LocalDate dataInicio, LocalDate dataFim) {
		return shopRepository.getReportByDate(dataInicio, dataFim);
	}
}
