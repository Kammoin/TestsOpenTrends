package com.opentrends.ci.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.opentrends.ci.bean.Build;

/**
 * Repository that implements CRUD on Build table
 * 
 * @author iorodriguez
 *
 */
public interface BuildRepository extends JpaRepository<Build, Long> {

}
