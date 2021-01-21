package com.victor.taller.project.service;

import java.util.List;

import com.victor.taller.project.soa.bean.CatalogDetailBean;

public interface CatalogDetailService {
	
	abstract List<CatalogDetailBean> getListCatalogDetailByCatalogId(CatalogDetailBean catalogDetailBean);

}
