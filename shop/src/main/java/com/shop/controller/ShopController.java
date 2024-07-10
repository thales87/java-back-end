	package com.shop.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.client.dto.ShopDTO;
import com.client.dto.ShopReportDTO;
import com.shop.service.ShopService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ShopController {

	private final ShopService shopService;
	
	@GetMapping("/shopping")
	public List<ShopDTO> getShops(){
		return shopService.getAll();
	}
	
	@GetMapping("/shopping/shopByUser/{userIdentifier}")
	public List<ShopDTO> getShops(@PathVariable String userIdentifier){
		return shopService.getByUser(userIdentifier);
	}
	
	@GetMapping("/shopping/shopByDate/{days}")
	public List<ShopDTO> getShops(@PathVariable int days){
		return shopService.getByDate(days);
	}
	
	@GetMapping("/shopping/{id}")
	public ShopDTO findById(@PathVariable Long id){
		return shopService.findById(id);
	}
	
	@PostMapping("/shopping")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ShopDTO newShop(@RequestHeader(name = "key",required = true) String key, @Valid @RequestBody ShopDTO shopDTO) {
		return shopService.save(shopDTO,key);
	}
	
	@GetMapping("/shopping/search")
	public List<ShopDTO> getShopsByFilter(
		@RequestParam(name="dataInicio", required = true)
		@DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataInicio,
		@RequestParam(name="dataFim", required = false)
		@DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataFim,
		@RequestParam(name="valorMinimo", required=false)
		Float valorMinimo){
		return shopService.getShopsByFilter(dataInicio, dataFim, valorMinimo);
	}
	
	@GetMapping("/shopping/report")
	public ShopReportDTO getShopsByDate(
		@RequestParam(name="dataInicio", required = true)
		@DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataInicio,
		@RequestParam(name="dataFim", required = true)
		@DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataFim) {
		return shopService.getReportByDate(dataInicio, dataFim);
	}
	
}
