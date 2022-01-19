package org.springframework.samples.petclinic.system;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ConditionalOnProperty(prefix = "fulltextsearch", name = "enabled")
class IndexController {

	private final IndexingService indexingService;

	private final SearchService searchService;

	IndexController(IndexingService indexingService, SearchService searchService) {
		this.indexingService = indexingService;
		this.searchService = searchService;
	}

	@PostMapping("/reindex")
	public void reindex() throws InterruptedException {
		indexingService.initiateIndexing();
	}

}
