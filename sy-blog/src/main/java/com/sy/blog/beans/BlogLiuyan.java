package com.sy.blog.beans;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Su.Yang
 * @since 2019-10-15
 */
@TableName("blog_liuyan")
public class BlogLiuyan extends Model<BlogLiuyan> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 用户id
     */
	@TableField("user_id")
	private Integer userId;
    /**
     * 内容
     */
	private String content;
    /**
     * 回复类型
     */
	@TableField("reply_type")
	private Integer replyType;
	/**
	 * 父类id
	 */
	@TableField("parent_id")
	private Integer parentId;
    /**
     * 文章id
     */
	@TableField("reply_id")
	private Integer replyId;
    /**
     * 状态
     */
	private Integer status;
	/**
	 * 留言时间
	 */
	@TableField("liuyan_date")
	private Date liuyanDate;
	private String remark;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getReplyType() {
		return replyType;
	}

	public void setReplyType(Integer replyType) {
		this.replyType = replyType;
	}

	public Integer getReplyId() {
		return replyId;
	}

	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getParentId() { return parentId; }

	public void setParentId(Integer parentId) { this.parentId = parentId; }

	public Date getLiuyanDate() {
		return liuyanDate;
	}

	public void setLiuyanDate(Date liuyanDate) {
		this.liuyanDate = liuyanDate;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
