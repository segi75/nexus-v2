<template>
  <div class="home-container">
    <h1>NEXUS Framework 2.0</h1>
    <p>환영합니다. 현재 시스템 가동 중입니다.</p>
    
    <div class="links">
      <NuxtLink to="/hello" class="btn">
        API 연동 테스트 (Hello World) 보러가기
      </NuxtLink>
    </div>
  </div>
</template>

<style scoped>
.home-container {
  text-align: center;
  padding: 50px;
  font-family: sans-serif;
}
.btn {
  display: inline-block;
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #00dc82; /* Nuxt Green */
  color: white;
  text-decoration: none;
  border-radius: 5px;
  font-weight: bold;
}
.btn:hover {
  background-color: #00b368;
}
</style>