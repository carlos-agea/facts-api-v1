package carlos.consumer;

import java.util.Map;

public interface RestClient {
	public Map<String, Fact> getFacts();

	int getTotalMessages();
}
