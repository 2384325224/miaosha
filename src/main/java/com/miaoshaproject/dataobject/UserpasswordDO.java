package com.miaoshaproject.dataobject;

public class UserpasswordDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_password.id
     *
     * @mbg.generated Tue Dec 18 16:36:45 CST 2018
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_password.encrpt_password
     *
     * @mbg.generated Tue Dec 18 16:36:45 CST 2018
     */
    private String encrptPassword;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_password.userid
     *
     * @mbg.generated Tue Dec 18 16:36:45 CST 2018
     */
    private Integer userid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_password.id
     *
     * @return the value of user_password.id
     *
     * @mbg.generated Tue Dec 18 16:36:45 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_password.id
     *
     * @param id the value for user_password.id
     *
     * @mbg.generated Tue Dec 18 16:36:45 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_password.encrpt_password
     *
     * @return the value of user_password.encrpt_password
     *
     * @mbg.generated Tue Dec 18 16:36:45 CST 2018
     */
    public String getEncrptPassword() {
        return encrptPassword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_password.encrpt_password
     *
     * @param encrptPassword the value for user_password.encrpt_password
     *
     * @mbg.generated Tue Dec 18 16:36:45 CST 2018
     */
    public void setEncrptPassword(String encrptPassword) {
        this.encrptPassword = encrptPassword == null ? null : encrptPassword.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_password.userid
     *
     * @return the value of user_password.userid
     *
     * @mbg.generated Tue Dec 18 16:36:45 CST 2018
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_password.userid
     *
     * @param userid the value for user_password.userid
     *
     * @mbg.generated Tue Dec 18 16:36:45 CST 2018
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}