package cn.litany.admin.dto.blog;

import java.util.Objects;

/**
 * @author Litany
 */
public class Blog {
    private String title;
    private Top top;
    private Context context;
    private Boolean isUpload;

    public Boolean getUpload() {
        return isUpload;
    }

    public void setUpload(Boolean upload) {
        isUpload = upload;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Top getTop() {
        return top;
    }

    public void setTop(Top top) {
        this.top = top;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getBlog(){
        if (Objects.isNull(this.top)){
            this.top=new Top();
        }
        if (Objects.isNull(this.context)){
            this.context=new Context();
        }

        return this.top.toString() +
                "\n" +
                "\n" +
                this.context.getNote() +
                "\n" +
                "\n" +
                "\n" +
                this.context.getContent();
    }

    @Override
    public String toString() {
        return "Blog{" +
                "title='" + title + '\'' +
                ", top=" + top +
                ", context=" + context +
                ", isUpload=" + isUpload +
                '}';
    }
}
