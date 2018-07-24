package com.allen.dao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 
* @ClassName: UserInfo
* @Description: 用户信息实体映射类
* @author chenliqiao
* @date 2017年12月29日 下午2:15:18
*
 */
@Entity
public class UserInfo implements Serializable{
	
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    /**角色id**/
    private Integer roleId;

    @Column
    /**姓名**/
    private String name;

    @Column
    /**账户**/
    private String account;

    @Column
    /**手机号码**/
    private String mobile;

    @Column
    /**最后登录时间**/
    private Date lastLoginTime;

    @Column
    /**创建时间**/
    private Date createTime;

    @Column
    /**状态**/
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", roleId=" + roleId + ", name=" + name + ", account=" + account + ", mobile="
				+ mobile + ", lastLoginTime=" + lastLoginTime + ", createTime=" + createTime + ", status=" + status
				+ "]";
	}
    
    
    
}