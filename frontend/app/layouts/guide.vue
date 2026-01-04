<script setup lang="ts">
const route = useRoute();

// ★ 메뉴 목록 업데이트 (여기에 추가하면 사이드바에 생깁니다)
const menuItems = [
  {
    category: '1. Atoms (기본 부품)',
    items: [
      { name: 'Button (버튼)', path: '/guide/atoms/button' },
      { name: 'Input (입력창)', path: '/guide/atoms/input' },   // 추가됨
      { name: 'Select (선택창)', path: '/guide/atoms/select' }  // 추가됨
    ]
  },
  // 나중에 추가될 메뉴 예시
  /*
  {
    category: '2. Molecules (조립 부품)',
    items: [
      { name: 'SearchBox (검색)', path: '/guide/molecules/search' }
    ]
  }
  */
];
</script>

<template>
  <div class="doc-layout">
    <aside class="doc-sidebar">
      <div class="brand">🧩 NEXUS Docs</div>
      
      <nav class="doc-nav">
        <div v-for="(group, idx) in menuItems" :key="idx" class="nav-group">
          <h3 class="group-title">{{ group.category }}</h3>
          <ul>
            <li v-for="item in group.items" :key="item.path">
              <NuxtLink 
                :to="item.path" 
                class="nav-item"
                :class="{ 'active': route.path === item.path }"
              >
                {{ item.name }}
              </NuxtLink>
            </li>
          </ul>
        </div>
      </nav>
    </aside>

    <main class="doc-content">
      <slot />
    </main>
  </div>
</template>

<style scoped>
/* 기존 스타일 유지 */
.doc-layout { display: flex; min-height: 100vh; font-family: 'Pretendard', sans-serif; background: #fff;}
.doc-sidebar { width: 260px; background: #f8f9fa; border-right: 1px solid #e9ecef; position: fixed; height: 100vh; padding: 20px; box-sizing: border-box; overflow-y: auto;}
.brand { font-size: 1.5rem; font-weight: 800; color: #212529; margin-bottom: 30px; border-bottom: 2px solid #212529; padding-bottom: 10px; }
.nav-group { margin-bottom: 25px; }
.group-title { font-size: 0.85rem; color: #868e96; font-weight: 700; margin-bottom: 10px; text-transform: uppercase; }
.doc-nav ul { list-style: none; padding: 0; margin: 0; }
.nav-item { display: block; padding: 10px 12px; color: #495057; text-decoration: none; border-radius: 6px; margin-bottom: 4px; font-size: 0.95rem; transition: all 0.2s; }
.nav-item:hover { background: #e9ecef; color: #212529; }
.nav-item.active { background: #e7f5ff; color: #1971c2; font-weight: 700; }
.doc-content { margin-left: 260px; flex: 1; padding: 40px 60px; max-width: 1000px; }
</style>