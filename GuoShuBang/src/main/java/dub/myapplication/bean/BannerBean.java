package dub.myapplication.bean;

public class BannerBean {

    private String id; //主键ID
    private String image; //图片路径
    private int type; //0：不跳转，1：跳转到热卖，2：跳转到商品详情
    private String gid; //商品ID
    private int c_time; //创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public int getC_time() {
        return c_time;
    }

    public void setC_time(int c_time) {
        this.c_time = c_time;
    }
}
