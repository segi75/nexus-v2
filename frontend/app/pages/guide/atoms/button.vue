<script setup lang="ts">
definePageMeta({
  layout: 'guide' // 위에서 만든 guide 레이아웃 적용
});
</script>

<template>
  <div>
    <h1>Button (버튼)</h1>
    <p class="intro">사용자의 클릭 액션을 처리하는 가장 기본적인 요소입니다.</p>
    <hr class="divider" />
    
    <GuideNxButton />
  </div>
</template>

<style scoped>
.intro { color: #666; font-size: 1.1rem; margin-bottom: 30px; }
.divider { margin-bottom: 40px; border: 0; border-top: 1px solid #eee; }
h1 { font-size: 2rem; font-weight: bold; margin-bottom: 10px; }
</style>