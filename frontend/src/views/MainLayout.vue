<template>
  <div class="app-shell">
    <main class="app-main">
      <router-view />
    </main>
    <van-tabbar route class="bottom-nav">
      <van-tabbar-item to="/" icon="wap-home-o">首页</van-tabbar-item>
      <van-tabbar-item to="/foods" icon="orders-o">食材</van-tabbar-item>
      <van-tabbar-item to="/categories" icon="apps-o">分类</van-tabbar-item>
      <van-tabbar-item to="/stats" icon="bar-chart-o">统计</van-tabbar-item>
    </van-tabbar>
  </div>
</template>
