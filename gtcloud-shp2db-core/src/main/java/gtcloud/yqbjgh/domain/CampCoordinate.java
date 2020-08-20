package gtcloud.yqbjgh.domain;

public class CampCoordinate {

	private String jlbm;

	private String fid;

	private String coordinateNum;

	private String coorX;

	private String coorY;

	private String centerX;

	private String centerY;

	public String getJlbm() {
		return jlbm;
	}

	public void setJlbm(String jlbm) {
		this.jlbm = jlbm;
	}

	public String getCoordinateNum() {
		return coordinateNum;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public void setCoordinateNum(String coordinateNum) {
		this.coordinateNum = coordinateNum;
	}

	public String getCoorX() {
		return coorX;
	}

	public void setCoorX(String coorX) {
		this.coorX = coorX;
	}

	public String getCoorY() {
		return coorY;
	}

	public void setCoorY(String coorY) {
		this.coorY = coorY;
	}

    public String getCenterX() {
        return centerX;
    }

    public void setCenterX(String centerX) {
        this.centerX = centerX;
    }

    public String getCenterY() {
        return centerY;
    }

    public void setCenterY(String centerY) {
        this.centerY = centerY;
    }

    @Override
    public String toString() {
        return "CampCoordinate{" +
                "jlbm='" + jlbm + '\'' +
                ", fid='" + fid + '\'' +
                ", coordinateNum='" + coordinateNum + '\'' +
                ", coorX='" + coorX + '\'' +
                ", coorY='" + coorY + '\'' +
                ", centerX='" + centerX + '\'' +
                ", centerY='" + centerY + '\'' +
                '}';
    }
}
