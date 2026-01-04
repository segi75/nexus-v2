<script setup>
// 1. 전역 테마 로딩 (모든 페이지 공통 적용)
const { fetchTheme } = useNexusTheme();

onMounted(() => {
  fetchTheme('DEFAULT_THEME');
});
</script>

<template>
  <div class="app-layout">
    <header class="navbar">
      <h2 class="logo">NEXUS 2.0</h2>
      <nav>
        <NuxtLink to="/" class="nav-item">홈</NuxtLink>
        <NuxtLink to="/notice" class="nav-item">공지사항</NuxtLink>
      </nav>
    </header>

    <main class="content-area">
      <NuxtPage />
    </main>
  </div>
</template>

<style scoped>
/* 전체 레이아웃 잡기 */
.app-layout {
  min-height: 100vh;
  background-color: var(--nx-surface, #f9f9f9); /* DB 테마색 */
  color: #333;
}

.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 30px;
  background-color: white;
  border-bottom: 1px solid #ddd;
}

.logo {
  color: var(--nx-primary); /* DB 테마색 */
  margin: 0;
}

.nav-item {
  margin-left: 20px;
  text-decoration: none;
  color: #555;
  font-weight: bold;
}

.nav-item:hover {
  color: var(--nx-primary);
}

.content-area {
  padding: 20px;
}
</style>