package com.shop.repository;

import java.time.LocalDate;
import java.util.List;

import com.client.dto.ShopReportDTO;
import com.shop.model.Shop;

public interface ReportRepository {

	public List<Shop> getShopByFilters(LocalDate dataInicio, LocalDate dataFim, Float valorMinimo);
	
	public ShopReportDTO getReportByDate(LocalDate dataInicio, LocalDate dataFim);
}
