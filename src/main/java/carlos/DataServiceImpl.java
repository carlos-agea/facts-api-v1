package carlos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class DataServiceImpl implements DataService {

	private final String ISO6391_FILEPATH = "src/main/resources/iso-639-1.csv";
	private Map<String, String> isocodes;
	
	@Override
	public Map<String, String> getIsocodes() {
		return isocodes;
	}
	
	@PostConstruct
	private void readIsocodes() {
		Stream<String> lines = null;
		try {
			lines = Files.lines(Paths.get(ISO6391_FILEPATH));
		} catch (IOException e) {
			e.printStackTrace();
		}

		isocodes = lines.map(s -> s.split(",")).collect(Collectors.toMap(a -> a[0], a -> a[1]));
	}
}
