/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Acer
 */
import java.util.Date;

// Done
public class Account {

    private int accountId;
    private int userId;
    private int roleId;
    private String username;
    private String password;
    private String passwordResetToken;
    private Date lastOptSend;
    private Date lastPasswordChange;
    private Date lastLoginTime;
    private int wrongLoginCount;
    private Date lastWrongLogin;
    private int status;

    public Account(int accountId, String username) {
        this.accountId = accountId;
        this.username = username;
    }

    public Account() {
    }

    public Account(int userId, int roleId, String username, String password, int wrongLoginCount, int status) {
        this.userId = userId;
        this.roleId = roleId;
        this.username = username;
        this.password = password;
        this.wrongLoginCount = wrongLoginCount;
        this.status = status;

        this.accountId = 0;
        this.passwordResetToken = null;
        this.lastLoginTime = null;
        this.lastOptSend = null;
        this.lastWrongLogin = null;
        this.lastPasswordChange = null;

    }

    public Account(int accountId, int userId, int roleId, String username, String password, String password_reset_token, Date lastOptSend, Date lastPasswordChange, Date lastLoginTime, int wrongLoginCount, Date last_wrong_login, int status) {
        this.accountId = accountId;
        this.userId = userId;
        this.roleId = roleId;
        this.username = username;
        this.password = password;
        this.passwordResetToken = password_reset_token;
        this.lastOptSend = lastOptSend;
        this.lastPasswordChange = lastPasswordChange;
        this.lastLoginTime = lastLoginTime;
        this.wrongLoginCount = wrongLoginCount;
        this.lastWrongLogin = last_wrong_login;
        this.status = status;
    }

    public Account(int id, String userName, int role) {
        this.accountId = id;
        this.username = userName;
        this.roleId = role;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public Date getLastWrongLogin() {
        return lastWrongLogin;
    }

    public void setLastWrongLogin(Date lastWrongLogin) {
        this.lastWrongLogin = lastWrongLogin;
    }

    public Date getLastOptSend() {
        return lastOptSend;
    }

    public void setLastOptSend(Date lastOptSend) {
        this.lastOptSend = lastOptSend;
    }

    public Date getLastPasswordChange() {
        return lastPasswordChange;
    }

    public void setLastPasswordChange(Date lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getWrongLoginCount() {
        return wrongLoginCount;
    }

    public void setWrongLoginCount(int wrongLoginCount) {
        this.wrongLoginCount = wrongLoginCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Account{" + "accountId=" + accountId + ", userId=" + userId + ", roleId=" + roleId + ", username=" + username + ", password=" + password + ", passwordResetToken=" + passwordResetToken + ", lastOptSend=" + lastOptSend + ", lastPasswordChange=" + lastPasswordChange + ", lastLoginTime=" + lastLoginTime + ", wrongLoginCount=" + wrongLoginCount + ", lastWrongLogin=" + lastWrongLogin + ", status=" + status + '}';
    }

}
