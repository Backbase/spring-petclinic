package org.springframework.samples.petclinic.system;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "fulltextsearch", name = "enabled")
public class StartupEvent implements ApplicationListener<ApplicationReadyEvent> {

	private final IndexingService service;

	public StartupEvent(IndexingService service) {
		this.service = service;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		try {
			service.initiateIndexing();
		}
		catch (InterruptedException e) {
			// log.error("Failed to reindex entities ",e);
		}
	}

}
