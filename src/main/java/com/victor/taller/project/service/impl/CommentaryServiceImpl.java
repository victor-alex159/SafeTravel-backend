package com.victor.taller.project.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.taller.project.entity.CommentaryEntity;
import com.victor.taller.project.entity.ProductEntity;
import com.victor.taller.project.repository.jpa.CommentaryJpaRepository;
import com.victor.taller.project.service.CommentaryService;
import com.victor.taller.project.soa.bean.CommentaryBean;
import com.victor.taller.project.soa.bean.ProductBean;

@Service
public class CommentaryServiceImpl implements CommentaryService {

	@Autowired
	private CommentaryJpaRepository commentaryRepository;
	
	@Override
	public CommentaryBean save(CommentaryBean commentaryBean) {
		CommentaryEntity commentaryEntity = new CommentaryEntity();
		BeanUtils.copyProperties(commentaryBean, commentaryEntity);
		if(commentaryBean.getProduct() != null) {
			commentaryEntity.setProduct(new ProductEntity());
			commentaryEntity.getProduct().setId(commentaryBean.getProduct().getId());
		}
		
		commentaryEntity = commentaryRepository.save(commentaryEntity);
		commentaryBean.setId(commentaryEntity.getId());
		
		return commentaryBean;
	}

	@Override
	public List<CommentaryBean> getCommentaryByProductId(CommentaryBean commentary) {
		List<CommentaryBean> result = new ArrayList<>();
		List<CommentaryEntity> list = commentaryRepository.getCommentaryByProductId(commentary.getProduct().getId());
		if(list != null) {
			list.forEach(commentaryEntity -> {
				CommentaryBean commentaryBean = new CommentaryBean();
				BeanUtils.copyProperties(commentaryEntity, commentaryBean);
				if(commentaryEntity.getProduct() != null) {
					commentaryBean.setProduct(new ProductBean());
					commentaryBean.getProduct().setId(commentaryEntity.getProduct().getId());
				}
				result.add(commentaryBean);
			});
			return result;
		}
		return null;
	}

}
