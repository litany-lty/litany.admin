package cn.litany.admin.util;

import cn.litany.admin.dto.blog.Blog;
import cn.litany.admin.dto.blog.Context;
import cn.litany.admin.dto.blog.Top;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.litany.admin.constant.BlogConstant.MARKDOWN_TYPE;
import static cn.litany.admin.constant.BlogConstant.TOP;

/**
 * @author Litany
 */
public class BlogUtil {
    private static Pattern p = Pattern.compile("(\\d{4})-(\\d{1,2})-(\\d{1,2})");


    public static List<Blog> getNewBlogListByNum(Map<String, List<Blog>> groupDateBlogList, int number) {
        AtomicInteger index = new AtomicInteger();
        Set<String> dateSet = groupDateBlogList.keySet();
        String[] newDateList = getNewDateList(dateSet.toArray(new String[0]));
        Set<Blog> newBlogList = new LinkedHashSet<>();
        for (String s : newDateList) {
            List<Blog> blogList = groupDateBlogList.get(s);
            ArrayList<String> dates = new ArrayList<>();
            blogList.forEach((blog) -> {
                dates.add(blog.getTitle().substring(0, 10));
            });
            for (String title : getNewDateList(dates.toArray(new String[0]))) {
                blogList.forEach((blog -> {
                    if (newBlogList.size() < number && blog.getTitle().substring(0, 10).equalsIgnoreCase(title)) {
                        blog.setIndex(index.getAndIncrement());
                        newBlogList.add(blog);
                    }
                }));
            }
        }
        return new ArrayList<>(newBlogList);


    }

    private static String[] getNewDateList(String[] dates) {
        String[] newDates = new String[dates.length];
        for (int i = 0; i < newDates.length; i++) {
            newDates[i] = dates[i];
            for (int z = i + 1; z < dates.length; z++) {
                if (newDates[i].compareTo(dates[z]) < 1) {
                    String temp = dates[z];
                    dates[z] = newDates[i];
                    newDates[i] = temp;
                }
            }
        }
        return newDates;

    }

    public static Map<String, List<Blog>> findInBlogListByDate(List<Blog> blogList) {
        Map<String, List<Blog>> blogDateMap = new HashMap<>();
        for (Blog blog : blogList) {
            String date = getDate(blog.getTitle());
            date = date.substring(0, date.lastIndexOf("-"));
            if (blogDateMap.get(date) == null) {
                List<Blog> blogDateList = new ArrayList<Blog>();
                blogDateList.add(blog);
                blogDateMap.put(date, blogDateList);
            } else {
                List<Blog> blogDateList = blogDateMap.get(date);
                blogDateList.add(blog);
                blogDateMap.put(date, blogDateList);
            }
        }
        return blogDateMap;
    }


    public static List<Blog> getBlogListByDirPath(String dirPath) {
        List<Blog> blogList = new ArrayList<Blog>();
        File[] blogFiles = getFilesByDirPath(dirPath);
        if (blogFiles != null && blogFiles.length > 0) {
            for (File blogFile : blogFiles) {
                Blog blog = parseBlogFile(blogFile);
                if (Objects.nonNull(blog)) {
                    blogList.add(blog);
                }
            }
        }
        return blogList;
    }

    public static File[] getFilesByDirPath(String dirPath) {
        File dir = new File(dirPath);
        if (dir.isDirectory()) {

            return dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.isFile() && pathname.getName().endsWith(MARKDOWN_TYPE);
                }
            });
        }
        return null;
    }


    public static Blog parseBlogFile(File blogFile) {
        if (!blogFile.exists() && blogFile.getName().endsWith(MARKDOWN_TYPE)) {
            return null;
        }
        try {
            Blog blog = new Blog();
            Context context = new Context();
            Top top = null;
            String s = FileUtils.readFileToString(blogFile, StandardCharsets.UTF_8);
            int i = s.indexOf(TOP, 2);
            int nodeIndex = s.indexOf("#");
            int topIndex = i + 3;
            if (i != -1) {
                String topString = s.substring(0, topIndex);
                top = getTop(topString);
            } else {
                top = new Top();
                top.setTitle(blogFile.getName());
            }
            if (nodeIndex != -1) {
                String node = s.substring(topIndex, nodeIndex);
                String content = s.substring(nodeIndex);
                context.setContent(content);
                context.setNote(node);
                blog.setContext(context);
                blog.setTop(top);
                blog.setTitle(top.getTitle());
            } else {
                blog.setTitle(blogFile.getName());
                context.setContent(s);
                blog.setTop(top);
                blog.setContext(context);
            }
            return blog;

        } catch (IOException e) {

            return null;
        }

    }

    public static Top getTop(String topString) {
        topString = cleanString(topString);
        String[] split = topString.split("&LITANY&");
        StringBuilder sb = new StringBuilder("{");
        for (String s1 : split) {
            if (s1.contains(":") && !s1.endsWith(":")) {
                String[] attrAndValue = s1.split(":");
                sb.append("\"").append(attrAndValue[0]).append("\"").append(":")
                        .append("\"").append(attrAndValue[1]).append("\"").append(",");
            } else if (s1.contains(":")) {
                String replace = s1.replace(":", "");
                sb.append("\"").append(replace).append("\"").append(":");
            } else {
                sb.append("\"").append(s1).append("\"");
            }
        }
        String x = sb.toString().replace("\"\"", "");
        if (x.endsWith(",")) {
            x = x.substring(0, x.length() - 1);
        }
        x = x + "}";
        Top top = JSON.parseObject(x, Top.class);
        String tags = top.getTags();
        tags = tags.replace("-", ",").substring(1, tags.length());
        top.setTags(tags);
        return top;
    }

    private static String getDate(String title) {
        String dateStr = "";
        if (StringUtils.isNotBlank(title)) {
            Matcher m = p.matcher(title);
            while (m.find()) {
                dateStr += m.group();
            }
        }
        return dateStr;
    }

    private static String cleanString(String str) {
        str = str.replace("\r", "").replace("\n", "&LITANY&").replace(TOP, "");
        char[] chars = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char aChar : chars) {
            String s = String.valueOf(aChar);
            if (!StringUtils.isBlank(s)) {
                sb.append(aChar);
            }
        }
        return sb.toString();
    }

}
