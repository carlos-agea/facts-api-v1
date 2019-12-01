package carlos.consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FactsClient implements RestClient {

	private final int THREAD_POOL_SIZE = 8;
	private final int MESSAGES_TO_PULL_SIZE = 1000;
	private final String FACTS_URL = "https://uselessfacts.jsph.pl/random.json?language=en";
	private Map<String, Fact> facts = new HashMap<>();
	private AtomicBoolean initialized = new AtomicBoolean(false);

	@Autowired
	RestTemplate restTemplate;

	@Override
	public int getTotalMessages() {
		return MESSAGES_TO_PULL_SIZE;
	}

	@Override
	public Map<String, Fact> getFacts() {
		if (initialized.get())
			return facts;

		return null;
	}

	@PostConstruct
	public void pullFacts() throws Exception {
		PullFactsThread thread = new PullFactsThread();
		thread.start();
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	public class GetFactCallable implements Callable<Fact> {
		@Override
		public Fact call() throws Exception {
			return restTemplate.getForObject(FACTS_URL, Fact.class);
		}
	}

	public class PullFactsThread extends Thread {
		public void run() {
			try {
				ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
				List<Future<Fact>> list = new ArrayList<Future<Fact>>();
				Callable<Fact> callable = new GetFactCallable();

				for (int i = 0; i < MESSAGES_TO_PULL_SIZE; i++) {
					list.add(executor.submit(callable));
				}

				for (Future<Fact> futureFact : list) {
					try {
						Fact fact = futureFact.get();
						facts.put(fact.getId(), fact);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				initialized.compareAndSet(false, true);
				executor.shutdown();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
