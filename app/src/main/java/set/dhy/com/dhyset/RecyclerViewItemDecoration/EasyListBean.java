package set.dhy.com.dhyset.RecyclerViewItemDecoration;

/**
 * author dhy
 * Created by test on 2018/4/26.
 */

public class EasyListBean {
    private int itemType;  //类型
    private int parent;  //第几类
    private String content; //具体内容

    public EasyListBean(int itemType, int parent, String content) {
        this.itemType = itemType;
        this.parent = parent;
        this.content = content;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
