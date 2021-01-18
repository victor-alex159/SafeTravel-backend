package com.victor.taller.project.service;

import java.util.List;

import com.victor.taller.project.soa.bean.CommentaryBean;

public interface CommentaryService {
	
	abstract CommentaryBean save(CommentaryBean commentaryEntity);
	abstract List<CommentaryBean> getCommentaryByProductId(CommentaryBean commentaryBean);

}
