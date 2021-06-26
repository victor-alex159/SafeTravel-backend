package com.safetravel.taller.project.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetravel.taller.project.entity.CatalogDetailEntity;
import com.safetravel.taller.project.repository.jpa.CatalogDetailJpaRepository;
import com.safetravel.taller.project.service.CatalogDetailService;
import com.safetravel.taller.project.soa.bean.CatalogBean;
import com.safetravel.taller.project.soa.bean.CatalogDetailBean;

@Service
public class CatalogDetailServiceImpl implements CatalogDetailService {

	@Autowired
	private CatalogDetailJpaRepository catalogDetailRepository;
	
	@Override
	public List<CatalogDetailBean> getListCatalogDetailByCatalogId(CatalogDetailBean catalogDetailBean) {
		List<CatalogDetailBean> result = new ArrayList<>();
		List<CatalogDetailEntity> list = catalogDetailRepository.getListCatalogDetailByCatalogId(catalogDetailBean.getCatalog().getId());
		if(list != null) {
			list.forEach(catalogDetailEntity -> {
				CatalogDetailBean cdBean = new CatalogDetailBean();
				BeanUtils.copyProperties(catalogDetailEntity, cdBean);
				if(catalogDetailEntity.getCatalog() != null) {
					cdBean.setCatalog(new CatalogBean());
					cdBean.getCatalog().setId(catalogDetailEntity.getCatalog().getId());
				}
				result.add(cdBean);
			});
			return result;
		}
		return null;
	}

}
