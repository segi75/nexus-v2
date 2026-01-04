<script setup lang="ts">
definePageMeta({ layout: 'guide' });
</script>

<template>
  <div>
    <h1>Select (선택창)</h1>
    <p class="intro">미리 정의된 옵션 중 하나를 선택하는 컴포넌트입니다.</p>
    <hr class="divider" />
    <GuideNxSelect />
  </div>
</template>

<style scoped>
.intro { color: #666; font-size: 1.1rem; margin-bottom: 30px; }
.divider { margin-bottom: 40px; border: 0; border-top: 1px solid #eee; }
</style>