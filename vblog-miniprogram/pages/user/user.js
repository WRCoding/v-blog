// pages/user/user.js
const util = require('../../utils/request.js')
import Dialog from '@vant/weapp/dialog/dialog';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isLogin: false,
    username: '',
    personalData: {}
  },

  toLogin() {
    wx.navigateTo({
      url: '/pages/login/login',
    })
  },

  getPersonalData() {
    util.get('admin/personalData', {
      'username': this.data.username
    }).then(res => {
      this.setData({
        personalData: res.data
      })
    }).catch(res => {

    })
  },

  logout() {
    Dialog.confirm({
      title: '提示',
      message: '确定退出？'
    }).then(() => {
      // on confirm
      wx.removeStorageSync('token')
      wx.removeStorageSync('username')
      this.setData({
        isLogin: false,
        username: ''
      })
    }).catch(() => {
      // on cancel
    });

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    try {
      var value = wx.getStorageSync('token')
      var name = wx.getStorageSync('username')
      if (value) {
        this.setData({
          isLogin: true,
          username: name
        })
        this.getPersonalData()
      } else {
        this.setData({
          isLogin: false,
          username: ''
        })
      }
    } catch (e) {
      console.log("获取不到")
    }
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
    try {
      var value = wx.getStorageSync('token')
      var name = wx.getStorageSync('username')
      if (value) {
        this.setData({
          isLogin: true,
          username: name
        })
        this.getPersonalData()
      } else {
        this.setData({
          isLogin: false,
          username: ''
        })
      }
    } catch (e) {
      console.log("获取不到")
    }
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