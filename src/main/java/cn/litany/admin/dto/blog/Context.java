package cn.litany.admin.dto.blog;

import org.apache.commons.lang.StringUtils;

/**
 * @author Litany
 */
public class Context {

    private String note;

    private String content;

    private static final String MARK = "> ";

    public String getNote() {
        if (StringUtils.isBlank(note)) {
            return "";
        }
        if (!note.startsWith(MARK)) {
            note = MARK + this.note;
        }
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getContent() {
        if (StringUtils.isBlank(content)) {
            return "# 简述";
        }
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
