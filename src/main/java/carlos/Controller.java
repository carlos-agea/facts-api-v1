package carlos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import carlos.consumer.Fact;
import carlos.consumer.RestClient;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class Controller {

	private enum status {
		COMPLETED, PENDING
	};

	@Autowired
	RestClient restClient;

	@Autowired
	DataService dataService;
	
	@RequestMapping(value = "/status", method = RequestMethod.GET)
	public StatusDTO status() {
		Map<String, Fact> facts = restClient.getFacts();

		if (facts == null) {
			return new StatusDTO(status.PENDING.toString(), new FactsStatusDTO(0, 0));
		} else {
			return new StatusDTO(status.COMPLETED.toString(),
					new FactsStatusDTO(restClient.getTotalMessages(), facts.size()));
		}
	}

	@RequestMapping(value = "/facts", method = RequestMethod.GET)
	public List<String> factsIds() {
		Map<String, Fact> facts = restClient.getFacts();
		if (facts == null) {
			return new ArrayList<>();
		}

		return facts.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
	}

	@RequestMapping(value = "/facts/{factId}", method = RequestMethod.GET)
	public FactDTO factsId(@PathVariable("factId") String factId, @RequestParam(value="lang", defaultValue="en") String language) {
		if(!dataService.getIsocodes().containsKey(language)) {
			return new FactDTO();
		}
		
		Map<String, Fact> facts = restClient.getFacts();
		if (facts == null) {
			return new FactDTO();
		}

		Optional<FactDTO> factDTO =  facts.entrySet().stream()
				.filter(fact -> fact.getKey().equals(factId) && fact.getValue().getLanguage().equals(language))
				.map(fact -> fact.getValue().asDTO()).findFirst();
		
		if(factDTO.isPresent()) return factDTO.get();
		return new FactDTO();
		
	}
}
