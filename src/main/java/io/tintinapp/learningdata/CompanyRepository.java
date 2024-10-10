package io.tintinapp.learningdata;

import io.tintinapp.learningdata.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {

//    @Query(
//            value = "from Company c join fetch c.employees as e",
//            countQuery = "select count(e) from Company e"
//    )
//    Page<Company> findAllWithEmployees(Pageable pageable);

}
