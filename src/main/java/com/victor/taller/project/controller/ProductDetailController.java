package com.victor.taller.project.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.victor.taller.project.service.ProductDetailService;
import com.victor.taller.project.soa.bean.ProductBean;
import com.victor.taller.project.soa.bean.ProductDetailBean;
import com.victor.taller.project.soa.request.GenericRequest;
import com.victor.taller.project.soa.response.GenericResponse;

@RestController
@RequestMapping(value = "/pdc")
public class ProductDetailController {

	private static final Logger logger = LogManager.getLogger(ProductDetailController.class);

	@Autowired
	private ProductDetailService productDetailService;
	
	@RequestMapping(value = "/spd", method = RequestMethod.POST)
	public GenericResponse<ProductDetailBean> saveProductDetail(@RequestBody GenericRequest<ProductDetailBean> request) {
		logger.info("ProductDetailController.saveProductDetail()");
		GenericResponse<ProductDetailBean> response = new GenericResponse<>();
		ProductDetailBean productDetail = new ProductDetailBean();
		productDetail = productDetailService.saveProductDetail(request.getData());
		response.setData(productDetail);
		return response;
	}
	
	@RequestMapping(value = "/gpd", method = RequestMethod.POST)
	public GenericResponse<List<Map<String, Object>>> getProductsDetail(@RequestBody GenericRequest<ProductBean> request) {
		logger.info("ProductDetailController.getProductDetail()");
		GenericResponse<List<Map<String, Object>>> response = new GenericResponse<>();
		List<Map<String, Object>> productDetailList = productDetailService.getProductDetail(request.getData());
		response.setData(productDetailList);
		return response;
	}
	
	@RequestMapping(value = "/gpdbi", method = RequestMethod.POST)
	public GenericResponse<ProductDetailBean> getProductDetailById(@RequestBody GenericRequest<ProductDetailBean> request) {
		logger.info("ProductDetailController.getProductDetailById()");
		GenericResponse<ProductDetailBean> response = new GenericResponse<>();
		ProductDetailBean productDetail = new ProductDetailBean();
		productDetail = productDetailService.getProductDetailById(request.getData().getId());
		response.setData(productDetail);
		return response;
	}
	
	@RequestMapping(value = "/lf/{productDetailId}", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public GenericResponse loadFile(final HttpServletRequest httpRequest, @PathVariable("productDetailId") Integer productDetailId) throws IOException, ServletException {
		logger.info("ProductDetailController.loadFile()");
		GenericResponse<ProductBean> response = new GenericResponse<>();
		String path = "d:/tallerProject/opt/product_files/product_detail_files";
		ProductDetailBean productDetail = productDetailService.getProductDetailById(productDetailId);
		String customFilePath = path;
		Collection<Part> parts = httpRequest.getParts();
		if(parts.size() > 0) {
			Part part = parts.iterator().next();
			InputStream file = part.getInputStream();
			byte[] bytes = IOUtils.toByteArray(file);
			customFilePath = path + File.separator + part.getSubmittedFileName();
			File newFile = new File(customFilePath);
			if(!newFile.exists()) {
				newFile.getParentFile().mkdirs();
				newFile.createNewFile();
			}
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFile));
			stream.write(bytes);
			stream.close();
		}
		productDetail.setImagePath(customFilePath);
		productDetailService.saveProductDetail(productDetail);
		return response;
	}
	
	@RequestMapping(value = "/gf/{producDetailtId}")
	public ResponseEntity<Resource> getFile(@PathVariable("producDetailtId") Integer productDetailId) throws MalformedURLException {
		logger.info("ProductController.getFile()");
		GenericResponse<ProductBean> response = new GenericResponse<>();
		ProductDetailBean productDetail = productDetailService.getProductDetailById(productDetailId);
		Path file = Paths.get(productDetail.getImagePath());
		Resource resourceFile = new UrlResource(file.toUri());
		if (resourceFile.exists() || resourceFile.isReadable()) {
			String imagePath = productDetail.getImagePath();
			int intIndex = productDetail.getImagePath().lastIndexOf(".");
			switch(imagePath.substring(intIndex).toUpperCase()) {
			case ".PNG":
				return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + resourceFile.getFilename() + "\"").contentType(MediaType.IMAGE_PNG).body(resourceFile);
			case ".JPEG":
				return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + resourceFile.getFilename() + "\"").contentType(MediaType.IMAGE_JPEG).body(resourceFile);
			case ".JPG":
				return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + resourceFile.getFilename() + "\"").contentType(MediaType.IMAGE_JPEG).body(resourceFile);
			case ".GIF":
				return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + resourceFile.getFilename() + "\"").contentType(MediaType.IMAGE_GIF).body(resourceFile);
			}
			
		} else {
			throw new RuntimeException("Error en la imagen" + resourceFile.getFilename());
		}
		return null;
	}
	
	
}
