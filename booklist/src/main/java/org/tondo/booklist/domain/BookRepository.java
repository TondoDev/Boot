package org.tondo.booklist.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author TondoDev
 * 
 * This interface will be implemented automatically at runtinme during application start.
 * Responsible for this should be SpringData.
 *
 */
public interface BookRepository extends JpaRepository<Book, Long>{

	List<Book> findByReader(String reader);
	
}
