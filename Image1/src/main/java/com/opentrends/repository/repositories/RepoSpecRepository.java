package com.opentrends.repository.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.opentrends.repository.bean.RepositorySpec;

/**
 * Repository that implements CRUD on RepoSpec table
 * 
 * @author iorodriguez
 *
 */
public interface RepoSpecRepository extends JpaRepository<RepositorySpec, Long> {

}
