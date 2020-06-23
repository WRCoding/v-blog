/**
 * GET请求封装
 */
function get(url, data = {}) {
  return request(url, data, 'GET')
}

/**
 * POST请求封装
 */
function post(url, data = {}) {
  return request(url, data, 'POST')
}


function request(url, data = {}, method = "GET") {
  const header = {
    'Content-Type': 'application/json',
  }
  // https://www.lpepsi.top/api/
  const baseURL = 'http://192.168.0.12:7777/'
  try {
    var value = wx.getStorageSync('token')
    if (value) {
      // Do something with return value
      header.Authorization = value
    }
  } catch (e) {
    // Do something when catch error
  }
  return new Promise(function(resolve, reject) {
    wx.request({
      url: baseURL + url,
      data: data,
      method: method,
      header: header,
      success: function(res) {
        if (res.statusCode == 200) {
          //请求正常200
          var daesData = null
          daesData = res.data
          if(res.data.code == 5001){
            reject("登录已过期")
            wx.removeStorageSync('token')
            wx.removeStorageSync('username')
          }
          //正常
          resolve(daesData);
        } else if (res.data == 401) {
          //此处验证了token的登录失效，如果不需要，可以去掉。
          //未登录，跳转登录界面
          reject("登录已过期")
          wx.removeStorageSync('token')
          wx.removeStorageSync('username')
        } else {
          //请求失败
          reject("请求失败：" + res.statusCode)
        }
      },
      fail: function(err) {
        //服务器连接异常
        console.log('===============================================================================================')
        console.log('==    接口地址：' + url)
        console.log('==    接口参数：' + JSON.stringify(data))
        console.log('==    请求类型：' + method)
        console.log("==    服务器连接异常")
        console.log('===============================================================================================')
        reject("服务器连接异常，请检查网络再试")
      }
    })
  });
}

module.exports = {
  request,
  get,
  post
}