package gtcloud.config;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gtcloud.domain.CampCoordinate;
import gtcloud.helper.ShpToolHelper;
import gtcloud.reader.CampCoordinateItemReader;
import gtcloud.writer.CampCoordinateItemWriter;

@Configuration
public class JobConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Value("${shp.file.path}")
	private String shpFilePath;

	@Bean
	public CampCoordinateItemReader campCoordinateItemReader() throws Exception {
		List<CampCoordinate> data = ShpToolHelper.readShpByPath(shpFilePath);
		return new CampCoordinateItemReader(data);
	}

	@Bean
	public CampCoordinateItemWriter campCoordinateItemWriter() {
		return new CampCoordinateItemWriter();
	}

	@Bean
	public Step step() throws Exception {
		return stepBuilderFactory.get("step")
				.<CampCoordinate, CampCoordinate>chunk(10)
				.reader(campCoordinateItemReader())
				.writer(campCoordinateItemWriter())
				.build();
	}

	@Bean
	public Job job() throws Exception {
		return jobBuilderFactory.get("job")
				.start(step())
				.build();
	}

}
