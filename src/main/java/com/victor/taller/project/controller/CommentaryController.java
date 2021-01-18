package com.victor.taller.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.victor.taller.project.service.CommentaryService;
import com.victor.taller.project.soa.bean.CommentaryBean;
import com.victor.taller.project.soa.request.GenericRequest;
import com.victor.taller.project.soa.response.GenericResponse;

@RestController
@RequestMapping(value = "/cmc")
public class CommentaryController {

	private static final Logger logger = LogManager.getLogger(CommentaryController.class);

	@Autowired
	private CommentaryService commentaryService;
	
	@RequestMapping(value = "/sc", method = RequestMethod.POST)
	public GenericResponse<CommentaryBean> saveCommentary(@RequestBody GenericRequest<CommentaryBean> request) {
		logger.info("CommentaryController.saveCommentary()");
		GenericResponse<CommentaryBean> response = new GenericResponse<>();
		CommentaryBean commentary = new CommentaryBean();
		commentary = commentaryService.save(request.getData());
		response.setData(commentary);		
		return response;
		
	}
	
	@RequestMapping(value = "/gcbpi", method = RequestMethod.POST)
	public GenericResponse<CommentaryBean> getCommentaryByProductId(@RequestBody GenericRequest<CommentaryBean> request) {
		logger.info("CommentaryController.getCommentaryByProductId()");
		GenericResponse<CommentaryBean> response = new GenericResponse<>();
		List<CommentaryBean> listCommentaryBean = new ArrayList<CommentaryBean>();
		listCommentaryBean = commentaryService.getCommentaryByProductId(request.getData());
		response.setDatalist(listCommentaryBean);
		return response;
	}
}
