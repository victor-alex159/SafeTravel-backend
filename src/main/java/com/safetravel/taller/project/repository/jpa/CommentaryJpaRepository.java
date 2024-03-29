package com.safetravel.taller.project.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.safetravel.taller.project.entity.CommentaryEntity;

public interface CommentaryJpaRepository extends PagingAndSortingRepository<CommentaryEntity, Integer>, CommentaryJpaRepositoryCustom {

	@Query("SELECT c FROM CommentaryEntity c inner join c.product pr WHERE pr.id =:productId AND c.deleted=false AND pr.deleted=false order by 1 desc")
	public List<CommentaryEntity> getCommentaryByProductId(@Param("productId") Integer productId);
	
}
