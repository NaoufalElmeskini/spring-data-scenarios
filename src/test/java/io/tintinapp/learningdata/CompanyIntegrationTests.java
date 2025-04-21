package io.tintinapp.learningdata;

import io.tintinapp.learningdata.dto.CompanyDto;
import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompanyIntegrationTests {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private EntityManager entityManager;

	@Test
	void givenCompaniesWithAssociationEmployees_whenTheFetchTypeIsLazy_thenTheNPlus1QueryProblemIsPresent(){
		Session session = entityManager.unwrap(Session.class);
		Statistics statistics = session.getSessionFactory().getStatistics();
		statistics.clear();

		List<CompanyDto> companyDtoList = companyService.list();

		Assertions.assertThat(companyDtoList.size()).isEqualTo(10);
		Assertions.assertThat(statistics.getQueryExecutionCount()).isEqualTo(1);
		// !!! n+1 query problem !!!
		Assertions.assertThat(statistics.getCollectionFetchCount()).isEqualTo(companyDtoList.size());
	}
}
