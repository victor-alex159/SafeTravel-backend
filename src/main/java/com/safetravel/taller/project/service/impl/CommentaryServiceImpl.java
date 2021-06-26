package com.safetravel.taller.project.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetravel.taller.project.entity.CommentaryEntity;
import com.safetravel.taller.project.entity.ProductEntity;
import com.safetravel.taller.project.repository.jpa.CommentaryJpaRepository;
import com.safetravel.taller.project.service.CommentaryService;
import com.safetravel.taller.project.service.UserService;
import com.safetravel.taller.project.soa.bean.CommentaryBean;
import com.safetravel.taller.project.soa.bean.ProductBean;
import com.safetravel.taller.project.soa.bean.UserBean;

@Service
public class CommentaryServiceImpl implements CommentaryService {

	@Autowired
	private CommentaryJpaRepository commentaryRepository;
	
	@Autowired
	private UserService userService;
	
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
				if(commentaryEntity.getUserCreateId() != null) {
					UserBean user = userService.getUserById(commentaryEntity.getUserCreateId());
					commentaryBean.setUsername(user.getUsername());
				}
				result.add(commentaryBean);
			});
			return result;
		}
		return null;
	}

}
