package gtcloud.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;

import gtcloud.domain.CampCoordinate;
import gtcloud.helper.HttpClientHelper;

public class CampCoordinateItemWriter implements ItemWriter<CampCoordinate> {

    @Autowired
    private ObjectMapper mapper;

    @Value("${server.endpoint}")
    private String endpoint;

	@Override
	public void write(List<? extends CampCoordinate> items) throws Exception {
        for (CampCoordinate item : items) {
            String json = mapper.writeValueAsString(item);
            HttpClientHelper.doHttpPostWithJsonBody(endpoint, json);
        }
	}

}
