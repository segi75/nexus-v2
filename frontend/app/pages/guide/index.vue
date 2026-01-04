<script setup lang="ts">
definePageMeta({ layout: 'guide' });

// 메인 화면에 보여줄 바로가기 카드들
const components = [
  { name: 'Button', desc: '클릭 액션 및 상태 제어', path: '/guide/atoms/button', icon: '🖱️' },
  { name: 'Input', desc: '텍스트 입력 폼', path: '/guide/atoms/input', icon: '📝' },
  { name: 'Select', desc: '옵션 선택 드롭다운', path: '/guide/atoms/select', icon: '▼' },
];
</script>

<template>
  <div class="welcome-container">
    <div class="hero">
      <h1>👋 NEXUS Design System</h1>
      <p>
        개발 생산성을 높이는 UI 컴포넌트 가이드입니다.<br>
        좌측 메뉴를 이용하거나 아래 카드를 클릭하여 문서를 확인하세요.
      </p>
    </div>

    <div class="shortcut-grid">
      <NuxtLink 
        v-for="comp in components" 
        :key="comp.path" 
        :to="comp.path"
        class="shortcut-card"
      >
        <div class="icon">{{ comp.icon }}</div>
        <div class="info">
          <h3>{{ comp.name }}</h3>
          <p>{{ comp.desc }}</p>
        </div>
        <div class="arrow">→</div>
      </NuxtLink>
    </div>
  </div>
</template>

<style scoped>
.welcome-container { padding-top: 20px; }
.hero { margin-bottom: 50px; text-align: center; }
.hero h1 { font-size: 2.5rem; color: #212529; margin-bottom: 15px; font-weight: 800; }
.hero p { font-size: 1.1rem; color: #666; line-height: 1.6; }

/* 카드 그리드 스타일 */
.shortcut-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
}

.shortcut-card {
  display: flex;
  align-items: center;
  gap: 15px;
  background: white;
  border: 1px solid #e9ecef;
  padding: 20px;
  border-radius: 12px;
  text-decoration: none;
  color: inherit;
  transition: all 0.2s;
  box-shadow: 0 2px 8px rgba(0,0,0,0.03);
}

.shortcut-card:hover {
  transform: translateY(-5px);
  border-color: var(--nx-primary, #3B82F6);
  box-shadow: 0 10px 20px rgba(59, 130, 246, 0.1);
}

.icon { font-size: 2rem; }
.info h3 { margin: 0 0 5px 0; font-size: 1.2rem; color: #343a40; }
.info p { margin: 0; font-size: 0.9rem; color: #868e96; }
.arrow { margin-left: auto; font-weight: bold; color: #adb5bd; }
.shortcut-card:hover .arrow { color: var(--nx-primary, #3B82F6); }
</style>