// pages/login/login.js
const util = require('../../utils/request.js')
import Toast from '@vant/weapp/toast/toast';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isDisabled: true,
    form: {
      username: "",
      password: ""
    }
  },

  login: function() {
    util.post('login', this.data.form).then(res => {
      var code = res.code
      if (code === 500) {
        Toast.fail(res.data);
        var eamil = "form.username";
        var pwd = "form.password";
        this.setData({
          [eamil]: "",
          [pwd]: ""
        })
        this.judeg()
      } else {
        wx.setStorageSync('token', res.data.token)
        wx.setStorageSync('username', res.data.username)
        wx.navigateBack({

        })
      }
    }).then(res => {

    })
  },

  inputEmail(e) {
    var eamil = "form.username";
    this.setData({
      [eamil]: e.detail.value
    })
    this.judeg()
  },
  inputPwd(e) {
    var pwd = "form.password";
    this.setData({
      [pwd]: e.detail.value
    })
    this.judeg()
  },
  judeg() {
    if (this.data.form.username.length == 0 || this.data.form.password.length == 0) {
      this.setData({
        isDisabled: true
      })
    }
    if (this.data.form.username.length > 0 && this.data.form.password.length > 0) {
      this.setData({
        isDisabled: false
      })
    }
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  }
})