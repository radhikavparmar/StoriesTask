package test.rvp.happtask;

public class CoverModel {

    String coverPath;
    String covertitle;

    public CoverModel(String coverPath, String covertitle) {
        this.coverPath = coverPath;
        this.covertitle = covertitle;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getCovertitle() {
        return covertitle;
    }

    public void setCovertitle(String covertitle) {
        this.covertitle = covertitle;
    }
}