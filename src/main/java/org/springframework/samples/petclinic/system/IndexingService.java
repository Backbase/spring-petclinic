package org.springframework.samples.petclinic.system;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(prefix = "fulltextsearch", name = "enabled")
public class IndexingService {

	private final EntityManager entityManager;

	public IndexingService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	public void initiateIndexing() throws InterruptedException {
		// log.info("Initiating indexing...");
		SearchSession searchSession = Search.session(entityManager);
		MassIndexer indexer = searchSession.massIndexer(Owner.class).threadsToLoadObjects(7);
		indexer.startAndWait();
		// log.info("All entities indexed");

	}

}
