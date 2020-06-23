// pages/sorts/sorts.js
const app = getApp();
const util = require('../../utils/request.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    TabCur: 0,
    scrollLeft: 0,
    tagList: [],
    blogWithTagList: []
  },

  tabSelect(e) {
    var key = e.currentTarget.dataset.tagname
    this.setData({
      TabCur: e.currentTarget.dataset.id,
      scrollLeft: (e.currentTarget.dataset.id - 1) * 60
    })
    this.getBlogWithTag(key)
  },

  getBlogWithTag(key) {
    this.data.blogWithTagList = []
    util.get(`tag/${key}`, '').then(res => {
      this.setData({
        blogWithTagList: res.data 
      })
    }).catch(res => {

    })
  },
  toArticle(e) {
    var id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: '/pages/article/article?id=' + id,
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    util.get('tags', '').then(res => {
      this.setData({
        tagList: res.data
      })
      this.getBlogWithTag(this.data.tagList[0].tagName)
    }).catch(res => {

    })
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