package gtcloud.reader;

import java.util.Iterator;
import java.util.List;

import org.springframework.batch.item.ItemReader;

import gtcloud.domain.CampCoordinate;

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
