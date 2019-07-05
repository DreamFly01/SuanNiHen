package com.fdl.bean;

import java.util.List;

/**
 * <p>desc：<p>
 * <p>author：DreamFly<p>
 * <p>creatTime：2019/4/4<p>
 * <p>changeTime：2019/4/4<p>
 * <p>version：1<p>
 */
public class SuperMarkGoodsBean {
    public int GoodsId;
    public int Supplier;
    public String GoodsName;
    public String CoverImgId;
    public String[] RollImages;
    public String Describe;
    public int CommentCount;
    public List<GoodsCommentResult> Comment;

    public class GoodsCommentResult{
        public int CommentID;
        public int UserID;
        public String UserName;
        public int Starlevel;
        public long CommentDate;
        public String Comment;
        public String[] CommentImgs;
        public String HeadPortrait;
    }
}
