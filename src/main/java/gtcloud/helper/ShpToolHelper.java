package gtcloud.helper;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.*;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

import gtcloud.domain.CampCoordinate;

public class ShpToolHelper {

    public final static String wkt = "PROJCS[\"CGCS2000_3_Degree_GK_Zone_39\",GEOGCS[\"GCS_China_Geodetic_Coordinate_System_2000\",DATUM[\"D_China_2000\",SPHEROID[\"CGCS2000\",6378137,298.257222101]],PRIMEM[\"Greenwich\",0],UNIT[\"Degree\",0.017453292519943295]],PROJECTION[\"Gauss_Kruger\"],PARAMETER[\"Latitude_Of_Origin\",0],PARAMETER[\"Central_Meridian\",117],PARAMETER[\"Scale_Factor\",1],PARAMETER[\"False_Easting\",39500000],PARAMETER[\"False_Northing\",0],UNIT[\"Meter\",1],AUTHORITY[\"EPSG\",4527]]";

    public static List<CampCoordinate> readShpByPath(String filePath) throws Exception {
        // ��Shp�ļ���ȡ
        ShapefileDataStore shpDataStore = new ShapefileDataStore(new File(filePath).toURI().toURL());

        // �����ַ�����
        shpDataStore.setCharset(Charset.forName("GBK"));


        SimpleFeatureSource featureSource = shpDataStore.getFeatureSource();
        SimpleFeatureIterator featureIter = featureSource.getFeatures().features();

        List<CampCoordinate> campCoordinates = new ArrayList<>();
        while (featureIter.hasNext()) {
            SimpleFeature feature = featureIter.next();
            String featureId = feature.getID();
            String fid = featureId.substring(featureId.lastIndexOf(".") + 1);
            MultiPolygon m = (MultiPolygon) feature.getProperty("the_geom").getValue();
            Point centroid = m.getCentroid();
            Coordinate centroidCoordinate = new Coordinate(centroid.getX(), centroid.getY());
            Coordinate targetCentroidCoordiante = convert(centroidCoordinate);
            Coordinate[] sources = m.getCoordinates();
            List<Coordinate> targets = new ArrayList<>();
            for (Coordinate source : sources) {
                Coordinate target = convert(source);
                targets.add(target);
            }
            for (int i = 0; i < targets.size(); i++) {
                Coordinate coordinate = targets.get(i);
                CampCoordinate campCoordinate = new CampCoordinate();
                campCoordinate.setJlbm(UUID.randomUUID().toString().replace("-", ""));
                campCoordinate.setFid(fid);
                campCoordinate.setCoordinateNum(String.valueOf(i));
                campCoordinate.setCoorX(String.valueOf(coordinate.getY()));  // ����:y γ��:x
                campCoordinate.setCoorY(String.valueOf(coordinate.getX()));
                campCoordinate.setCenterX(String.valueOf(targetCentroidCoordiante.getY()));
                campCoordinate.setCenterY(String.valueOf(targetCentroidCoordiante.getX()));
                campCoordinates.add(campCoordinate);
            }
        }
        return campCoordinates;
    }

    public static Coordinate convert(Coordinate source) throws Exception {
        CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:4490");
        CoordinateReferenceSystem sourceCRS = CRS.parseWKT(wkt);
        MathTransform transform = CRS.findMathTransform(sourceCRS, targetCRS);
        Coordinate target = JTS.transform(source, source, transform);
        return target;
    }

    public static void test() {
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);
        Coordinate coord = new Coordinate(1, 1);
        Point point = geometryFactory.createPoint(coord);

        Coordinate[] coordinates = new Coordinate[] {
                new Coordinate(1, 1),
                new Coordinate(2, 2)
        };
        Polygon polygon = geometryFactory.createPolygon(coordinates);
        Point centroid = polygon.getCentroid();
    }
}
