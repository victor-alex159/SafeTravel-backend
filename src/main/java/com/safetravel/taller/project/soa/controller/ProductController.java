package com.safetravel.taller.project.soa.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.safetravel.taller.project.util.UtilFunctions;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.safetravel.taller.project.service.ProductService;
import com.safetravel.taller.project.service.UserService;
import com.safetravel.taller.project.soa.bean.OrganizationBean;
import com.safetravel.taller.project.soa.bean.ProductBean;
import com.safetravel.taller.project.soa.bean.UserBean;
import com.safetravel.taller.project.soa.request.GenericRequest;
import com.safetravel.taller.project.soa.response.GenericResponse;

@RestController
@RequestMapping(value = "/pc")
public class ProductController {

	private static final Logger logger = LogManager.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/sp", method = RequestMethod.POST)
	public GenericResponse<ProductBean> saveProduct(@RequestBody GenericRequest<ProductBean> request) {
		logger.info("ProductController.saveProduct()");
		GenericResponse<ProductBean> response = new GenericResponse<>();
		ProductBean product = request.getData();
		ProductBean productAux = new ProductBean();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserBean user = userService.getUserByUsername(principal.toString());
		if(UtilFunctions.noEsNulo(user.getOrganizationId())) {
			product.setOrganization(new OrganizationBean());
			product.getOrganization().setId(user.getOrganizationId());			
		}
		productAux = productService.saveProduct(product);
		response.setData(productAux);
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
	
	/*@Cacheable("imagesCache")
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
	}*/
	
	@RequestMapping(value = "/gpbup", method = RequestMethod.POST)
	public GenericResponse<ProductBean> getProductsByUserPrincipal(@RequestBody GenericRequest<ProductBean> request) {
		logger.info("ProductController.getProductsByUserPrincipal()");
		GenericResponse<ProductBean> response = new GenericResponse<>();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserBean user = userService.getUserByUsername(principal.toString());
		List<ProductBean> productList = null;
		if(UtilFunctions.noEsNulo(user.getOrganizationId())) {
			productList = productService.getProductsByUserPrincipal(user.getOrganizationId());			
		}
		response.setDatalist(productList);
		return response;
	}
	
	@RequestMapping(value = "/gpbi", method = RequestMethod.POST)
	public GenericResponse<ProductBean> getProductById(@RequestBody GenericRequest<ProductBean> request) {
		logger.info("ProductController.getProductById()");
		GenericResponse<ProductBean> response = new GenericResponse<>();
		ProductBean product = new ProductBean();
		product = productService.getProductById(request.getData().getId());
		response.setData(product);
		return response;
	}
	
	@RequestMapping(value = "/gpbt", method = RequestMethod.POST)
	public GenericResponse<ProductBean> getProductByType(@RequestBody GenericRequest<ProductBean> request) {
		logger.info("ProductController.getProductByType()");
		GenericResponse<ProductBean> response = new GenericResponse<>();
		List<ProductBean> productList = productService.getProductByType(request.getData());
		response.setDatalist(productList);
		return response;
	}
	
	@RequestMapping(value = "/sv", method = RequestMethod.POST)
	public GenericResponse<ProductBean> save(@RequestPart("product") ProductBean productBean, @RequestPart("file") MultipartFile file) throws IOException {
		logger.info("ProductController.saveProduct()");
		GenericResponse<ProductBean> response = new GenericResponse<>();
		ProductBean product = new ProductBean();
		ProductBean productAux = productBean;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserBean user = userService.getUserByUsername(principal.toString());
		if(UtilFunctions.noEsNulo(user.getOrganizationId())) {
			productAux.setOrganization(new OrganizationBean());
			productAux.getOrganization().setId(user.getOrganizationId());			
		}
		if(file.getBytes().length > 0) {
			productAux.setImage(file.getBytes());
		}
		product = productService.saveProduct(productAux);
		response.setData(product);
		return response;
	}
	
	@RequestMapping(value = "/gi/{productId}", method = RequestMethod.POST)
	public GenericResponse<byte[]> getImage(@PathVariable("productId") Integer productId) {
		logger.info("ProductController.getImage()");
		GenericResponse<byte[]> response = new GenericResponse<>();
		ProductBean productBean = new ProductBean();
		productBean = productService.getProductById(productId);
		byte[] imageData = productBean.getImage();
		response.setData(imageData);
		return response;
	}
	
	@RequestMapping(value = "/gpbnad", method = RequestMethod.POST)
	public GenericResponse<List<Map<String, Object>>> getProductByNameAndDates(@RequestBody GenericRequest<ProductBean> request) {
		logger.info("ProductController.getProductByNameAndDates()");
		GenericResponse<List<Map<String, Object>>> response = new GenericResponse<>();
		List<Map<String, Object>> productList= productService.getProductByNameAndDates(request.getData());
		response.setData(productList);
		return response;
	}
	
	@RequestMapping(value = "/dp", method = RequestMethod.POST)
	public GenericResponse<ProductBean> deleteProduct(@RequestBody GenericRequest<ProductBean> request) {
		logger.info("ProductController.deleteProduct()");
		GenericResponse<ProductBean> response = new GenericResponse<>();
		productService.deleteProduct(request.getData().getId());
		return response;
	}
	
	@RequestMapping(value = "/gapd", method = RequestMethod.POST)
	public GenericResponse<ProductBean> getAllProductsDisabled(@RequestBody GenericRequest<ProductBean> request) {
		logger.info("ProductController.getAllProductsDisabled()");
		GenericResponse<ProductBean> response = new GenericResponse<>();
		List<ProductBean> productList = productService.getProductsDisabled();
		response.setDatalist(productList);
		return response;
	}
	
	@RequestMapping(value = "/uep", method = RequestMethod.POST)
	public GenericResponse<ProductBean> updateStatusProduct(@RequestBody GenericRequest<ProductBean> request) {
		logger.info("ProductController.updateStatusProduct()");
		GenericResponse<ProductBean> response = new GenericResponse<>();
		productService.updateStatus(request.getData().getId());
		return response;
	}
	
	@RequestMapping(value = "/grpe", method = RequestMethod.POST)
	public void getReportProductsExcel(@RequestBody GenericRequest<ProductBean> request, HttpServletResponse response) throws Exception {
		logger.info("ProductController.getReportProductsExcel()");
		XSSFWorkbook workbook = productService.generateReportExcel();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		workbook.write(bos);
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		byte[] bytes = bos.toByteArray();
		final OutputStream outStream = response.getOutputStream();
		bos.close();
		outStream.write(bytes);
		outStream.flush();
		outStream.close();
	}
	
	@RequestMapping(value = "/sf", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
	public GenericResponse saveFile(final HttpServletRequest requestHttp) throws IOException, ServletException {
		logger.info("ProductController.saveFile()");
		GenericResponse response = new GenericResponse<>();
		String pathFile = "C:\\Users\\ALEX\\Desktop\\test";
		String customFilePath = pathFile;
		Collection<Part> partFromFile = requestHttp.getParts();
		if(partFromFile.size() > 0) {
			Part part = partFromFile.iterator().next();
			InputStream inputFile = part.getInputStream();
			byte[] bytes = IOUtils.toByteArray(inputFile);
			customFilePath = pathFile + File.separator + part.getSubmittedFileName();
			File file = new File(customFilePath);
			if(!file.exists()) {
				//file.getParentFile().mkdirs();
				file.createNewFile();
			}
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(bytes);
			bos.flush();
			bos.close();
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping(value = "/df", method = RequestMethod.POST)
	public void downloadFile(@RequestBody GenericRequest<ProductBean> request, final HttpServletResponse response) throws IOException {
		logger.info("ProductController.downloadFile()");
		String pathFile = "c:/Users/ALEX/Desktop/test/Pantallazos de lo que se explicó durante la entrevista.pdf";
		if(UtilFunctions.noEsNulo(pathFile)) {
			File file = new File(pathFile);
			if(file.exists()) {
				Path path = Paths.get(pathFile);
				byte[] bytes = (byte[])Files.readAllBytes(path);
				response.setContentType("application/pdf");
				OutputStream os = response.getOutputStream();
				os.write(bytes);
				os.flush();
				os.close();
			}
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/dz", method = RequestMethod.POST)
	public void downloadZip(@RequestBody GenericRequest<ProductBean> request, final HttpServletResponse response) throws IOException {
		logger.info("ProductController.downloadFile()");
		String pathFile = "c:/Users/ALEX/Desktop/test/test.zip";
		if(UtilFunctions.noEsNulo(pathFile)) {
			File file = new File(pathFile);
			if(file.exists()) {
				response.setContentType("application/zip");
				//response.setHeader("Content-disposition", "attachment; filename=archivoTest.zip");
				Path path = Paths.get(pathFile);
				//ZipOutputStream zipOS = new ZipOutputStream(response.getOutputStream());
				//FileInputStream fis = new FileInputStream(file);
				//zipOS.putNextEntry(new ZipEntry(file.getName()));
				//byte[] bytes = new byte[1024];
				OutputStream os = response.getOutputStream();
				byte[] bytes = Files.readAllBytes(path);
				int length;
				//zipOS.write(bytes);
				//while((length = fis.read()) >= 0) {
					//zipOS.write(bytes, 0, length);
				//}
				//zipOS.flush();
				//zipOS.closeEntry();
				//zipOS.close();
				//fis.close();
				os.write(bytes);
				os.flush();
				os.close();
			}
		}
	}
	
	
}
