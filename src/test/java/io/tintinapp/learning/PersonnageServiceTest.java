package io.tintinapp.learning;

import io.tintinapp.learning.domain.infra.AccessoireRepo;
import io.tintinapp.learning.domain.infra.PersonnageRepository;
import io.tintinapp.learning.domain.infra.entity.Accessoire;
import io.tintinapp.learning.domain.infra.entity.Personnage;

import org.hibernate.query.results.Builders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class PersonnageServiceTest {

	@Autowired
	private PersonnageService personnageService;

	@Autowired
	private PersonnageRepository personnageRepository;

	@Autowired
	private AccessoireRepo accessoireRepository;

	@Test
	@DisplayName("step 1 : relation bi-directionnelle Personnage-accessoire")
	public void verifierLAssociationBiDirectionnelle() {
		// given

		Personnage tintin = MesBuilders.unPersonnage(
			"Tintin", 
			MesBuilders.unAccessoire("besace"));
		personnageRepository.save(tintin);
		// when
		List<Accessoire> allAccessoires = accessoireRepository.findAll();
		// then
		assertThat(allAccessoires).hasSize(1);
		assertEquals(tintin.getNom(), allAccessoires.getFirst().getProprio().getNom());
	}


	@Test
	@DisplayName("step 2 : constater probleme N+1")
	public void constaterProblemeNPlus1() {
		// given : j'ai 2 personnage ayant chacun 1 accessoires		
		Personnage tintin = MesBuilders.unPersonnage(
			"Tintin", 
			MesBuilders.unAccessoire("besace"));
		Personnage milou = MesBuilders.unPersonnage(
			"Milou", 
			MesBuilders.unAccessoire("musoliere"));
		personnageRepository.save(tintin);
		personnageRepository.save(milou);

		tintin.ajouterAccessoire(MesBuilders.unAccessoire("lunettes"));
		// when : je recupere tous les personnages
		List<Personnage> allPersonnages = personnageService.getAll();

		// then : je constate 1+2 (=3) requetes sql  
		for (Personnage p : allPersonnages) {
			for (Accessoire a : p.getAccessoires()) {
				System.out.println(a.getNom());  // Forcer l'accès complet aux accessoires
			}
		}
		
		assertThat(allPersonnages).hasSize(2);
	}
}