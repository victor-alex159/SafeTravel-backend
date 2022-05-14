package com.safetravel.taller.project.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.safetravel.taller.project.entity.OrganizationEntity;
import com.safetravel.taller.project.entity.ProductEntity;
import com.safetravel.taller.project.entity.ServiceEntity;
import com.safetravel.taller.project.repository.jpa.ProductJpaRepository;
import com.safetravel.taller.project.repository.jpa.ServiceJpaRepository;
import com.safetravel.taller.project.service.ProductService;
import com.safetravel.taller.project.soa.bean.OrganizationBean;
import com.safetravel.taller.project.soa.bean.ProductBean;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductJpaRepository productRepository;
	
	@Autowired
	private ServiceJpaRepository serviceJpaRepository;

	@Override
	public ProductBean saveProduct(ProductBean productBean) {
		ProductEntity productEntity = new ProductEntity();
		BeanUtils.copyProperties(productBean, productEntity);
		if(productBean.getOrganization() != null) {
			productEntity.setOrganization(new OrganizationEntity());
			productEntity.getOrganization().setId(productBean.getOrganization().getId());			
		}
		
		productEntity = productRepository.save(productEntity);
		productBean.setId(productEntity.getId());
		return productBean;
	}

	@Override
	public List<ProductBean> getAllProducts() {
		List<ProductBean> result = new ArrayList<>();
		List<ProductEntity> list = productRepository.getAllProducts();
		if(list != null) {
			list.forEach(productEntity -> {
				ProductBean productBean = new ProductBean();
				BeanUtils.copyProperties(productEntity, productBean);
				if(productEntity.getOrganization() != null) {
					productBean.setOrganization(new OrganizationBean());
					productBean.getOrganization().setId(productEntity.getOrganization().getId());
				}
				result.add(productBean);
			});
		}
		
		return result;
	}

	@Override
	public ProductBean getProductById(Integer productId) {
		ProductEntity productEntity = productRepository.getProductById(productId);
		ProductBean productBean = new ProductBean();
		if(productEntity != null) {
			BeanUtils.copyProperties(productEntity, productBean);
			if(productEntity.getOrganization() != null) {
				productBean.setOrganization(new OrganizationBean());
				productBean.getOrganization().setId(productEntity.getOrganization().getId());
			}
			return productBean;
		}
		return null;
	}

	@Override
	public List<ProductBean> getProductsByUserPrincipal(Integer organizationId) {
		List<ProductBean> result = new ArrayList<>();
		List<ProductEntity> list = productRepository.getProductsByUserPrincipal(organizationId);
		if(list != null) {
			list.forEach(productEntity -> {
				ProductBean productBean = new ProductBean();
				BeanUtils.copyProperties(productEntity, productBean);
				if(productEntity.getOrganization() != null) {
					productBean.setOrganization(new OrganizationBean());
					productBean.getOrganization().setId(productEntity.getOrganization().getId());
				}
				result.add(productBean);
			});
		}
		
		return result;
	}

	@Override
	public List<ProductBean> getProductByType(ProductBean productBean) {
		List<ProductEntity> list = productRepository.getProductByType(productBean.getType());
		List<ProductBean> result = new ArrayList<ProductBean>();
		if(list != null) {
			list.forEach(productEntity -> {
				ProductBean prodBean = new ProductBean();
				BeanUtils.copyProperties(productEntity, prodBean);
				if(productEntity.getOrganization() != null) {
					prodBean.setOrganization(new OrganizationBean());
					prodBean.getOrganization().setId(productEntity.getOrganization().getId());
				}
				result.add(prodBean);
			});
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getProductByNameAndDates(ProductBean productBean) {
		List<Object[]> listResult = productRepository.getProductByNameAndDates(productBean);
		if(listResult != null) {
			List<Map<String, Object>> result = new ArrayList<>();
			listResult.forEach(response -> {
				Map<String, Object> map = new HashMap<>();
				map.put("id", response[0]);
				map.put("name", response[1]);
				map.put("image", response[2]);
				map.put("description", response[3]);
				map.put("price", response[4]);
				map.put("price_min", response[5]);
				map.put("price_max", response[6]);
				map.put("ubication", response[7]);
				map.put("startDate", response[8]);
				map.put("endDate", response[9]);
				result.add(map);
			});
			return result;
		}
		
		return null;
	}
	
	@Override
	public void deleteProduct(Integer productId) {
		if(productId != null) {
			productRepository.delete(productId);
		}
	}
	
	@Override
	public List<ProductBean> getProductsDisabled() {
		List<ProductEntity> list = productRepository.getAllProductsDisabled();
		List<ProductBean> result = new ArrayList<ProductBean>();
		if(list != null) {
			list.forEach(productEntity -> {
				ProductBean productBean = new ProductBean();
				BeanUtils.copyProperties(productEntity, productBean);
				if(productEntity.getOrganization() != null) {
					productBean.setOrganization(new OrganizationBean());
					productBean.getOrganization().setId(productEntity.getOrganization().getId());
				}
				result.add(productBean);
			});
			return result;
		}
		return null;
	}
	
	@Override
	public void updateStatus(Integer productId) {
		if(productId != null) {
			productRepository.updateStatus(productId);
		}
		
	}

	@Override
	public XSSFWorkbook generateReportExcel() throws IOException {
		List<ProductEntity> listProducts = productRepository.getAllProducts();
		if(listProducts != null) {
			XSSFWorkbook workbook = new XSSFWorkbook(this.getClass().getClassLoader().getResourceAsStream("files" + File.separator + "reporte-productos.xlsx" ));
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			CellStyle styleDefault = workbook.createCellStyle();
			styleDefault.setBorderBottom(BorderStyle.THIN);
			styleDefault.setBorderTop(BorderStyle.THIN);
			styleDefault.setBorderRight(BorderStyle.THIN);
			styleDefault.setBorderLeft(BorderStyle.THIN);
			styleDefault.setWrapText(true);
			
			int i = 0;
			
			Row rowHeader = sheet.createRow(0);
			Cell cellHeader = rowHeader.createCell(0);
			cellHeader.setCellValue("N°");
			cellHeader.setCellStyle(styleDefault);
			
			cellHeader = rowHeader.createCell(1);
			cellHeader.setCellValue("Empresa");
			cellHeader.setCellStyle(styleDefault);
			
			cellHeader = rowHeader.createCell(2);
			cellHeader.setCellValue("Tipo de empresa");
			cellHeader.setCellStyle(styleDefault);
			
			cellHeader = rowHeader.createCell(3);
			cellHeader.setCellValue("Correo administrador");
			cellHeader.setCellStyle(styleDefault);
			
			cellHeader = rowHeader.createCell(4);
			cellHeader.setCellValue("Dirección");
			cellHeader.setCellStyle(styleDefault);
			
			cellHeader = rowHeader.createCell(5);
			cellHeader.setCellValue("Servicios");			
			cellHeader.setCellStyle(styleDefault);
			
			for(ProductEntity product: listProducts) {
				Row row = sheet.createRow(i+1);
				
				Cell cell0 = row.createCell(0);
				cell0.setCellValue(i+1);
				cell0.setCellStyle(styleDefault);
				
				Cell cell1 = row.createCell(1);
				cell1.setCellValue(product.getName());
				cell1.setCellStyle(styleDefault);
				
				Cell cell2 = row.createCell(2);
				cell2.setCellValue(product.getType());
				cell2.setCellStyle(styleDefault);
				
				Cell cell3 = row.createCell(3);
				cell3.setCellValue(product.getEmailAdmin());
				cell3.setCellStyle(styleDefault);
				
				Cell cell4 = row.createCell(4);
				cell4.setCellValue(product.getUbication());
				cell4.setCellStyle(styleDefault);
				
				Cell cell5 = row.createCell(5);
				if(product.getServiceId() != null) {
					List<String> codes = Arrays.asList(product.getServiceId().split(","));
					List<ServiceEntity> listServiceByCode = serviceJpaRepository.getListServiceByCodes(codes);
					String listServices = "";
					for(ServiceEntity service: listServiceByCode) {
						listServices = listServices + service.getDescription() + ", ";
					}
					listServices = listServices.substring(0, listServices.length() -2);
					cell5.setCellValue(listServices);
					cell5.setCellStyle(styleDefault);
					
				} else {
					cell5.setCellValue("SERVICIOS NO REGISTRADOS");
					cell5.setCellStyle(styleDefault);
				}
				
				i++;
			}
			return workbook;
		}
		return null;
	}

}
