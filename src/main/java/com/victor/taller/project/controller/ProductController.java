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

import com.victor.taller.project.service.ProductService;
import com.victor.taller.project.soa.bean.ProductBean;
import com.victor.taller.project.soa.request.GenericRequest;
import com.victor.taller.project.soa.response.GenericResponse;

@RestController
@RequestMapping(value = "/pc")
public class ProductController {

	private static final Logger logger = LogManager.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/sp", method = RequestMethod.POST)
	public GenericResponse<ProductBean> saveProduct(@RequestBody GenericRequest<ProductBean> request) {
		logger.info("ProductController.saveProduct()");
		GenericResponse<ProductBean> response = new GenericResponse<>();
		ProductBean product = new ProductBean();
		product = productService.saveProduct(request.getData());
		response.setData(product);
		return response;
	}
	
	@RequestMapping(value = "/gap", method = RequestMethod.POST)
	public GenericResponse<ProductBean> getAllProducts(@RequestBody GenericRequest<ProductBean> request) {
		logger.info("ProductController.getAllProducts()");
		GenericResponse<ProductBean> response = new GenericResponse<>();
		List<ProductBean> productList = productService.getAllProducts();
		response.setDatalist(productList);
		return response;
	}
	
	@RequestMapping(value = "/lf/{productId}", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public GenericResponse loadFile(final HttpServletRequest httpRequest, @PathVariable("productId") Integer productId) throws IOException, ServletException {
		logger.info("ProductController.loadFile()");
		GenericResponse<ProductBean> response = new GenericResponse<>();
		String path = "d:/tallerProject/opt/product_files";
		ProductBean product = productService.getProductById(productId);
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
		product.setImagePath(customFilePath);
		productService.saveProduct(product);
		return response;
	}
	
	@RequestMapping(value = "/gf/{productId}")
	public ResponseEntity<Resource> getFile(@PathVariable("productId") Integer productId) throws MalformedURLException {
		logger.info("ProductController.getFile()");
		GenericResponse<ProductBean> response = new GenericResponse<>();
		ProductBean product = productService.getProductById(productId);
		Path file = Paths.get(product.getImagePath());
		Resource resourceFile = new UrlResource(file.toUri());
		if (resourceFile.exists() || resourceFile.isReadable()) {
			String imagePath = product.getImagePath();
			int intIndex = product.getImagePath().lastIndexOf(".");
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
