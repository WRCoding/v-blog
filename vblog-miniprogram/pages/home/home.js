const app = getApp();
const util = require('../../utils/request.js')
Page({
  data: {
    CustomBar: app.globalData.CustomBar,
    TabCur: 0,
    winH: 0,
    currentPage: 1,
    scrollLeft: 0,
    cardCur: 0,
    swiperList: [{
      id: 0,
      type: 'image',
      url: 'https://lpepsi.oss-cn-shenzhen.aliyuncs.com/pepsi/2020-04-07/5e5efaec70c147dd89ad63c3af09fd0d-dgeqoj.jpg',
      title: '哈哈哈1'
    }, {
      id: 1,
      type: 'image',
      url: 'https://lpepsi.oss-cn-shenzhen.aliyuncs.com/pepsi/2020-03-06/cc3979672b4042bba30c0000ca3eb4af-img_6.jpg',
      title: '哈哈哈2'
    }, {
      id: 2,
      type: 'image',
      url: 'https://lpepsi.oss-cn-shenzhen.aliyuncs.com/pepsi/2020-03-01/b8b17cb8b9ee4db586e9615357e29d7d-m.jpg',
      title: '哈哈哈2'
    }],
    blogList: [],
    isEmpty: true,
    key: ""
    // tabList: ["全部", "访问最多"]
  },
  onLoad() {
    wx.getSystemInfo({
        success: (res) => {
          this.setData({
            winH: res.windowHeight
          });
        },
      }),
      this.getBlogList()
  },
  onShow() {

  },
  searchKey(e) {
    this.setData({
      key: e.detail.value
    })
  },
  searchBlog() {
    wx.navigateTo({
      url: '/pages/search/search?key=' + this.data.key,
    })
    this.setData({
      key: ""
    })
  },
  getBlogList() {
    wx.showLoading({
      title: '加载中',
    })
    util.get('blogs', {
      'currentPage': this.data.currentPage
    }).then((res) => {
      var totaBlogList = {}
      if (!this.data.isEmpty) {
        totaBlogList = this.data.blogList.concat(res.data.pagination)
      } else {
        totaBlogList = res.data.pagination
        this.data.isEmpty = false
      }
      this.setData({
        blogList: totaBlogList
      })
      wx.hideLoading()
    }).catch((res) => {
      console.log(res)
      wx.hideLoading()
    })
  },
  onBindscrolltolower(e) {
    this.data.currentPage += 1
    this.getBlogList()
  },
  tabSelect(e) {
    this.setData({
      TabCur: e.currentTarget.dataset.id,
      scrollLeft: (e.currentTarget.dataset.id - 1) * 60
    })
  },
  // cardSwiper
  cardSwiper(e) {
    this.setData({
      cardCur: e.detail.current
    })
  },
  toArticle(e) {
    var id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: '/pages/article/article?id=' + id,
    })
  }
})