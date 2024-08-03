package com.shop.repository;

import java.time.LocalDate;
import java.util.List;

import com.client.dto.ShopReportDTO;
import com.shop.model.Shop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@PersistenceContext
public class ReportRepositoryImpl implements ReportRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Shop> getShopByFilters(LocalDate dataInicio, LocalDate dataFim, Float valorMinimo){
		StringBuilder sb = new StringBuilder();
		sb.append("select s from shop s where s.date >= :dataInicio");
		
		if(dataFim!=null) {
			sb.append(" and s.date <= :dataFim ");
		}
		
		if(valorMinimo!=null) {
			sb.append(" and s.total >= :valorMinimo ");
		}
		
		Query query = entityManager.createQuery(sb.toString());
		query.setParameter("dataInicio", dataInicio.atTime(0, 0));
		
		if(dataFim != null) {
			query.setParameter("dataFim", dataFim.atTime(23,59));
		}
		
		if(valorMinimo !=null) {
			query.setParameter("valorMinimo", valorMinimo);
		}
		//criar sql, criar objeto query, definir parametros
		return query.getResultList();
	}
	
	@Override
	public ShopReportDTO getReportByDate(LocalDate dataInicio, LocalDate dataFim) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(sp.id), sum(sp.total), avg(sp.total) ");
		sb.append("from shopping.shop sp ");
		sb.append("where sp.date >= :dataInicio ");
		sb.append("and sp.date <= :dataFim");
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("dataInicio", dataInicio);
		query.setParameter("dataFim", dataFim);
		
		Object[] result = (Object[])query.getSingleResult();
		ShopReportDTO shopReportDTO = new ShopReportDTO();
		shopReportDTO.setCount(((Long) result[0]).intValue());
		shopReportDTO.setTotal((Double) result[1]);
		shopReportDTO.setMean((Double) result[2]);
		return shopReportDTO;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
