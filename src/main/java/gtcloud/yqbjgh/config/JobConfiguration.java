package gtcloud.yqbjgh.config;

import gtcloud.yqbjgh.domain.CampCoordinate;
import gtcloud.yqbjgh.helper.ShpFileScanner;
import gtcloud.yqbjgh.helper.ShpToolHelper;
import gtcloud.yqbjgh.reader.CampCoordinateItemReader;
import gtcloud.yqbjgh.writer.CampCoordinateItemWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Value("${shp.file.path}")
	private String shpFilePath;

	@Value("${shp.fid.prefix}")
	private String prefix;

	@Bean
	public CampCoordinateItemReader campCoordinateItemReader() throws Exception {
		File currentDir = new File(shpFilePath);
		List<File> result = new ArrayList<>();
		ShpFileScanner.scanShp(currentDir, result);
		List<CampCoordinate> data = new ArrayList<>();
		for (File file : result) {
			List<CampCoordinate> coordinates = ShpToolHelper.readShpByPath(file.getAbsolutePath());
			for (CampCoordinate coordinate : coordinates) {
				CampCoordinate newCoordinate = customFid(coordinate);
				data.add(newCoordinate);
			}
		}
		return new CampCoordinateItemReader(data);
	}

	private CampCoordinate customFid(CampCoordinate oldCoordinate) {
		String fid = oldCoordinate.getFid();
		String customFid = prefix + fid;
		CampCoordinate newCoordinate = new CampCoordinate();
		newCoordinate.setJlbm(oldCoordinate.getJlbm());
		newCoordinate.setFid(customFid);
		newCoordinate.setCoordinateNum(oldCoordinate.getCoordinateNum());
		newCoordinate.setCoorX(oldCoordinate.getCoorX());
		newCoordinate.setCoorY(oldCoordinate.getCoorY());
		newCoordinate.setCenterX(oldCoordinate.getCenterX());
		newCoordinate.setCenterY(oldCoordinate.getCenterY());
		return newCoordinate;
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
