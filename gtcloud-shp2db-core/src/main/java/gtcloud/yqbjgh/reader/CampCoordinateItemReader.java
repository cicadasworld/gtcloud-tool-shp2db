package gtcloud.yqbjgh.reader;

import java.util.Iterator;
import java.util.List;

import gtcloud.yqbjgh.domain.CampCoordinate;
import org.springframework.batch.item.ItemReader;

public class CampCoordinateItemReader implements ItemReader<CampCoordinate> {

    private final Iterator<CampCoordinate> data;

    public CampCoordinateItemReader(List<CampCoordinate> data) {
        this.data = data.iterator();
    }

    @Override
    public CampCoordinate read() throws Exception {
        if (this.data.hasNext()) {
            return this.data.next();
        } else {
            return null;
        }
    }

}
