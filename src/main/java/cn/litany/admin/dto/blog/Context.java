package cn.litany.admin.dto.blog;

import org.apache.commons.lang.StringUtils;

import static cn.litany.admin.constant.BlogConstant.*;

/**
 * @author Litany
 */
public class Context {

    private String note;

    private String content;


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
            return DEFAULT_CONTENT;
        }
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
