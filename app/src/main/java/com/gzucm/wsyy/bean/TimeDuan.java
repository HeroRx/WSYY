package com.gzucm.wsyy.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * fpvA7778
 *	jFIbXXXy
 MpVCEEEF	17:00	16:30	l	t11.png	2017-11-14 22:41:19	2017-11-29 12:25:34
 cpq3QQQW	16:30	16:00	k	t10.png	2017-11-14 22:41:04	2017-11-29 12:25:25
 5rgXMMMV	16:00	15:30	j	t12.png	2017-11-14 22:38:50	2017-11-29 12:27:14
 bxD8MMMk	15:30	15:00	i	t09.png	2017-11-14 22:38:38	2017-11-29 12:19:24
 47rcGGGL	15:00	14:30	h	t08.png	2017-11-14 22:38:26	2017-11-29 12:23:34
 r4Qo3334	14:30	14:00	g	t03.png	2017-11-14 22:37:57	2017-11-29 12:23:26
 DTtgAAAQ	11:00	10:30	f	t04.png	2017-11-14 22:37:34	2017-11-29 12:18:41
 dlZt000r	10:30	10:00	e	t05.png	2017-11-14 22:37:21	2017-11-29 12:18:22
 SJdAlllp	10:00	09:30	d	t06.png	2017-11-14 22:37:07	2017-11-29 12:18:13
 f5Hq0009	09:30	09:00	c	t07.png	2017-11-14 22:36:51	2017-11-29 12:18:00
 Mcyf222e	09:00	08:30	b	t02.png	2017-11-14 22:36:37	2017-11-29 12:17:14
 VLjs0006	08:30	08:00   a
 * Created by Administrator on 2017/11/21 0021.
 */

public class TimeDuan extends BmobObject implements Serializable{
    private String t_code;
    private String t_end;
    private String t_start;
    private BmobFile td_icon;
    public String getT_code() {
        return t_code;
    }

    public void setT_code(String t_code) {
        this.t_code = t_code;
    }

    public String getT_end() {
        return t_end;
    }

    public void setT_end(String t_end) {
        this.t_end = t_end;
    }

    public String getT_start() {
        return t_start;
    }

    public void setT_start(String t_start) {
        this.t_start = t_start;
    }

    public BmobFile getTd_icon() {
        return td_icon;
    }

    public void setTd_icon(BmobFile td_icon) {
        this.td_icon = td_icon;
    }
}
