package cn.litany.admin.dto.blog;

import org.apache.commons.lang.StringUtils;

import static cn.litany.admin.constant.BlogConstant.SPLIT1;
import static cn.litany.admin.constant.BlogConstant.SPLIT2;

/**
 * @author Litany
 */
public class Top {
    private String title;
    private String subtitle;
    private String date;
    private String author;
    private String headerImg;
    private String tags;


    private String[] getAllTag() {
        String[] tags;
        String tag = getTags();
        if (StringUtils.isBlank(tag)) {
            return null;
        }
        if (tag.contains(SPLIT1)) {
            tags = tag.split(SPLIT1);
        } else if (tag.contains(SPLIT2)) {
            tags = tag.split(SPLIT2);
        } else {
            tags = new String[]{tag};
        }
        return tags;
    }

    @Override
    public String toString() {
        String[] allTag = getAllTag();
        String tags = "";
        if (allTag != null) {
            StringBuilder sb = new StringBuilder("\n");
            for (String s : allTag) {
                sb.append("    - ").append(s).append("\n");
            }
            tags = sb.toString();
        }
        return "---\n" +
                "layout:     post\n" +
                "title:      " + title + "\n" +
                "subtitle:   " + subtitle + "\n" +
                "date:       " + date + "\n" +
                "author:     " + author + "\n" +
                "header-img: " + headerImg + "\n" +
                "catalog: true\n" +
                "tags:" + tags + "\n" +
                "---";
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }


    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
