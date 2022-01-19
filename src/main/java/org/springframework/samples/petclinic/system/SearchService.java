package org.springframework.samples.petclinic.system;

import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class SearchService {

	private final EntityManager entityManager;

	public SearchService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<Owner> search(String query) {
		SearchSession searchSession = Search.session(entityManager);

//		SearchResult<Owner> result = searchSession.search(Owner.class)
//				.where(f -> f.match().fields("firstName", "lastName", "pets.name").matching(query)).fetch(20);
//
//		long totalHitCount = result.total().hitCount();
//		List<Owner> hits = result.hits();

		List<Owner> hits2 = searchSession.search(Owner.class).where(
				f -> query == null || query.trim().isEmpty() ?
					f.matchAll() : f.match().fields("firstName", "lastName", "pets.name").matching(query).fuzzy())
				.fetchHits(20);

		return hits2;
	}

}
