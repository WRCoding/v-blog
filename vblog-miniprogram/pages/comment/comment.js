// pages/comment/comment.js
const util = require('../../utils/request.js')
import Toast from '@vant/weapp/toast/toast';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isLogin: false,
    articleId: "",
    InputBottom: 0,
    show: false,
    comments: [],
    placeholder: '想和作者说点什么...',
    commentForm: {
      articleId: "",
      commentName: "",
      comment: "",
      replyName: "",
      parentId: -1
    },
    comment: ""
  },
  saveReplyComment(e) {
    var parentId = "commentForm.parentId"
    var replyName = "commentForm.replyName"
    var articleId = "commentForm.articleId"
    var name = e.currentTarget.dataset.name
    var id = e.currentTarget.dataset.commentid
    this.setData({
      placeholder: '@ ' + name + ' :',
      [parentId]: id,
      [replyName]: name,
      [articleId]: ""
    })
  },
  saveComment() {
    if (!this.data.isLogin) {
      this.setData({
        comment: ""
      })
      wx.navigateTo({
        url: '/pages/login/login',
      })
    } else if (this.data.commentForm.comment.length == 0) {
      Toast("评论内容不能为空！");
    } else {
      util.post('saveComment', this.data.commentForm).then(res => {
        if (res.code == 200) {
          Toast.success(res.data);
          this.onReady()
        } else {
          Toast.fail(res.data);
        }
      }).catch(res => {
        Toast.fail('评论失败');
      })
      this.handleReset()
    }
  },
  handleReset() {
    var articleId = "commentForm.articleId"
    var parentId = "commentForm.parentId"
    this.setData({
      [articleId]: this.data.articleId,
      [parentId]: -1,
      comment: "",
      placeholder: "想对作者说点啥..."
    })
  },
  commentData(e) {
    var comment = "commentForm.comment";
    this.setData({
      [comment]: e.detail.value,
    })
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
    this.setData({
      articleId: options.id,
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {
    try {
      var value = wx.getStorageSync('token')
      if (value) {
        this.setData({
          isLogin: true
        })
      } else {
        this.setData({
          isLogin: false
        })
      }
    } catch (e) {
      console.log("获取不到")
    }
    var id = this.data.articleId
    var articleId = "commentForm.articleId"
    var name = "commentForm.commentName"
    var commentName = wx.getStorageSync('username')
    this.setData({
      [articleId]: id,
      [name]: commentName
    })
    util.get(`comment/${id}`, '').then((res) => {
      this.setData({
        comments: res.data.data
      })
    }).catch((res) => {
      console.log(res)
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    try {
      var value = wx.getStorageSync('token')
      if (value) {
        this.setData({
          isLogin: true
        })
      } else {
        this.setData({
          isLogin: false
        })
      }
    } catch (e) {
      console.log("获取不到")
    }
    var id = this.data.articleId
    var articleId = "commentForm.articleId"
    var name = "commentForm.commentName"
    var commentName = wx.getStorageSync('username')
    this.setData({
      [articleId]: id,
      [name]: commentName
    })
    util.get(`comment/${id}`, '').then((res) => {
      this.setData({
        comments: res.data.data
      })
    }).catch((res) => {

    })
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