package com.sj.news35.bean;

import java.util.List;

public class NewCenter {

    public List<NewsCenter> data;
    public List<String> expand;

    public int retcode;

    public class NewsCenter {
        private List<ChildrenItem> children;
        public int id;
        public String title;
        public String type;
        public String url;
        public String url1;
        public String dayurl;
        public String excurl;
        public String weekurl;

        public List<ChildrenItem> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenItem> children) {
            this.children = children;
        }
    }

    public class ChildrenItem {
        public int id;
        public String title;
        public String type;
        public String url;
    }
}
