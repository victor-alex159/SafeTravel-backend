package com.victor.taller.project.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.victor.taller.project.entity.CommentaryEntity;

public interface CommentaryJpaRepository extends PagingAndSortingRepository<CommentaryEntity, Integer> {

	@Query("SELECT c FROM CommentaryEntity c WHERE c.product.id =:productId AND c.deleted=false order by 1 desc")
	public List<CommentaryEntity> getCommentaryByProductId(@Param("productId") Integer productId);
	
}
