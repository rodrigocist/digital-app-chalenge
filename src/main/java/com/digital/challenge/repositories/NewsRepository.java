package com.digital.challenge.repositories;

import com.digital.challenge.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface NewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {

    @Transactional
    @Modifying
    @Query("update News n set n.visible = false where n.storyId = :id")
    void  updateVisibiltyById(@Param("id") Long id);

}
