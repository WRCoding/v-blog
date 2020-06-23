// pages/article/article.js
const app = getApp();
const util = require('../../utils/request.js')
import Toast from '@vant/weapp/toast/toast';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isLogin: false,
    isLike: false,
    likeStatus: 0,
    articleId: 0,
    info: {},
    article: {},
    InputBottom: 0,
    commentForm: {
      articleId: -1,
      commentName: "",
      comment: "",
      replyName: "",
      parentId: -1
    },
    comment: ""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    try {
      var articleId = "commentForm.articleId"
      var commentName = "commentForm.commentName"
      var username = wx.getStorageSync('username')
      console.log(username)
      this.setData({
        [articleId]: options.id,
        [commentName]: username,
        articleId: options.id
      })
      var value = wx.getStorageSync('token')
      if (value) {
        // Do something with return value
        this.setData({
          isLogin: true
        })
      } else {
        this.setData({
          isLogin: false
        })
      }
    } catch (e) {
      // Do something when catch error
      console.log("获取不到")
    }
  },

  showArticle(id) {
    const _ts = this;
    util.get(`article/${id}`, "").then((res) => {
      let obj = app.towxml.toJson(res.data.data.articleContent, 'markdown', {});
      _ts.setData({
        articleId: id,
        info: res.data.data,
        article: obj,
        isLoading: false
      });
    }).catch((res) => {
    });
    this.getLikeStatus();
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
        this.getLikeStatus()
      }
    } catch (e) {
      console.log("获取不到")
    }
    //
    try {
      var commentName = "commentForm.commentName"
      var username = wx.getStorageSync('username')
      console.log('username: '+username)
      this.setData({
        [commentName]: username
      })
      console.log('commentForm', this.data.commentForm)
      var value = wx.getStorageSync('token')
      if (value) {
        // Do something with return value
        this.setData({
          isLogin: true
        })
      } else {
        this.setData({
          isLogin: false
        })
      }
    } catch (e) {
      // Do something when catch error
      console.log(e)
    }
  },
  handleLike(e) {
    this.setLiked()
  },
  getLikeStatus() {
    var username = wx.getStorageSync('username')
    if (username) {
      util.get(`like/${this.data.articleId}/${username}`).then(res => {
        this.setData({
          likeStatus: res.data
        })
        if (this.data.likeStatus == 1) {
          this.setData({
            isLike: true
          })
        } else {
          this.setData({
            isLike: false
          })
        }
      }).catch(res => {
        console.log(res)
      })
    } else {}
  },
  InputFocus(e) {
    this.setData({
      InputBottom: e.detail.height
    })
  },
  InputBlur(e) {
    this.setData({
      InputBottom: 0
    })
  },
  commentData(e) {
    var comment = "commentForm.comment"
    this.setData({
      [comment]: e.detail.value
    })
  },
  toComment(e) {
    wx.navigateTo({
      url: '/pages/comment/comment?id=' + this.data.articleId,
    })
  },
  setLiked(e) {
    var username = wx.getStorageSync('username')
    var value = wx.getStorageSync('token')
    if (value) {
      var falg = this.data.isLike
      util.get(`likeStatus/${this.data.articleId}/${username}/${this.data.likeStatus}`).then(res => {
        this.setData({
          isLike: !falg
        })
        this.data.info.likeNum = res.data
        this.data.likeStatus = this.data.isLike ? 1 : 0
        this.onReady()
      }).catch(res => {
        Toast.fail(res);
        this.onReady()
      })
    } else {
      Toast.fail("请登录");
      this.onReady()
    }
  },
  saveComment(e) {
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
      var replyName = "commentForm.replyName"
      this.setData({
        [replyName]: e.currentTarget.dataset.commentname
      })
      console.log('commentForm',this.data.commentForm)
      util.post('saveComment', this.data.commentForm).then(res => {
        if (res.code == 200) {
          Toast.success(res.data);
        } else {
          Toast.fail(res.data);
        }
      }).catch(res => {
        Toast.fail('评论失败');
      })
      var comment = "commentForm.comment"
      this.setData({
        comment: "",
        [comment]: ""
      })
    }
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
    this.showArticle(this.data.articleId)
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
    this.showArticle(this.data.articleId)
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
    wx.stopPullDownRefresh()
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