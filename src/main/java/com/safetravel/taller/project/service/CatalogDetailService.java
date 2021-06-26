package com.safetravel.taller.project.service;

import java.util.List;

import com.safetravel.taller.project.soa.bean.CatalogDetailBean;

public interface CatalogDetailService {
	
	abstract List<CatalogDetailBean> getListCatalogDetailByCatalogId(CatalogDetailBean catalogDetailBean);

}
