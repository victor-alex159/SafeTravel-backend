package com.victor.taller.project.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.victor.taller.project.entity.CommentaryEntity;

public interface CommentaryJpaRepository extends PagingAndSortingRepository<CommentaryEntity, Integer> {

}
